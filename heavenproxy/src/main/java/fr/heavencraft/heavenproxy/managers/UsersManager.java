package fr.heavencraft.heavenproxy.managers;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

import net.md_5.bungee.api.connection.ProxiedPlayer;
import fr.heavencraft.heavenproxy.HeavenProxy;
import fr.heavencraft.heavenproxy.exceptions.UserNotFoundException;

public class UsersManager
{
	public static void createUser(ProxiedPlayer player)
	{
		try
		{
			PreparedStatement ps = HeavenProxy.getConnection().prepareStatement(
					"INSERT INTO users (uuid, name, last_ip, last_login) VALUES (?, ?, ?, ?)");
			
			ps.setString(1, player.getUUID());
			ps.setString(2, player.getName());
			ps.setString(3, player.getAddress().getAddress().toString());
			ps.setTimestamp(4, new Timestamp(new Date().getTime()));

			ps.executeUpdate();
			ps.close();
		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
		}
	}

	public static void updateUser(ProxiedPlayer player)
	{
		try
		{
			PreparedStatement ps = HeavenProxy.getConnection().prepareStatement(
					"UPDATE users SET name = ?, last_ip = ?, last_login = ? WHERE uuid = ?");
			
			ps.setString(1, player.getName());
			ps.setString(2, player.getAddress().getAddress().toString());
			ps.setTimestamp(3, new Timestamp(new Date().getTime()));
			ps.setString(4, player.getUUID());

			ps.executeUpdate();
			ps.close();
		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
		}
	}

	public static User getUserByName(String name) throws UserNotFoundException
	{
		try
		{
			PreparedStatement ps = HeavenProxy.getConnection().prepareStatement(
					"SELECT * FROM users WHERE name = ? LIMIT 1");
			ps.setString(1, name);

			ResultSet rs = ps.executeQuery();

			if (rs.next())
				return new User(rs);
			else
				throw new UserNotFoundException(name);
		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
			throw new UserNotFoundException(name);
		}
	}

	public static User getUserByUuid(String uuid) throws UserNotFoundException
	{
		try
		{
			PreparedStatement ps = HeavenProxy.getConnection().prepareStatement(
					"SELECT * FROM users WHERE uuid = ? LIMIT 1");
			ps.setString(1, uuid);

			ResultSet rs = ps.executeQuery();

			if (rs.next())
				return new User(rs);
			else
				throw new UserNotFoundException(uuid);
		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
			throw new UserNotFoundException(uuid);
		}
	}

	public static class User
	{
		private final String uuid;
		private final String name;
		private final String color;
		private final String lastIp;
		private final Date lastLogin;

		private User(ResultSet rs) throws SQLException
		{
			uuid = rs.getString("uuid");
			name = rs.getString("name");
			color = rs.getString("color");
			lastIp = rs.getString("last_ip");
			lastLogin = rs.getTimestamp("last_login");
		}
		
		public String getUuid()
		{
			return uuid;
		}

		public String getName()
		{
			return name;
		}

		public String getColor()
		{
			return "§" + color;
		}
		
		public String getLastIp()
		{
			return lastIp;
		}
		
		public Date getLastLogin()
		{
			return lastLogin;
		}
	}
}