package fr.heavencraft.heavenrp.economy.bankaccount;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException;

import fr.heavencraft.exceptions.HeavenException;
import fr.heavencraft.exceptions.SQLErrorException;
import fr.heavencraft.heavenrp.HeavenRP;
import fr.heavencraft.heavenrp.economy.enterprise.EnterprisesManager;

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

		public List<String> getOwnersNames() throws HeavenException
		{
			final List<String> owners = new ArrayList<String>();

			switch (_type)
			{
				case USER:
					owners.add(_name);
					break;
				case TOWN:
					try (PreparedStatement ps = HeavenRP.getConnection().prepareStatement(
							"SELECT u.name FROM users u, mayors m WHERE u.id = m.user_id AND region_name = ?"))
					{
						ps.setString(1, _name);
						final ResultSet rs = ps.executeQuery();

						while (rs.next())
							owners.add(rs.getString("name"));
					}
					catch (final SQLException ex)
					{
						ex.printStackTrace();
					}

					break;

				case ENTERPRISE:
					owners.addAll(EnterprisesManager.getEnterpriseByName(_name).getMembers(false));
					break;
			}

			return owners;
		}

		public List<CommandSender> getOwners() throws HeavenException
		{
			final List<CommandSender> owners = new ArrayList<CommandSender>();

			for (final String name : getOwnersNames())
			{
				final Player player = Bukkit.getPlayer(name);

				if (player != null)
					owners.add(player);
			}

			return owners;
		}

		public int getBalance() throws HeavenException
		{
			try (PreparedStatement ps = HeavenRP.getConnection().prepareStatement(
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
			try (PreparedStatement ps = HeavenRP.getConnection().prepareStatement(
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

	public static void createBankAccount(String owner, BankAccountType type) throws HeavenException
	{
		try (PreparedStatement ps = HeavenRP.getConnection().prepareStatement(
				"INSERT INTO bank_account (owner, type) VALUE (?, ?);"))
		{
			ps.setString(1, owner);
			ps.setString(2, type.getCode());

			ps.executeUpdate();
		}
		catch (final MySQLIntegrityConstraintViolationException ex)
		{
			ex.printStackTrace();
			throw new HeavenException("Le compte en banque {%1$s} existe déjà.", owner);
		}
		catch (final SQLException ex)
		{
			ex.printStackTrace();
			throw new SQLErrorException();
		}
	}

	public static void deleteBankAccount(String name, BankAccountType type) throws HeavenException
	{
		try (PreparedStatement ps = HeavenRP.getConnection().prepareStatement(
				"DELETE FROM bank_account WHERE owner = ? AND type = ? LIMIT 1;"))
		{
			ps.setString(1, name);
			ps.setString(2, type.getCode());

			ps.executeUpdate();
		}
		catch (final SQLException ex)
		{
			ex.printStackTrace();
			throw new SQLErrorException();
		}
	}

	public static BankAccount getBankAccount(String name, BankAccountType type) throws HeavenException
	{
		try (PreparedStatement ps = HeavenRP.getConnection().prepareStatement(
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

	public static BankAccount getBankAccountById(int id) throws HeavenException
	{
		try (PreparedStatement ps = HeavenRP.getConnection().prepareStatement(
				"SELECT ba.id, ba.owner, ba.type FROM bank_account ba WHERE ba.id = ? LIMIT 1"))
		{
			ps.setInt(1, id);
			final ResultSet rs = ps.executeQuery();

			if (!rs.next())
				throw new HeavenException("Le compte en banque {%1$s} n'existe pas.", id);

			return new BankAccount(rs);
		}
		catch (final SQLException ex)
		{
			ex.printStackTrace();
			throw new HeavenException("Le compte en banque {%1$s} n'existe pas.", id);
		}
	}

	public static List<BankAccount> getAccountByOwner(String owner) throws HeavenException
	{
		final List<BankAccount> result = new ArrayList<BankAccount>();

		try (PreparedStatement ps = HeavenRP.getConnection().prepareStatement(
				"(SELECT ba.id, ba.owner, ba.type "
						+ // Sélection des comptes de villes
						"FROM bank_account ba, mayors m, users u " + "WHERE ba.type = 'T' " + "AND ba.owner = m.region_name "
						+ "AND m.user_id = u.id " + "AND u.name = ?) " + "UNION "
						+ "(SELECT ba.id, ba.owner, ba.type "
						+ // Sélection des comptes d'entreprises
						"FROM bank_account ba, enterprises e, enterprises_members em, users u " + "WHERE ba.type ='E' "
						+ "AND ba.owner = e.name " + "AND e.id = em.enterprise_id " + "AND em.user_id = u.id "
						+ "AND u.name = ?);"))
		{
			ps.setString(1, owner);
			ps.setString(2, owner);
			final ResultSet rs = ps.executeQuery();

			while (rs.next())
				result.add(new BankAccount(rs));
		}
		catch (final SQLException ex)
		{
			ex.printStackTrace();
			new SQLErrorException();
		}

		return result;
	}
}