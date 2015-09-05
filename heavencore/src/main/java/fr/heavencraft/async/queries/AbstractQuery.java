package fr.heavencraft.async.queries;

import java.sql.SQLException;

public abstract class AbstractQuery implements Query
{
	@Override
	public void onSQLException(SQLException ex)
	{
	}
}