package fr.heavencraft.api.providers.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.bukkit.Bukkit;

import fr.heavencraft.utils.DevUtil;

public class DefaultConnectionProvider implements ConnectionProvider
{
	private static final String DB_URL = "jdbc:mysql://localhost:3306/%1$s?user=mc-sql&password=9e781e41f865901850d5c3060063c8ca&zeroDateTimeBehavior=convertToNull&?autoReconnect=true";

	private final Map<Database, Connection> connections = new ConcurrentHashMap<Database, Connection>();

	@Override
	public Connection getConnection(Database database)
	{
		Connection connection = connections.get(database);

		try
		{
			if (connection == null || connection.isClosed())
				connection = DriverManager.getConnection(String.format(DB_URL, database.getDatabaseName()));
		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
			Bukkit.shutdown();
		}

		DevUtil.logInfo("Using connection %1$s", database);
		return connection;
	}

	@Override
	public void clearCache()
	{
		// DO NOTHING
	}
}