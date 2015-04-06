package fr.heavencraft.Utils;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import fr.heavencraft.exceptions.HeavenException;
import fr.heavencraft.exceptions.SQLErrorException;
import fr.heavencraft.exceptions.UserNotFoundException;
import fr.heavencraft.railways.HeavenRailway;

public class UserProvider
{
	private static Map<String, User> usersByName = new HashMap<String, User>();

	public static void removeFromCache(String name)
	{
		usersByName.remove(name);
	}

	public static User getUserByName(String name) throws HeavenException
	{
		User user = usersByName.get(name);

		if (user != null)
			return user;

		try (PreparedStatement ps = HeavenRailway.getConnection().prepareStatement("SELECT * FROM users WHERE name = ? LIMIT 1;"))
		{
			ps.setString(1, name);

			final ResultSet rs = ps.executeQuery();

			if (!rs.next())
				throw new UserNotFoundException(name);

			user = new User(rs);

			usersByName.put(name, user);
			return user;
		}

		catch (final SQLException ex)
		{
			ex.printStackTrace();
			throw new SQLErrorException();
		}
	}

	/**
	 * Returns an User using his uuid. Remark: This function does not add the
	 * user to the cache.
	 * 
	 * @param uuid
	 * @return
	 * @throws HeavenException
	 */
	public static User getUserByUUID(String uuid) throws HeavenException
	{
		User user = null;
		try (PreparedStatement ps = HeavenRailway.getConnection().prepareStatement("SELECT * FROM users WHERE uuid = ? LIMIT 1;"))
		{
			ps.setString(1, uuid);

			final ResultSet rs = ps.executeQuery();

			if (!rs.next())
				throw new UserNotFoundException(uuid);

			user = new User(rs);
			return user;
		}

		catch (final SQLException ex)
		{
			ex.printStackTrace();
			throw new SQLErrorException();
		}
	}
}