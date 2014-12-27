package fr.heavencraft.heavenguard.bukkit;

import java.sql.Connection;

import fr.heavencraft.HeavenPlugin;
import fr.heavencraft.api.providers.connection.ConnectionProvider.Database;
import fr.heavencraft.heavenguard.api.RegionManager;
import fr.heavencraft.heavenguard.api.RegionProvider;
import fr.heavencraft.heavenguard.bukkit.commands.RegionCommand;
import fr.heavencraft.heavenguard.bukkit.listeners.BlockListener;
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
	private static HeavenGuard instance;

	public static HeavenGuard getInstance()
	{
		return instance;
	}

	private static RegionProvider regionProvider = new SQLRegionProvider();
	private static RegionManager regionManager = new RegionManager(regionProvider);

	private static String database;

	@Override
	public void onEnable()
	{
		instance = this;

		super.onEnable();

		new PlayerListener();
		new BlockListener();

		new RegionCommand();
	}

	public static Connection getConnection()
	{
		return instance.getConnectionProvider().getConnection(Database.TEST);
	}

	public static RegionProvider getRegionProvider()
	{
		return regionProvider;
	}

	public static RegionManager getRegionManager()
	{
		return regionManager;
	}
}