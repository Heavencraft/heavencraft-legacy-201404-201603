package fr.heavencraft.heavenproxy.ban;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import fr.heavencraft.heavenproxy.HeavenProxy;
import fr.heavencraft.heavenproxy.Utils;

public class BanManager {
	
	public static void banPlayer(String name, String bannedBy, String reason)
	{
		try
		{
			String uuid = Utils.getUUID(name);
			
			PreparedStatement ps = HeavenProxy.getConnection().prepareStatement("REPLACE INTO banlist (uuid, name, banned_by, reason) VALUES (?, ?, ?, ?);");

			ps.setString(1, uuid);
			ps.setString(2, name.toLowerCase());
			ps.setString(3, bannedBy);
			ps.setString(4, reason);

			ps.executeUpdate();
		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
		}
	}
	
	public static void unbanPlayer(String name)
	{
		try
		{
			PreparedStatement ps = HeavenProxy.getConnection().prepareStatement("DELETE FROM banlist WHERE name = ?;");
			ps.setString(1, name.toLowerCase());
			
			ps.executeUpdate();
		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
		}
	}
	
	public static boolean isBanned(String name)
	{
		try
		{
			PreparedStatement ps = HeavenProxy.getConnection().prepareStatement("SELECT reason FROM banlist WHERE name = ?;");
			ps.setString(1, name.toLowerCase());
			
			ResultSet rs = ps.executeQuery();
			
			return rs.next();
		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
			return true;
		}
	}
	
	public static String getReason(String name)
	{
		try
		{
			PreparedStatement ps = HeavenProxy.getConnection().prepareStatement("SELECT CONCAT_WS(' : ', banned_by, reason) FROM banlist WHERE name = ?;");
			ps.setString(1, name.toLowerCase());
			
			ResultSet rs = ps.executeQuery();
			
			if (!rs.next())
				return "";
			
			return rs.getString(1);
		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
			return "";
		}
	}
}
