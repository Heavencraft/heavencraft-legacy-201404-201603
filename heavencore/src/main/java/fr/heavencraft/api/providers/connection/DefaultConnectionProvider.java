package fr.heavencraft.api.providers.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.bukkit.Bukkit;

import fr.heavencraft.utils.HeavenLog;

public class DefaultConnectionProvider implements ConnectionProvider
{
	private static final String DB_URL = "jdbc:mysql://localhost:3306/%1$s?user=mc-sql&password=9e781e41f865901850d5c3060063c8ca&zeroDateTimeBehavior=convertToNull&?autoReconnect=true";

	private final HeavenLog log = HeavenLog.getLogger(getClass());

	private Connection connection;
	private final Database database;

	public DefaultConnectionProvider(Database database)
	{
		this.database = database;
	}

	@Override
	public Connection getConnection()
	{
		try
		{
			if (connection == null || connection.isClosed())
			{
				connection = DriverManager.getConnection(String.format(DB_URL, database.getDatabaseName()));
				log.info("Created connection to database %1$s", database);
			}
		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
			Bukkit.shutdown();
		}

		log.info("Using connection to database %1$s", database);
		return connection;
	}

	@Override
	public void clearCache()
	{
		// DO NOTHING
	}
}