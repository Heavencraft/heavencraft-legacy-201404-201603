package fr.heavencraft.heavenproxy.users;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import fr.heavencraft.heavenproxy.HeavenProxy;
import fr.heavencraft.heavenproxy.exceptions.HeavenException;
import fr.heavencraft.heavenproxy.exceptions.SQLErrorException;

public class User
{
	private final String _uuid;
	private String _name;
	private final String _color;
	private Timestamp _lastLogin;

	User(ResultSet rs) throws HeavenException
	{
		try
		{
			_uuid = rs.getString("uuid");
			_name = rs.getString("name");
			_color = rs.getString("color");
			_lastLogin = rs.getTimestamp("last_login");
		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
			throw new SQLErrorException();
		}
	}

	public String getUuid()
	{
		return _uuid;
	}

	public String getName()
	{
		return _name;
	}

	void setName(String name) throws HeavenException
	{
		if (_name.equals(name))
			return;

		try (PreparedStatement ps = HeavenProxy.getConnection().prepareStatement(
				"UPDATE users SET name = ? WHERE uuid = ? LIMIT 1"))
		{
			ps.setString(1, name);
			ps.setString(2, _uuid);
			ps.executeUpdate();

			UserProvider.removeFromCache(this);
			_name = name;
		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
			throw new SQLErrorException();
		}
	}

	public String getColor()
	{
		return "§" + _color;
	}

	public Timestamp getLastLogin()
	{
		return _lastLogin;
	}

	void setLastLogin(Timestamp lastLogin) throws HeavenException
	{
		if (_lastLogin.equals(lastLogin))
			return;

		try (PreparedStatement ps = HeavenProxy.getConnection().prepareStatement(
				"UPDATE users SET last_login = ? WHERE uuid = ? LIMIT 1"))
		{
			ps.setTimestamp(1, lastLogin);
			ps.setString(2, _uuid);
			ps.executeUpdate();

			_lastLogin = lastLogin;
		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
			throw new SQLErrorException();
		}
	}
}