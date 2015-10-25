package fr.heavencraft.heavenrp.database.bankaccounts;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import fr.heavencraft.async.queries.AbstractQuery;
import fr.heavencraft.async.queries.QueriesHandler;
import fr.heavencraft.exceptions.HeavenException;
import fr.heavencraft.heavenrp.HeavenRP;
import fr.heavencraft.heavenrp.database.transactions.AddTransactionQuery;

public class UpdateBankAccountBalanceQuery extends AbstractQuery
{
	private static final String QUERY = "UPDATE bank_account SET balance = balance + ? WHERE id = ? AND balance + ? >= 0 LIMIT 1;";

	private final BankAccount account;
	private final int delta;
	private final String text;

	public UpdateBankAccountBalanceQuery(BankAccount account, int delta, String text)
	{
		this.account = account;
		this.delta = delta;
		this.text = text;
	}

	@Override
	public void executeQuery() throws HeavenException, SQLException
	{
		if (delta == 0)
			return; // Nothing to do

		try (PreparedStatement ps = HeavenRP.getConnection().prepareStatement(QUERY))
		{
			ps.setInt(1, delta);
			ps.setInt(2, account.getId());
			ps.setInt(3, delta);

			System.out.println("Executing query " + ps);

			if (ps.executeUpdate() == 0)
				throw new HeavenException("Il n'y a pas assez d'argent sur le compte {%1$s}.", account.getName());

			// No issues : add transaction
			QueriesHandler.addQuery(new AddTransactionQuery(account, delta, text));
		}
	}
}