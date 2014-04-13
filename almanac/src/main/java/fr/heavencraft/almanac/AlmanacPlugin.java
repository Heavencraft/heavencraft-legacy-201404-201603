package fr.heavencraft.almanac;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class AlmanacPlugin extends JavaPlugin
{
	private final static String RP_DB_URL = "jdbc:mysql://localhost:3306/minecraft-semirp?user=mc-sql&password=MfGJQMBzmAS5xYhH&zeroDateTimeBehavior=convertToNull";

	private static Connection _connection;
	private static AlmanacPlugin _instance;

	@Override
	public void onEnable()
	{
		super.onEnable();

		_instance = this;

		new AlmanacListener();
		new AlmanacCommand();
	}

	public static AlmanacPlugin getInstance()
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