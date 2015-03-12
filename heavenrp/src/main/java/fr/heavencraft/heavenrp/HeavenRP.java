package fr.heavencraft.heavenrp;

import java.sql.Connection;
import java.util.Random;

import org.bukkit.plugin.Plugin;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;

import fr.heavencraft.HeavenPlugin;
import fr.heavencraft.api.providers.connection.ConnectionHandler;
import fr.heavencraft.api.providers.connection.ConnectionHandlerFactory;
import fr.heavencraft.api.providers.connection.Database;
import fr.heavencraft.heavenrp.stores.StoresListener;
import fr.heavencraft.heavenrp.stores.StoresManager;
import fr.lorgan17.heavenrp.managers.AuctionManager;
import fr.manu67100.heavenrp.laposte.Files;

public class HeavenRP extends HeavenPlugin
{
	private static WorldGuardPlugin _WGP;
	private static HeavenRP _instance;

	private static ConnectionHandler srpConnection;
	private static ConnectionHandler mainConnection;

	private static StoresManager _storesManager;
	private static AuctionManager _auctionManager;

	public static Random Random = new Random();

	@Override
	public void onEnable()
	{
		super.onEnable();

		_instance = this;
		srpConnection = ConnectionHandlerFactory.getConnectionHandler(Database.SEMIRP);
		mainConnection = ConnectionHandlerFactory.getConnectionHandler(Database.WEB);

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
		return srpConnection.getConnection();
	}

	@Deprecated
	public static Connection getMainConnection()
	{
		return mainConnection.getConnection();
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
			final Plugin plugin = getInstance().getServer().getPluginManager().getPlugin("WorldGuard");
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