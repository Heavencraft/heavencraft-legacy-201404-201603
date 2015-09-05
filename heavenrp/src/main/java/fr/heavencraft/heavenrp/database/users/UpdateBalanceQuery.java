package fr.heavencraft.heavenrp.database.users;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import fr.heavencraft.async.queries.AbstractQuery;
import fr.heavencraft.heavenrp.HeavenRP;

public class UpdateBalanceQuery extends AbstractQuery
{
	private static final String QUERY = "UPDATE users SET balance = ? WHERE id = ? LIMIT 1;";

	private final int id;
	private final int balance;

	public UpdateBalanceQuery(int id, int balance)
	{
		this.id = id;
		this.balance = balance;
	}

	@Override
	public void executeQuery() throws SQLException
	{
		try (final PreparedStatement ps = HeavenRP.getConnection().prepareStatement(QUERY))
		{
			ps.setInt(1, balance);
			ps.setInt(2, id);
			System.out.println("Executing query " + ps);
			ps.executeUpdate();
		}
	}
}