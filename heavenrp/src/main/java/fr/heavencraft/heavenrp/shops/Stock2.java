package fr.heavencraft.heavenrp.shops;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import fr.heavencraft.exceptions.HeavenException;
import fr.heavencraft.exceptions.SQLErrorException;
import fr.heavencraft.heavenrp.HeavenRP;
import fr.heavencraft.heavenrp.economy.bankaccount.BankAccountsManager;
import fr.heavencraft.heavenrp.economy.bankaccount.BankAccountsManager.BankAccount;

public class Stock2
{
	private final int id;
	private final String name;

	private final int account;

	Stock2(ResultSet rs) throws SQLException
	{
		id = rs.getInt("id");
		name = rs.getString("name");
		account = rs.getInt("account");
	}

	public BankAccount getBankAccount() throws HeavenException
	{
		return BankAccountsManager.getBankAccountById(account);
	}

	public void remove() throws SQLErrorException
	{
		try (PreparedStatement ps = HeavenRP.getConnection().prepareStatement(
				"DELETE FROM stocks WHERE id = ? LIMIT 1"))
		{
			ps.setInt(1, id);
			ps.executeUpdate();
		}
		catch (final SQLException ex)
		{
			ex.printStackTrace();
			throw new SQLErrorException();
		}
	}

	public String getName()
	{
		return name;
	}
}