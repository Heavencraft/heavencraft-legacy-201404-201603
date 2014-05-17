package fr.heavencraft.heavenproxy.users;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import fr.heavencraft.heavenproxy.HeavenProxy;
import fr.heavencraft.heavenproxy.exceptions.HeavenException;
import fr.heavencraft.heavenproxy.exceptions.SQLErrorException;
import fr.heavencraft.heavenproxy.exceptions.UserNotFoundException;

public class UserProvider
{
	private static Map<String, User> _usersByUuid = new ConcurrentHashMap<String, User>();
	private static Map<String, User> _usersByName = new ConcurrentHashMap<String, User>();

	private static void addToCache(User user)
	{
		_usersByUuid.put(user.getUuid(), user);
		_usersByName.put(user.getName(), user);
	}

	static void removeFromCache(User user)
	{
		_usersByUuid.remove(user.getUuid());
		_usersByName.remove(user.getName());
	}

	public static User getUserByUuid(String uuid) throws HeavenException
	{
		User user = _usersByUuid.get(uuid);

		if (user != null)
			return user;

		try (PreparedStatement ps = HeavenProxy.getConnection().prepareStatement(
				"SELECT * FROM users WHERE uuid = ? LIMIT 1"))
		{
			ps.setString(1, uuid);
			ResultSet rs = ps.executeQuery();

			if (!rs.next())
				throw new UserNotFoundException(uuid);

			user = new User(rs);
			addToCache(user);
			return user;
		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
			throw new SQLErrorException();
		}
	}

	public static User getUserByName(String name) throws HeavenException
	{
		User user = _usersByName.get(name);

		if (user != null)
			return user;

		try (PreparedStatement ps = HeavenProxy.getConnection().prepareStatement(
				"SELECT * FROM users WHERE name = ? LIMIT 1"))
		{
			ps.setString(1, name);
			ResultSet rs = ps.executeQuery();

			if (!rs.next())
				throw new UserNotFoundException(name);

			user = new User(rs);
			addToCache(user);
			return user;
		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
			throw new SQLErrorException();
		}
	}

	static void createUser(String uuid, String name) throws HeavenException
	{
		try (PreparedStatement ps = HeavenProxy.getConnection().prepareStatement(
				"INSERT INTO users (uuid, name, last_login) VALUES (?, ?, ?)"))
		{
			ps.setString(1, uuid);
			ps.setString(2, name);
			ps.setTimestamp(3, new Timestamp(new Date().getTime()));

			ps.executeUpdate();
		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
			throw new SQLErrorException();
		}
	}
}