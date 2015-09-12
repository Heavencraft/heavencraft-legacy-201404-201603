package fr.heavencraft.heavenrp.general.users;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

import fr.heavencraft.exceptions.HeavenException;
import fr.heavencraft.exceptions.SQLErrorException;
import fr.heavencraft.heavenrp.HeavenRP;
import fr.heavencraft.heavenrp.database.users.UpdateUserBalanceQuery;
import fr.heavencraft.heavenrp.provinces.ProvincesManager;
import fr.heavencraft.heavenrp.provinces.ProvincesManager.Province;

public class User
{
	private final int _id;
	private final String _uuid;
	private final String _name;
	private int _balance;
	private int _homeNumber;

	private Timestamp _dealerLicense;
	private Timestamp _lastLogin;

	User(ResultSet rs) throws SQLException
	{
		_id = rs.getInt("id");
		_uuid = rs.getString("uuid");
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

	public String getUUID()
	{
		return _uuid;
	}

	public String getName()
	{
		return _name;
	}

	public Province getProvince() throws HeavenException
	{
		return ProvincesManager.getProvinceByUser(this);
	}

	public void setProvince(Integer provinceId)
	{
		try (PreparedStatement ps = HeavenRP.getConnection().prepareStatement(
				"INSERT INTO mayor_people (user_id, city_id) VALUES (?, ?)"))
		{
			ps.setInt(1, _id);
			ps.setInt(2, provinceId);

			ps.executeUpdate();
		}

		catch (final SQLException ex)
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

	/*
	 * Homes
	 */

	public int getHomeNumber()
	{
		return _homeNumber;
	}

	/*
	 * Mise à jour de la base de données
	 */

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
			final Calendar calendar = Calendar.getInstance();
			calendar.setTime(_dealerLicense);
			calendar.add(Calendar.MONTH, 1);

			_dealerLicense = new Timestamp(calendar.getTimeInMillis());
		}
		else
		{
			final Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.MONTH, 1);

			_dealerLicense = new Timestamp(calendar.getTimeInMillis());
		}

		try (PreparedStatement ps = HeavenRP.getConnection().prepareStatement(
				"UPDATE users SET dealer_license = ? WHERE id = ?;"))
		{
			ps.setTimestamp(1, _dealerLicense);
			ps.setInt(2, _id);

			ps.executeUpdate();
			ps.close();
		}
		catch (final SQLException ex)
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

	// For vault :(
	public void updateBalance(int delta) throws HeavenException
	{
		try
		{
			new UpdateUserBalanceQuery(this, delta).executeQuery();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			throw new SQLErrorException();
		}
	}
}