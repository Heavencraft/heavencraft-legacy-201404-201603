package fr.heavencraft.heavenguard.bukkit;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.bukkit.Bukkit;

import fr.heavencraft.HeavenPlugin;
import fr.heavencraft.heavenguard.api.RegionProvider;
import fr.heavencraft.heavenguard.bukkit.commands.RegionCommand;
import fr.heavencraft.heavenguard.bukkit.listeners.PlayerListener;
import fr.heavencraft.heavenguard.datamodel.SQLRegionProvider;

/*
 * Database looks like :
 * regions (
 *   id, name, parent_id,
 *   world, min_x, min_y, min_z, max_x, max_y, max_z,
 *   ... +flags
 * )
 * 
 * hg_regions_members (region_id, user_id, owner)
 * 
 * hg_uuid (id, uuid, last_name)
 * 
 */
public class HeavenGuard extends HeavenPlugin
{
	private static final String DB_URL = "jdbc:mysql://localhost:3306/%1$s?user=mc-sql&password=9e781e41f865901850d5c3060063c8ca&zeroDateTimeBehavior=convertToNull&?autoReconnect=true";

	private static Connection connection;
	private static RegionProvider regionProvider = new SQLRegionProvider();

	private static String database;

	@Override
	public void onEnable()
	{
		super.onEnable();

		// TODO : load from config
		database = "test";

		new PlayerListener();
		new RegionCommand();
	}

	public static Connection getConnection()
	{
		try
		{
			if (connection == null || connection.isClosed())
			{
				connection = DriverManager.getConnection(String.format(DB_URL, database));
			}
		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
			Bukkit.shutdown();
		}

		return connection;
	}

	public static RegionProvider getRegionProvider()
	{
		return regionProvider;
	}

}