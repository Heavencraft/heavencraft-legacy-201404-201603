package fr.lorgan17.lorganserver.entities;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import fr.heavencraft.exceptions.HeavenException;
import fr.heavencraft.exceptions.UserNotFoundException;
import fr.lorgan17.lorganserver.LorganServer;

public class User
{

	private final int _id;

	private User(int id)
	{
		_id = id;
	}

	public int getId()
	{
		return _id;
	}

	public void updateName(String name)
	{
		try (PreparedStatement ps = LorganServer.getConnection().prepareStatement(
				"UPDATE users SET name = ? WHERE id = ? LIMIT 1;"))
		{
			ps.setString(1, name);
			ps.setInt(2, _id);
			ps.executeUpdate();
		}

		catch (final SQLException ex)
		{
			ex.printStackTrace();
		}
	}

	public static void createUser(UUID uuid, String name)
	{
		try (PreparedStatement ps = LorganServer.getConnection()
				.prepareStatement("INSERT INTO users (uuid, name) VALUES (?, ?);"))
		{
			ps.setString(1, uuid.toString());
			ps.setString(2, name);
			ps.executeUpdate();
		}

		catch (final SQLException ex)
		{
			ex.printStackTrace();
		}
	}

	public static User getUserByName(String name) throws HeavenException
	{
		try (PreparedStatement ps = LorganServer.getConnection().prepareStatement("SELECT * FROM users WHERE name = ? LIMIT 1"))
		{
			ps.setString(1, name);

			try (ResultSet rs = ps.executeQuery())
			{
				if (!rs.next())
					throw new UserNotFoundException(name);

				return new User(rs.getInt("id"));
			}
		}

		catch (final SQLException ex)
		{
			ex.printStackTrace();
			throw new UserNotFoundException(name);
		}
	}

	public static User getUserByUniqueId(UUID uuid) throws HeavenException
	{
		try (PreparedStatement ps = LorganServer.getConnection().prepareStatement("SELECT * FROM users WHERE uuid = ? LIMIT 1"))
		{
			ps.setString(1, uuid.toString());

			try (ResultSet rs = ps.executeQuery())
			{
				if (!rs.next())
					throw new UserNotFoundException(uuid);

				return new User(rs.getInt("id"));
			}
		}

		catch (final SQLException ex)
		{
			ex.printStackTrace();
			throw new UserNotFoundException(uuid);
		}
	}
}
