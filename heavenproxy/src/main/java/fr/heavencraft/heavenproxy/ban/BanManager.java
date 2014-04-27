package fr.heavencraft.heavenproxy.ban;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import fr.heavencraft.heavenproxy.HeavenProxy;
import fr.heavencraft.heavenproxy.Utils;
import fr.heavencraft.heavenproxy.exceptions.HeavenException;
import fr.heavencraft.heavenproxy.exceptions.SQLErrorException;

public class BanManager
{
	public static void banPlayer(String name, String bannedBy, String reason) throws HeavenException
	{
		try
		{
			String uuid = Utils.getUUID(name);

			PreparedStatement ps = HeavenProxy.getConnection().prepareStatement(
					"REPLACE INTO banlist (uuid, name, banned_by, reason) VALUES (?, ?, ?, ?);");
			ps.setString(1, uuid);
			ps.setString(2, name.toLowerCase());
			ps.setString(3, bannedBy);
			ps.setString(4, reason);

			ps.executeUpdate();
			ps.close();
		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
			throw new SQLErrorException();
		}
	}

	public static void unbanPlayer(String name) throws HeavenException
	{
		try
		{
			String uuid = Utils.getUUID(name);
			
			PreparedStatement ps = HeavenProxy.getConnection().prepareStatement("DELETE FROM banlist WHERE uuid = ?;");
			ps.setString(1, uuid);

			ps.executeUpdate();
			ps.close();
		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
			throw new SQLErrorException();
		}
	}

	public static String getReason(String uuid)
	{
		try
		{
			PreparedStatement ps = HeavenProxy.getConnection().prepareStatement(
					"SELECT CONCAT_WS(' : ', banned_by, reason) FROM banlist WHERE uuid = ?;");
			ps.setString(1, uuid);

			ResultSet rs = ps.executeQuery();

			if (!rs.next())
				return null;

			return rs.getString(1);
		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
			return null;
		}
	}
}
