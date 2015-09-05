package fr.heavencraft.heavenrp.database.users;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

import fr.heavencraft.async.queries.AbstractQuery;
import fr.heavencraft.heavenrp.HeavenRP;

public class UpdateLastLoginQuery extends AbstractQuery
{
	private static final String QUERY = "UPDATE users SET last_login = ? WHERE id = ? LIMIT 1;";

	private final int id;
	private final Timestamp lastLogin;

	public UpdateLastLoginQuery(int id, Timestamp lastLogin)
	{
		this.id = id;
		this.lastLogin = lastLogin;
	}

	@Override
	public void executeQuery() throws SQLException
	{
		try (final PreparedStatement ps = HeavenRP.getConnection().prepareStatement(QUERY))
		{
			ps.setTimestamp(1, lastLogin);
			ps.setInt(2, id);
			System.out.println("Executing query " + ps);
			ps.executeUpdate();
		}
	}
}