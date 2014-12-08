package fr.lorgan17.lorganserver.entities;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import fr.heavencraft.exceptions.HeavenException;
import fr.heavencraft.exceptions.UserNotFoundException;
import fr.lorgan17.lorganserver.LorganServer;

public class User
{

	private final int _id;
	private final String _name;

	private User(int id, String name)
	{
		_id = id;
		_name = name;
	}

	public int getId()
	{
		return _id;
	}

	public static void createUser(String name)
	{
		try (PreparedStatement ps = LorganServer.getConnection().prepareStatement(
				"INSERT INTO users (name) VALUES (?);"))
		{
			ps.setString(1, name);
			ps.executeUpdate();
		}

		catch (SQLException ex)
		{
			ex.printStackTrace();
		}
	}

	public static User getUserByName(String name) throws HeavenException
	{
		try (PreparedStatement ps = LorganServer.getConnection().prepareStatement(
				"SELECT * FROM users WHERE name = ? LIMIT 1"))
		{
			ps.setString(1, name);

			try (ResultSet rs = ps.executeQuery())
			{
				if (!rs.next())
					throw new UserNotFoundException(name);

				return new User(rs.getInt(1), name);
			}
		}

		catch (SQLException ex)
		{
			ex.printStackTrace();
			throw new UserNotFoundException(name);
		}
	}

}
