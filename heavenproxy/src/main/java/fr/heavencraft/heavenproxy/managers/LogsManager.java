package fr.heavencraft.heavenproxy.managers;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import fr.heavencraft.heavenproxy.HeavenProxy;

public class LogsManager {

	public static void addKick(String moderatorName, String playerName, String reason)
	{
		reason = reason.length() > 0 ? " (" + reason + ")" : "";
		
		try
		{
			PreparedStatement ps = HeavenProxy.getMainConnection().prepareStatement("INSERT INTO heavencraft_logs(log_date, username, content_type, content) VALUES(NOW(), ?, 0, ?)");
			ps.setString(1, playerName);
			ps.setString(2, "Kicked by " + moderatorName + reason + ".");
			ps.executeUpdate();
			ps.close();
		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
		}
	}
	
	public static void addBan(String moderatorName, String playerName, String reason)
	{
		reason = reason.length() > 0 ? " (" + reason + ")" : "";
		
		try
		{
			PreparedStatement ps = HeavenProxy.getMainConnection().prepareStatement("INSERT INTO heavencraft_logs(log_date, username, content_type, content) VALUES(NOW(), ?, 0, ?)");
			ps.setString(1, playerName);
			ps.setString(2, "Banned by " + moderatorName + reason + ".");
			ps.executeUpdate();
			ps.close();
		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
		}
	}

	public static void addUnban(String moderatorName, String playerName)
	{
		try
		{
			PreparedStatement ps = HeavenProxy.getMainConnection().prepareStatement("INSERT INTO heavencraft_logs(log_date, username, content_type, content) VALUES(NOW(), ?, 0, ?)");
			ps.setString(1, playerName);
			ps.setString(2, "Unbanned by " + moderatorName + ".");
			ps.executeUpdate();
			ps.close();
		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
		}
	}
}