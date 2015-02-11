package fr.tenkei.creaplugin.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.bukkit.Bukkit;

public class ConnectionManager
{
	private static final String FUN_DB_URL = "jdbc:mysql://localhost:3306/minecraft-creative-old?user=mc-sql&password=9e781e41f865901850d5c3060063c8ca";
	private static final String MAIN_DB_URL = "jdbc:mysql://localhost:3306/mc-db?user=mc-sql&password=9e781e41f865901850d5c3060063c8ca";

	private static Connection _connection;
	private static Connection _mainConnection;

	public static Connection getConnection()
	{
		try
		{
			if (_connection == null || _connection.isClosed())
			{
				_connection = DriverManager.getConnection(FUN_DB_URL);
			}
		}
		catch (final SQLException ex)
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
		catch (final SQLException ex)
		{
			ex.printStackTrace();
			Bukkit.shutdown();
		}
		return _mainConnection;
	}
}