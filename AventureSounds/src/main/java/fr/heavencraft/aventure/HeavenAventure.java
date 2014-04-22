package fr.heavencraft.aventure;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import com.sk89q.worldedit.Vector;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;

import static com.sk89q.worldguard.bukkit.BukkitUtil.*;

import org.bukkit.plugin.Plugin;

import fr.heavencraft.aventure.WorldGuardRegions.WGRegionEventsListener;
import fr.heavencraft.aventure.commands.CommandManager;
import fr.heavencraft.aventure.listeners.PlayerListener;

public class HeavenAventure extends JavaPlugin
{
	private static HeavenAventure _instance;
	private static Connection _mainConnection;
	private static WorldGuardPlugin _WGP;

	@Override
	public void onEnable()
	{
		_instance = this;

//		getConfig().options().copyDefaults(true);
		Files.getRegions().options().copyDefaults(true);
		Files.saveRegions();
//		Files.saveConfig();

		new CommandManager();	
		new PlayerListener();
		new WGRegionEventsListener();
	}

	public static HeavenAventure getInstance()
	{
		return _instance;
	}

	public static Connection getMainConnection() {
		try {
			if ((_mainConnection == null) || (_mainConnection.isClosed())) {
				_mainConnection = DriverManager
						.getConnection("jdbc:mysql://localhost:3306/mc-db?user=mc-sql&password=MfGJQMBzmAS5xYhH&zeroDateTimeBehavior=convertToNull&?autoReconnect=true");
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
			Bukkit.shutdown();
		}
		return _mainConnection;
	}

	public static WorldGuardPlugin getWorldGuard() {

		if (_WGP == null)
		{
			Plugin plugin = getInstance().getServer().getPluginManager().getPlugin("WorldGuard");
			if (plugin == null || !(plugin instanceof WorldGuardPlugin)) {
				_WGP = null;
			}
			else
			{
				_WGP = (WorldGuardPlugin) plugin;
			}			
		}
		return _WGP;
	}

	public boolean isWithinRegion(Player player, String region)
	{ return isWithinRegion(player.getLocation(), region); }

	public boolean isWithinRegion(Block block, String region)
	{ return isWithinRegion(block.getLocation(), region); }

	public boolean isWithinRegion(Location loc, String region)
	{
		WorldGuardPlugin guard = getWorldGuard();
		Vector v = toVector(loc);
		RegionManager manager = guard.getRegionManager(loc.getWorld());
		ApplicableRegionSet set = manager.getApplicableRegions(v);
		for (ProtectedRegion each : set)
			if (each.getId().equalsIgnoreCase(region))
				return true;
		return false;
	}
}