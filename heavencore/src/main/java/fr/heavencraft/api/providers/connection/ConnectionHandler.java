package fr.heavencraft.api.providers.connection;

import java.sql.Connection;

public interface ConnectionHandler
{
	Connection getConnection();
}