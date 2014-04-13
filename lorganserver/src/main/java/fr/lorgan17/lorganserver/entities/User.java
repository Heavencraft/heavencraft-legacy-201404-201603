package fr.lorgan17.lorganserver.entities;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import fr.lorgan17.lorganserver.LorganServer;
import fr.lorgan17.lorganserver.exceptions.LorganException;
import fr.lorgan17.lorganserver.exceptions.UserNotFoundException;

public class User {

	private int _id;
	private String _name;
	private String _color;
	private boolean _banned;
	private String _bannedReason;
	
	private User(int id, String name, String color, boolean banned, String bannedReason)
	{
		_id = id;
		_name = name;
		_color = color;
		_banned = banned;
		_bannedReason = bannedReason;
	}
	
	public int getId()
	{
		return _id;
	}
	
	public String getColor()
	{
		return "¤" + _color;
	}
	
	public boolean isBanned()
	{
		return _banned;
	}
	
	public String getBannedReason()
	{
		return _bannedReason;
	}
	
	public void ban(String reason)
	{
		try
		{
			PreparedStatement ps = LorganServer.getConnection().prepareStatement("UPDATE users SET banned = 1, banned_reason = ? WHERE id = ?");
			ps.setString(1, reason);
			ps.setInt(2, _id);
			
			ps.executeUpdate();
		}
		
		catch (SQLException ex)
		{
			ex.printStackTrace();
		}
	}
	
	public void unban()
	{
		try
		{
			PreparedStatement ps = LorganServer.getConnection().prepareStatement("UPDATE users SET banned = 0, banned_reason = '' WHERE id = ?");
			ps.setInt(1, _id);
			
			ps.executeUpdate();
		}
		
		catch (SQLException ex)
		{
			ex.printStackTrace();
		}
	}
	
	public static void createUser(String name)
	{
		try
		{
			PreparedStatement ps = LorganServer.getConnection().prepareStatement("INSERT INTO users (name) VALUES (?);");
			ps.setString(1, name);
			
			ps.executeUpdate();
		}
		
		catch (SQLException ex)
		{
			ex.printStackTrace();
		}
	}
	
	public static User getUserByName(String name) throws LorganException
	{
		try
		{
			PreparedStatement ps = LorganServer.getConnection().prepareStatement("SELECT u.id, u.color, u.banned, u.banned_reason FROM users u WHERE u.name = ? LIMIT 1");
			ps.setString(1, name);
			
			ResultSet rs = ps.executeQuery();
			
			if (!rs.next())
				throw new UserNotFoundException(name);
			
			return new User(rs.getInt(1), name, rs.getString(2), rs.getBoolean(3), rs.getString(4));
		}
		
		catch (SQLException ex)
		{
			ex.printStackTrace();
			throw new UserNotFoundException(name);
		}
	}
	
	
}
