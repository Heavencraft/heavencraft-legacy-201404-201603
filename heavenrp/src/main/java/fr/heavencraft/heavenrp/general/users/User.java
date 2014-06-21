package fr.heavencraft.heavenrp.general.users;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

import org.bukkit.Bukkit;
import org.bukkit.Location;

import fr.heavencraft.exceptions.HeavenException;
import fr.heavencraft.heavenrp.HeavenRP;
import fr.heavencraft.heavenrp.exceptions.UnknownErrorException;
import fr.heavencraft.heavenrp.provinces.ProvincesManager;
import fr.heavencraft.heavenrp.provinces.ProvincesManager.Province;

public class User
{
	private final int _id;
	private final String _name;
	private int _balance;
	private int _homeNumber;

	private Timestamp _dealerLicense;
	private Timestamp _lastLogin;

	User(ResultSet rs) throws SQLException
	{
		_id = rs.getInt("id");
		_name = rs.getString("name");
		_balance = rs.getInt("balance");
		_homeNumber = rs.getInt("homeNumber");
		_dealerLicense = rs.getTimestamp("dealer_license");
		_lastLogin = rs.getTimestamp("last_login");
	}

	public int getId()
	{
		return _id;
	}

	public String getName()
	{
		return _name;
	}

	public Province getProvince() throws HeavenException
	{
		return ProvincesManager.getProvinceByUser(_id);
	}

	public void setProvince(Integer provinceId)
	{
		try
		{
			PreparedStatement ps = HeavenRP.getConnection().prepareStatement(
					"INSERT INTO mayor_people (user_id, city_id) VALUES (?, ?)");
			ps.setInt(1, _id);
			ps.setInt(2, provinceId);

			ps.executeUpdate();
		}

		catch (SQLException ex)
		{
			ex.printStackTrace();
		}
	}

	public void removeProvince()
	{
		try
		{
			PreparedStatement ps = HeavenRP.getConnection().prepareStatement(
					"DELETE FROM mayor_people WHERE user_id = ?");
			ps.setInt(1, _id);

			ps.executeUpdate();
		}

		catch (SQLException ex)
		{
			ex.printStackTrace();
		}
	}

	/*
	 * Pièces d'or
	 */

	public int getBalance()
	{
		return _balance;
	}

	public void updateBalance(int delta) throws HeavenException
	{
		if (_balance < 0)
			throw new HeavenException("Vous avez moins de 0 pièces d'or sur vous. O_o");

		if (_balance + delta < 0)
			// throw new LorganException("Vous n'avez pas assez de pièces d'or sur vous.");
			throw new HeavenException("Vous fouillez dans votre bourse... Vous n'avez pas assez.");

		_balance += delta;
		update();
	}

	/*
	 * Homes
	 */

	public int getHomeNumber()
	{
		return _homeNumber;
	}

	public void incrementHomeNumber() throws HeavenException
	{
		try
		{
			PreparedStatement ps = HeavenRP.getConnection().prepareStatement(
					"UPDATE users SET homeNumber = homeNumber + 1 WHERE id = ?");
			ps.setInt(1, _id);
			ps.executeUpdate();

			_homeNumber++;
		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
			throw new UnknownErrorException();
		}
	}

	public Location getHome(int nb) throws HeavenException
	{
		if (nb < 1 || nb > _homeNumber)
			throw new HeavenException("Vous n'avez pas acheté le {home %1$d}.", nb);

		try
		{
			PreparedStatement ps = HeavenRP.getConnection().prepareStatement(
					"SELECT world, x, y, z, yaw, pitch FROM homes WHERE user_id = ? AND home_nb = ? LIMIT 1");
			ps.setInt(1, _id);
			ps.setInt(2, nb);

			ResultSet rs = ps.executeQuery();

			if (!rs.next())
				throw new HeavenException("Vous n'avez pas configuré votre {home %1$d}.", nb);

			return new Location(Bukkit.getWorld(rs.getString("world")), rs.getDouble("x"), rs.getDouble("y"),
					rs.getDouble("z"), rs.getFloat("yaw"), rs.getFloat("pitch"));
		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
			throw new HeavenException("Cette erreur n'est pas sensée se produire.");
		}
	}

	public void setHome(int nb, Location home) throws HeavenException
	{
		if (nb < 1 || nb > _homeNumber)
			throw new HeavenException("Vous n'avez pas acheté le {home %1$d}.", nb);

		try
		{
			PreparedStatement ps = HeavenRP
					.getConnection()
					.prepareStatement(
							"REPLACE INTO homes SET world = ?, x = ?, y = ?, z = ?, yaw = ?, pitch = ?, user_id = ?, home_nb = ?");
			ps.setString(1, home.getWorld().getName());
			ps.setDouble(2, home.getX());
			ps.setDouble(3, home.getY());
			ps.setDouble(4, home.getZ());
			ps.setFloat(5, home.getYaw());
			ps.setFloat(6, home.getPitch());

			ps.setInt(7, _id);
			ps.setInt(8, nb);

			ps.executeUpdate();
		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
			throw new HeavenException("Cette erreur n'est pas sensée se produire.");
		}
	}

	/*
	 * Mise à jour de la base de données
	 */

	private void update()
	{
		try
		{
			PreparedStatement ps = HeavenRP.getConnection().prepareStatement(
					"UPDATE users SET balance = ?, last_login = ? WHERE id = ?;");
			ps.setInt(1, _balance);
			ps.setTimestamp(2, _lastLogin);
			ps.setInt(3, _id);

			ps.executeUpdate();
			ps.close();
		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
		}
	}

	public boolean hasDealerLicense()
	{
		if (_dealerLicense == null)
			return false;

		return _dealerLicense.after(new Date());
	}

	public boolean alreadyHasDealerLicense()
	{
		return _dealerLicense != null;
	}

	public Date getLicenseExpireDate()
	{
		return _dealerLicense;
	}

	public void buyDealerLicense()
	{
		if (hasDealerLicense())
		{
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(_dealerLicense);
			calendar.add(Calendar.MONTH, 1);

			_dealerLicense = new Timestamp(calendar.getTimeInMillis());
		}
		else
		{
			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.MONTH, 1);

			_dealerLicense = new Timestamp(calendar.getTimeInMillis());
		}

		try
		{
			PreparedStatement ps = HeavenRP.getConnection().prepareStatement(
					"UPDATE users SET dealer_license = ? WHERE id = ?;");
			ps.setTimestamp(1, _dealerLicense);
			ps.setInt(2, _id);

			ps.executeUpdate();
			ps.close();
		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
		}
	}

	/*
	 * Last Login
	 */

	public Date getLastLogin()
	{
		return _lastLogin;
	}

	public void updateLastLogin(Date date)
	{
		_lastLogin = new Timestamp(date.getTime());

		update();
	}

}