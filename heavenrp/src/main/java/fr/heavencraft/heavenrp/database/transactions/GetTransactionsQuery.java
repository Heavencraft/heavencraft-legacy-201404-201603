package fr.heavencraft.heavenrp.database.transactions;

import java.sql.SQLException;

import fr.heavencraft.async.queries.AbstractQuery;
import fr.heavencraft.exceptions.HeavenException;
import fr.heavencraft.heavenrp.database.bankaccounts.BankAccount;

public class GetTransactionsQuery extends AbstractQuery
{
	private static final String QUERY = "SELECT * FROM transaction_history WHERE account_id = ? AND ";

	private final BankAccount account;

	public GetTransactionsQuery(BankAccount account)
	{
		this.account = account;
	}

	@Override
	public void executeQuery() throws HeavenException, SQLException
	{
		// TODO Auto-generated method stub

	}

}
