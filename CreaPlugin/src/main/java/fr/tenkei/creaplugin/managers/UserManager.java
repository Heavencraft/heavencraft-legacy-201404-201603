package fr.tenkei.creaplugin.managers;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import fr.heavencraft.exceptions.HeavenException;
import fr.heavencraft.utils.ChatUtil;
import fr.tenkei.creaplugin.exceptions.UserNotFoundException;
import fr.tenkei.creaplugin.managers.entities.User;
import fr.tenkei.creaplugin.utils.ConnectionManager;

public class UserManager
{
	private static final HashMap<String, User> _usersList = new HashMap<String, User>();

	public static User getUser(String playerName) throws HeavenException
	{
		if (isLoaded(playerName))
			return _usersList.get(playerName);
		else
		{
			initUserByName(playerName);
			User user = _usersList.get(playerName);

			if (user == null)
				throw new UserNotFoundException(playerName);

			return user;
		}
	}

	public static void saveUser(String getUser) throws HeavenException
	{
		User user = getUser(getUser);

		if (user == null)
			return;

		user.saveUser();
		_usersList.remove(getUser);
	}

	public static void initUserByName(String name) throws HeavenException
	{
		try
		{
			PreparedStatement ps = ConnectionManager.getConnection().prepareStatement(
					"SELECT u.id," + " u.jetons," + " u.nbHome, u.savedDate, u.varString"
							+ " FROM users u WHERE u.name = ? LIMIT 1");

			ps.setString(1, name);

			ResultSet rs = ps.executeQuery();

			if (!rs.next())
			{
				createUser(name);
				return;
			}

			_usersList.put(name, new User(rs.getInt(1), name, // N° et Nom
					rs.getInt(2), // Jeton
					rs.getInt(3), // Home
					rs.getString(4), rs.getString(5)));

			rs.close();
			ps.close();
		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
			throw new UserNotFoundException(name);
		}
	}

	public static void createUser(String name)
	{
		try
		{
			PreparedStatement ps = ConnectionManager.getConnection().prepareStatement(
					"INSERT INTO users (name) VALUES (?);");
			ps.setString(1, name);

			ps.executeUpdate();

			// this._bukkitPerms.getCommand("permissions").execute(
			// this._plugin.getServer().getConsoleSender(),
			// "permissions",
			// new String[] { "player", "addgroup", name, "user" });
		}

		catch (SQLException ex)
		{
			ex.printStackTrace();
		}
	}

	private static boolean isLoaded(String playerName)
	{
		return _usersList.containsKey(playerName);
	}

	public static void jetonByConnected() throws HeavenException
	{
		int jeton = _usersList.size() > 5 ? _usersList.size() : 5;

		ChatUtil.broadcastMessage("Vous venez de recevoir {" + jeton + "} Jetons.");

		for (User u : _usersList.values())
			u.updateBalance(jeton);
	}

	public static void saveAllUsers() throws HeavenException
	{
		Player[] plist = Bukkit.getServer().getOnlinePlayers();
		for (int i = 0; i < plist.length; i++)
		{
			User user = getUser(plist[i].getName());
			user.saveUser();
		}
	}
}
