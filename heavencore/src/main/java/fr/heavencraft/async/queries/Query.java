package fr.heavencraft.async.queries;

import java.sql.SQLException;

public interface Query
{
	void executeQuery() throws SQLException;

	void onSQLException(SQLException ex);
}