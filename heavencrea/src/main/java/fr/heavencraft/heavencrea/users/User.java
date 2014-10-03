package fr.heavencraft.heavencrea.users;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;

import fr.heavencraft.exceptions.HeavenException;
import fr.heavencraft.exceptions.SQLErrorException;
import fr.heavencraft.heavencrea.ConnectionManager;
import fr.heavencraft.heavencrea.exceptions.PlayerNoJetonRequired;
import fr.heavencraft.utils.ChatUtil;

public class User
{
	private final int _id;
	private final String _uuid;
	private final String _name;

	private int _jetons;
	private int _homeNumber;
	private String _savedDate;
	private Block _lastInteractBlock;

	public User(ResultSet rs) throws SQLException
	{
		_id = rs.getInt("id");
		_uuid = rs.getString("uuid");
		_name = rs.getString("name");

		_jetons = rs.getInt("jetons");
		_homeNumber = rs.getInt("homeNumber");
		_savedDate = rs.getString("savedDate");
	}

	public int getId()
	{
		return _id;
	}

	public String getUuid()
	{
		return _uuid;
	}

	public String getName()
	{
		return _name;
	}

	public int getJetons()
	{
		return _jetons;
	}

	public boolean shouldBeNewDay()
	{
		String currentDate = Stuff.getDateTime();
		MyPlugin.log(currentDate + " vs " + _savedDate);
		return !currentDate.equalsIgnoreCase(_savedDate);
	}

	public void setSavedData(String savedDate) throws HeavenException
	{
		_savedDate = savedDate;
		update();
	}

	// public void setStringVariable(String variable, String value)
	// {
	// if (_stringVariables.containsKey(variable))
	// _stringVariables.remove(variable);
	// _stringVariables.put(variable, value);
	// }
	//
	// public String getStringVariable(String variable)
	// {
	// if (_stringVariables.containsKey(variable))
	// return _stringVariables.get(variable);
	// return "";
	// }
	//
	public void setInteractBlock(Block block)
	{
		_lastInteractBlock = block;
	}

	public Block getInteractBlock()
	{
		return _lastInteractBlock;
	}

	public void update() throws HeavenException
	{
		try
		{
			PreparedStatement ps = ConnectionManager.getConnection().prepareStatement(
					"UPDATE users SET jetons = ?, savedDate = ? WHERE id = ?");
			ps.setInt(1, _jetons);
			ps.setString(2, _savedDate);
			ps.setInt(3, _id);

			ps.executeUpdate();
			ps.close();
		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
			throw new SQLErrorException();
		}
	}

	// public void loadVarStr(String varString)
	// {
	// String[] listString = varString.split("\n");
	//
	// for (int i = 0; i < listString.length; i++)
	// {
	// String[] lineData = listString[i].split("=");
	// if (lineData.length == 2)
	// {
	// String variable = lineData[0].trim();
	// String value = lineData[1].trim();
	// _stringVariables.put(variable, value);
	// }
	// }
	// }

	public void updateBalance(int delta) throws HeavenException
	{
		if (_jetons < 0)
			throw new PlayerNoJetonRequired(-delta);
		if (_jetons + delta < 0)
			throw new PlayerNoJetonRequired(-delta);

		_jetons += delta;
		update();
	}

	public void stateBalance()
	{
		ChatUtil.sendMessage(Bukkit.getPlayer(_name), "Vous avez maintenant {" + _jetons + "} Jetons.");
	}

	// public void setTeleportRequestName(String name)
	// {
	// _requestName = name;
	// }
	//
	// public String getTeleportRequestName()
	// {
	// return _requestName;
	// }

	// public boolean setHome(int number, Location loc)
	// {
	// setStringVariable("home_" + number, Stuff.locationToString(loc));
	// return true;
	// }

	// public boolean hasHome(int number)
	// {
	// return !getStringVariable("home_" + number).isEmpty();
	// }

	// public Location getHome(int number) throws PlayerNotHaveHomeNumber
	// {
	// if (number > _nbHome)
	// throw new PlayerNotHaveHomeNumber(number);
	//
	// return Stuff.stringToLocation(getStringVariable("home_" + number));
	// }

	// public int getHomeNumbre()
	// {
	// return _nbHome;
	// }

	/***
	 * How to buy home ? C'est ici. Prix donné par : prix = (2^nbHomeToBuy * 500)/4
	 * 
	 * @throws HeavenException
	 */
	// public void addHommeNumbre(Block block) throws HeavenException
	// {
	// int prix = (int) (Math.pow(2.0, _nbHome + 1) * 500) / 4;
	//
	// if ((_lastInteractBlock == null) || !Stuff.blocksEquals(_lastInteractBlock, block))
	// {
	// setInteractBlock(block);
	// int homeNombre = _nbHome + 1;
	// ChatUtil.sendMessage(Bukkit.getPlayer(_name), "Cliquez une seconde fois pour confirmer l'achat du home "
	// + homeNombre + " pour {" + prix + "} Jetons.");
	// return;
	// }
	//
	// setInteractBlock(null);
	//
	// updateBalance(-prix);
	//
	// _nbHome++;
	//
	// ChatUtil.sendMessage(Bukkit.getPlayer(_name), "Vous êtes maintenant l'heureux propriétaire de " + _nbHome
	// + " homes , cela vous a couté {" + prix + "} Jetons !");
	// stateBalance();
	// }

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
			PreparedStatement ps = ConnectionManager.getConnection().prepareStatement(
					"UPDATE users SET homeNumber = homeNumber + 1 WHERE id = ? LIMIT 1");
			ps.setInt(1, _id);
			ps.executeUpdate();

			_homeNumber++;
		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
			throw new SQLErrorException();
		}
	}

	public Location getHome(int nb) throws HeavenException
	{
		if (nb < 1 || nb > _homeNumber)
			throw new HeavenException("Vous n'avez pas acheté le {home %1$d}.", nb);

		try
		{
			PreparedStatement ps = ConnectionManager.getConnection().prepareStatement(
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
			throw new SQLErrorException();
		}
	}

	public void setHome(int nb, Location home) throws HeavenException
	{
		// if (nb < 1 || nb > _homeNumber)
		// throw new HeavenException("Vous n'avez pas acheté le {home %1$d}.", nb);

		try
		{
			PreparedStatement ps = ConnectionManager
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
			throw new SQLErrorException();
		}
	}
}