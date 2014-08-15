package fr.tenkei.creaplugin.users;

import static fr.tenkei.creaplugin.utils.ConnectionManager.getConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import fr.heavencraft.exceptions.HeavenException;
import fr.heavencraft.exceptions.SQLErrorException;
import fr.heavencraft.exceptions.UserNotFoundException;

public class UserProvider
{
	private static final Map<String, User> usersByName = new HashMap<String, User>();

	public static void createUser(String uuid, String name)
	{
		try
		{
			PreparedStatement ps = getConnection().prepareStatement("INSERT INTO users (uuid, name) VALUES (?, ?);");
			ps.setString(1, uuid);
			ps.setString(2, name);

			ps.executeUpdate();
			ps.close();
		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
		}
	}

	public static void removeFromCache(String name)
	{
		usersByName.remove(name);
	}

	public static User getUserByName(String name) throws HeavenException
	{
		User user = usersByName.get(name);

		if (user != null)
			return user;

		try
		{
			PreparedStatement ps = getConnection().prepareStatement("SELECT * FROM users WHERE name = ? LIMIT 1;");
			ps.setString(1, name);

			ResultSet rs = ps.executeQuery();

			if (!rs.next())
				throw new UserNotFoundException(name);

			user = new User(rs);

			usersByName.put(name, user);
			return user;
		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
			throw new SQLErrorException();
		}
	}

	public static void updateName(String uuid, String name) throws HeavenException
	{
		try
		{
			PreparedStatement ps = getConnection().prepareStatement("UPDATE users SET name = ? WHERE uuid = ? LIMIT 1");
			ps.setString(1, name);
			ps.setString(2, uuid);

			if (ps.executeUpdate() != 1)
				throw new UserNotFoundException(name);

			ps.close();
		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
			throw new SQLErrorException();
		}
	}
}