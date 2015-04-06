package fr.heavencraft.Utils;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import fr.heavencraft.exceptions.HeavenException;
import fr.heavencraft.exceptions.SQLErrorException;
import fr.heavencraft.railways.HeavenRailway;

public class BankAccountsManager
{
	public enum BankAccountType
	{
		USER("U"), TOWN("T"), ENTERPRISE("E");

		public static BankAccountType getByCode(String code) throws HeavenException
		{
			if (code.equals("U"))
				return BankAccountType.USER;
			else if (code.equals("T"))
				return BankAccountType.TOWN;
			else if (code.equals("E"))
				return BankAccountType.ENTERPRISE;
			else
				throw new HeavenException("Le type de livret {%1$s} est invalide.", code);
		}

		private final String _code;

		BankAccountType(String code)
		{
			_code = code;
		}

		public String getCode()
		{
			return _code;
		}
	}; // BankAccountType

	public static class BankAccount
	{
		private final int _id;
		private final String _name;
		private final BankAccountType _type;

		private BankAccount(ResultSet rs) throws HeavenException, SQLException
		{
			_id = rs.getInt("id");
			_name = rs.getString("owner"); // TODO : mettre name dans la base de
											// données
			_type = BankAccountType.getByCode(rs.getString("type"));
		}

		public int getId()
		{
			return _id;
		}

		public String getName()
		{
			return _name;
		}

		public int getBalance() throws HeavenException
		{
			try (PreparedStatement ps = HeavenRailway.getConnection().prepareStatement(
					"SELECT ba.balance FROM bank_account ba WHERE ba.id = ? LIMIT 1"))
			{
				ps.setInt(1, _id);

				final ResultSet rs = ps.executeQuery();

				if (!rs.next())
					throw new HeavenException("Le livret {%1$s} n'existe pas.", _id);

				return rs.getInt("balance");
			}
			catch (final SQLException ex)
			{
				ex.printStackTrace();
				throw new HeavenException("Le livret {%1$s} n'existe pas.", _id);
			}
		}

		public void updateBalance(int delta) throws HeavenException
		{
			try (PreparedStatement ps = HeavenRailway.getConnection().prepareStatement(
					"UPDATE bank_account SET balance = balance + ? WHERE type = ? AND owner = ? AND balance + ? >= 0"))
			{
				ps.setInt(1, delta);
				ps.setString(2, _type.getCode());
				ps.setString(3, _name);
				ps.setInt(4, delta);

				if (ps.executeUpdate() == 0)
					throw new HeavenException("Il n'y a pas assez d'argent sur le compte {%1$s}.", _name);

				ps.close();
			}
			catch (final SQLException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	} // BankAccount

	public static BankAccount getBankAccount(String name, BankAccountType type) throws HeavenException
	{
		try (PreparedStatement ps = HeavenRailway.getConnection().prepareStatement(
				"SELECT * FROM bank_account WHERE owner = ? AND type = ? LIMIT 1"))
		{
			ps.setString(1, name);
			ps.setString(2, type.getCode());

			final ResultSet rs = ps.executeQuery();

			if (!rs.next())
				throw new HeavenException("Le compte en banque {%1$s} n'existe pas.", name);

			return new BankAccount(rs);
		}
		catch (final SQLException ex)
		{
			ex.printStackTrace();
			throw new SQLErrorException();
		}
	}
}