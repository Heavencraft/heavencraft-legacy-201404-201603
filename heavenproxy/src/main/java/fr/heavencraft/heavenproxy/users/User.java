package fr.heavencraft.heavenproxy.users;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import fr.heavencraft.heavenproxy.exceptions.HeavenException;
import fr.heavencraft.heavenproxy.exceptions.SQLErrorException;

public class User
{
	private final String uuid;
	private final String name;
	private final String color;
	private final String lastIp;
	private final Date lastLogin;

	User(ResultSet rs) throws HeavenException
	{
		try
		{
			uuid = rs.getString("uuid");
			name = rs.getString("name");
			color = rs.getString("color");
			lastIp = rs.getString("last_ip");
			lastLogin = rs.getTimestamp("last_login");
		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
			throw new SQLErrorException();
		}
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
