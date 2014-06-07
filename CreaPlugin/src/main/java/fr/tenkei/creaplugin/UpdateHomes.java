package fr.tenkei.creaplugin;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.Location;

import fr.tenkei.creaplugin.utils.ConnectionManager;
import fr.tenkei.creaplugin.utils.Stuff;

public class UpdateHomes
{
	public static void updateAllPlayers()
	{
		try
		{
			PreparedStatement ps = ConnectionManager.getConnection().prepareStatement(
					"SELECT DISTINCT id, varString FROM users WHERE varString != ''");
			ResultSet rs = ps.executeQuery();

			while (rs.next())
			{
				updatePlayer(rs.getInt("id"), rs.getString("varString"));
			}
		}
		catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void updatePlayer(int id, String varString)
	{
		Map<String, String> map = toMap(varString);

		for (Entry<String, String> entry : map.entrySet())
		{
			if (entry.getKey().startsWith("home_"))
			{
				Location home = Stuff.stringToLocation(entry.getValue());

				if (home.getWorld() == null)
				{
					home.setWorld(Bukkit.getWorld("world_creative"));
				}

				entry.setValue(Stuff.locationToString(home));
			}
		}

		varString = fromMap(map);

		try
		{
			PreparedStatement ps = ConnectionManager.getConnection().prepareStatement(
					"UPDATE users SET varString = ? WHERE id = ?");

			ps.setString(1, varString);
			ps.setInt(2, id);
			ps.executeUpdate();

			System.out.println("Updated player " + id);
		}
		catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private static Map<String, String> toMap(String varString)
	{
		Map<String, String> result = new HashMap<String, String>();
		String[] listString = varString.split("\n");

		for (int i = 0; i < listString.length; i++)
		{
			String[] lineData = listString[i].split("=");
			if (lineData.length == 2)
			{
				String variable = lineData[0].trim();
				String value = lineData[1].trim();
				result.put(variable, value);
			}
		}

		return result;
	}

	private static String fromMap(Map<String, String> map)
	{
		String varString = "";

		for (Entry<String, String> element : map.entrySet())
		{
			varString += element.getKey() + "=" + element.getValue() + "\n";
		}

		return varString;
	}
}
