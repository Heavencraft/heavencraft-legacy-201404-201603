package fr.heavencraft.heavenrp.database;

import java.util.ArrayList;
import java.util.List;

import fr.heavencraft.async.queries.BatchQuery;
import fr.heavencraft.async.queries.Query;
import fr.heavencraft.heavenrp.database.bankaccounts.BankAccount;
import fr.heavencraft.heavenrp.database.bankaccounts.UpdateBankAccountBalanceQuery;
import fr.heavencraft.heavenrp.database.users.UpdateUserBalanceQuery;
import fr.heavencraft.heavenrp.database.users.User;

public class MoneyTransfertQuery extends BatchQuery
{
	public MoneyTransfertQuery(User from, BankAccount to, int delta, String text)
	{
		super(createQueries(from, to, delta, text));
	}

	private static List<Query> createQueries(User from, BankAccount to, int delta, String text)
	{
		List<Query> queries = new ArrayList<Query>();
		queries.add(new UpdateUserBalanceQuery(from, -delta));
		queries.add(new UpdateBankAccountBalanceQuery(to, delta, text));
		return queries;
	}

	public MoneyTransfertQuery(BankAccount from, User to, int delta, String text)
	{
		super(createQueries(from, to, delta, text));
	}

	private static List<Query> createQueries(BankAccount from, User to, int delta, String text)
	{
		List<Query> queries = new ArrayList<Query>();
		queries.add(new UpdateBankAccountBalanceQuery(from, -delta, text));
		queries.add(new UpdateUserBalanceQuery(to, delta));
		return queries;
	}

	public MoneyTransfertQuery(Object from, Object to, int delta, String text)
	{
		super(createQueries(from, to, delta, text));
	}

	private static List<Query> createQueries(Object from, Object to, int delta, String text)
	{
		if (from instanceof User && to instanceof BankAccount)
			return createQueries((User) from, (BankAccount) to, delta, text);

		if (from instanceof BankAccount && to instanceof User)
			return createQueries((BankAccount) from, (User) to, delta, text);

		return null;
	}
}