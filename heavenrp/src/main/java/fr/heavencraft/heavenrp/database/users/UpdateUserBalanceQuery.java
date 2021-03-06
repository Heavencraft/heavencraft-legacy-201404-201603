package fr.heavencraft.heavenrp.database.users;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import fr.heavencraft.async.queries.AbstractQuery;
import fr.heavencraft.exceptions.HeavenException;
import fr.heavencraft.heavenrp.HeavenRP;

public class UpdateUserBalanceQuery extends AbstractQuery
{
	private static final String QUERY = "UPDATE users SET balance = balance + ? WHERE id = ? AND balance + ? >= 0 LIMIT 1;";

	private final User user;
	private final int delta;

	public UpdateUserBalanceQuery(User user, int delta)
	{
		this.user = user;
		this.delta = delta;
	}

	@Override
	public void executeQuery() throws HeavenException, SQLException
	{
		if (delta == 0)
			return; // Nothing to do

		try (PreparedStatement ps = HeavenRP.getConnection().prepareStatement(QUERY))
		{
			ps.setInt(1, delta);
			ps.setInt(2, user.getId());
			ps.setInt(3, delta);

			System.out.println("Executing query " + ps);
			if (ps.executeUpdate() == 0)
				throw new HeavenException("Vous fouillez dans votre bourse... Vous n'avez pas assez.");

			UsersCache.invalidateCache(user);
		}
	}
}