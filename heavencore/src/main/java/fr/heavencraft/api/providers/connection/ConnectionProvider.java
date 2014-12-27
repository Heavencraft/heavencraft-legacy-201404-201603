package fr.heavencraft.api.providers.connection;

import java.sql.Connection;

import fr.heavencraft.api.providers.Provider;

public interface ConnectionProvider extends Provider
{
	public enum Database
	{
		PROXY("proxy"), //
		SEMIRP("minecraft-semirp"), //
		TEST("test");

		private final String database;

		private Database(String database)
		{
			this.database = database;
		}

		public String getDatabaseName()
		{
			return database;
		}
	}

	Connection getConnection(Database database);
}