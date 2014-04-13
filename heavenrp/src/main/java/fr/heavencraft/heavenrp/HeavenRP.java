package fr.heavencraft.heavenrp;

import java.util.Random;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import fr.heavencraft.heavenrp.stores.StoresListener;
import fr.heavencraft.heavenrp.stores.StoresManager;
import fr.lorgan17.heavenrp.listeners.JumpListener;
import fr.lorgan17.heavenrp.listeners.LampadaireListener;
import fr.lorgan17.heavenrp.listeners.LinkListener;
import fr.lorgan17.heavenrp.listeners.PVP4Manager;
import fr.lorgan17.heavenrp.listeners.PVPManager;
import fr.lorgan17.heavenrp.listeners.RedstoneLampListener;
import fr.lorgan17.heavenrp.listeners.SnowballListener;
import fr.lorgan17.heavenrp.managers.AuctionManager;
import fr.lorgan17.heavenrp.managers.SaveManager;

public class HeavenRP extends JavaPlugin {

	private final static String RP_DB_URL = "jdbc:mysql://localhost:3306/minecraft-semirp?user=mc-sql&password=MfGJQMBzmAS5xYhH&zeroDateTimeBehavior=convertToNull&?autoReconnect=true";
	private final static String MAIN_DB_URL = "jdbc:mysql://localhost:3306/mc-db?user=mc-sql&password=MfGJQMBzmAS5xYhH&zeroDateTimeBehavior=convertToNull&?autoReconnect=true";
	//private final static String RP_DB_URL = "jdbc:mysql://localhost:3306/minecraft-rp?user=root&password=root";
	//private final static String MAIN_DB_URL = "jdbc:mysql://localhost:3306/mc-db?user=root&password=root";
	
	public static final String Moderator = "heavenrp.moderator";
	
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

		// Stores
		new StoresListener(this);
		_storesManager = new StoresManager(this);
		_storesManager.init();
		
		
		// Listeners
		new JumpListener(this);
		new LampadaireListener(this);
		new LinkListener(this);
		new RedstoneLampListener(this);
		new SnowballListener(this);
		//new AdminShop();
		
		// Managers
		new PVP4Manager(this);
		new PVPManager(this);
		new SaveManager();
		
		_auctionManager = new AuctionManager();
		
	}
	
	@Override
	public void onDisable()
	{
		super.onDisable();
		
		SaveManager.save();
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
}