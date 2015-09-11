package fr.heavencraft.heavenrp.database.users;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

import fr.heavencraft.async.queries.AbstractQuery;
import fr.heavencraft.heavenrp.HeavenRP;
import fr.heavencraft.heavenrp.general.users.User;

public class UpdateLastLoginQuery extends AbstractQuery
{
	private static final String QUERY = "UPDATE users SET last_login = ? WHERE id = ? LIMIT 1;";

	private final User user;

	public UpdateLastLoginQuery(User user)
	{
		this.user = user;
	}

	@Override
	public void executeQuery() throws SQLException
	{
		try (PreparedStatement ps = HeavenRP.getConnection().prepareStatement(QUERY))
		{
			ps.setTimestamp(1, new Timestamp(new Date().getTime()));
			ps.setInt(2, user.getId());
			System.out.println("Executing query " + ps);
			ps.executeUpdate();
		}
	}
}