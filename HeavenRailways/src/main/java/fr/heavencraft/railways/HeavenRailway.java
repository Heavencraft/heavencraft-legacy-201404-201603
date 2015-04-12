package fr.heavencraft.railways;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import fr.heavencraft.InitManager;

public class HeavenRailway extends JavaPlugin {
	private static HeavenRailway _instance;
	
	private final static String RP_DB_URL = "jdbc:mysql://localhost:3306/minecraft-semirp-test?user=mc-sql&password=9e781e41f865901850d5c3060063c8ca&zeroDateTimeBehavior=convertToNull&?autoReconnect=true";
	private static Connection _connection;
	
	@Override
	public void onEnable()
	{
		super.onEnable();
		_instance = this;
		
		// Charger tout les listners 
		InitManager.init();
	}
	
	@Override
    public void onDisable() {
    }

	public static HeavenRailway getInstance()
	{
		return _instance;
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
	
}
