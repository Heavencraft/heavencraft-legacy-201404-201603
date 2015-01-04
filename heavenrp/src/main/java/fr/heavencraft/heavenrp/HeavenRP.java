package fr.heavencraft.heavenrp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;

import fr.heavencraft.HeavenPlugin;
import fr.heavencraft.heavenrp.stores.StoresListener;
import fr.heavencraft.heavenrp.stores.StoresManager;
import fr.lorgan17.heavenrp.managers.AuctionManager;
import fr.manu67100.heavenrp.laposte.Files;

public class HeavenRP extends HeavenPlugin
{
	// For test server
	 private final static String RP_DB_URL = "jdbc:mysql://localhost:3306/minecraft-semirp-test?user=mc-sql&password=9e781e41f865901850d5c3060063c8ca&zeroDateTimeBehavior=convertToNull&?autoReconnect=true";

	//private final static String RP_DB_URL = "jdbc:mysql://localhost:3306/minecraft-semirp?user=mc-sql&password=9e781e41f865901850d5c3060063c8ca&zeroDateTimeBehavior=convertToNull&?autoReconnect=true";
	private final static String MAIN_DB_URL = "jdbc:mysql://localhost:3306/mc-db?user=mc-sql&password=9e781e41f865901850d5c3060063c8ca&zeroDateTimeBehavior=convertToNull&?autoReconnect=true";
	// private final static String RP_DB_URL = "jdbc:mysql://localhost:3306/minecraft-rp?user=root&password=root";
	// private final static String MAIN_DB_URL = "jdbc:mysql://localhost:3306/mc-db?user=root&password=root";
	private static WorldGuardPlugin _WGP;
	private static HeavenRP _instance;

	private static Connection _connection;
	private static Connection _mainConnection;

	private static StoresManager _storesManager;
	private static AuctionManager _auctionManager;

	public static Random Random = new Random();

	@Override
	public void onEnable()
	{
		super.onEnable();

		_instance = this;

		InitManager.init();

		_auctionManager = new AuctionManager();

		// Stores
		new StoresListener(this);
		_storesManager = new StoresManager(this);
		_storesManager.init();

		// La Poste
		Files.getRegions().options().copyDefaults(true);
		Files.saveRegions();

	}

	public static Connection getConnection()
	{
		try
		{
			if (_connection == null || _connection.isClosed())
			{
				_connection = DriverManager.getConnection(RP_DB_URL);
			}
		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
			Bukkit.shutdown();
		}

		return _connection;
	}

	public static Connection getMainConnection()
	{
		try
		{
			if (_mainConnection == null || _mainConnection.isClosed())
			{
				_mainConnection = DriverManager.getConnection(MAIN_DB_URL);
			}
		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
			Bukkit.shutdown();
		}

		return _mainConnection;
	}

	public static HeavenRP getInstance()
	{
		return _instance;
	}

	public StoresManager getStoresManager()
	{
		return _storesManager;
	}

	public static AuctionManager getAuctionManager()
	{
		return _auctionManager;
	}

	public static WorldGuardPlugin getWorldGuard()
	{

		if (_WGP == null)
		{
			Plugin plugin = getInstance().getServer().getPluginManager().getPlugin("WorldGuard");
			if (plugin == null || !(plugin instanceof WorldGuardPlugin))
			{
				_WGP = null;
			}
			else
			{
				_WGP = (WorldGuardPlugin) plugin;
			}
		}
		return _WGP;
	}
}