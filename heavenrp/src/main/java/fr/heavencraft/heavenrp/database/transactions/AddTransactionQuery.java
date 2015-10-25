package fr.heavencraft.heavenrp.database.transactions;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import fr.heavencraft.async.queries.AbstractQuery;
import fr.heavencraft.exceptions.HeavenException;
import fr.heavencraft.heavenrp.HeavenRP;
import fr.heavencraft.heavenrp.database.bankaccounts.BankAccount;

public class AddTransactionQuery extends AbstractQuery
{
	private static final String QUERY = "INSERT INTO transaction_history (account_id, delta, text) VALUES (?, ?, ?);";

	private final BankAccount account;
	private final int delta;
	private final String text;

	public AddTransactionQuery(BankAccount account, int delta, String text)
	{
		this.account = account;
		this.delta = delta;
		this.text = text;
	}

	@Override
	public void executeQuery() throws HeavenException, SQLException
	{
		try (PreparedStatement ps = HeavenRP.getConnection().prepareStatement(QUERY))
		{
			ps.setInt(1, account.getId());
			ps.setInt(2, delta);
			ps.setString(3, text);

			System.out.println("Executing query " + ps);
			ps.executeUpdate();
		}
	}
}