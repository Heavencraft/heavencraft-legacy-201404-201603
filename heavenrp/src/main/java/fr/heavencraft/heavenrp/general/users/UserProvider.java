package fr.heavencraft.heavenrp.general.users;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import fr.heavencraft.exceptions.HeavenException;
import fr.heavencraft.exceptions.SQLErrorException;
import fr.heavencraft.exceptions.UserNotFoundException;
import fr.heavencraft.heavenrp.HeavenRP;
import fr.heavencraft.heavenrp.economy.bankaccount.BankAccountsManager;
import fr.heavencraft.heavenrp.economy.bankaccount.BankAccountsManager.BankAccountType;

public class UserProvider
{
	private static Map<String, User> usersByName = new HashMap<String, User>();

	public static void createUser(String uuid, String name)
	{
		try (PreparedStatement ps = HeavenRP.getConnection().prepareStatement(
				"INSERT INTO users (uuid, name) VALUES (?, ?);"))
		{
			ps.setString(1, uuid);
			ps.setString(2, name);

			ps.executeUpdate();
			ps.close();

			BankAccountsManager.createBankAccount(name, BankAccountType.USER);
		}

		catch (final SQLException ex)
		{
			ex.printStackTrace();
		}
		catch (final HeavenException ex)
		{
		}
	}

	public static void removeFromCache(String name)
	{
		usersByName.remove(name);
	}

	public static User getUserByName(String name) throws HeavenException
	{
		User user = usersByName.get(name);

		if (user != null)
			return user;

		try (PreparedStatement ps = HeavenRP.getConnection().prepareStatement(
				"SELECT * FROM users WHERE name = ? LIMIT 1;"))
		{
			ps.setString(1, name);

			final ResultSet rs = ps.executeQuery();

			if (!rs.next())
				throw new UserNotFoundException(name);

			user = new User(rs);

			usersByName.put(name, user);
			return user;
		}

		catch (final SQLException ex)
		{
			ex.printStackTrace();
			throw new SQLErrorException();
		}
	}

	/**
	 * Returns an User using his uuid. Remark: This function does not add the
	 * user to the cache.
	 * 
	 * @param uuid
	 * @return
	 * @throws HeavenException
	 */
	public static User getUserByUUID(String uuid) throws HeavenException
	{
		User user = null;
		try (PreparedStatement ps = HeavenRP.getConnection().prepareStatement(
				"SELECT * FROM users WHERE uuid = ? LIMIT 1;"))
		{
			ps.setString(1, uuid);

			final ResultSet rs = ps.executeQuery();

			if (!rs.next())
				throw new UserNotFoundException(uuid);

			user = new User(rs);
			return user;
		}

		catch (final SQLException ex)
		{
			ex.printStackTrace();
			throw new SQLErrorException();
		}
	}

	private static final String UPDATE_NAME_BANK = "UPDATE bank_account b, users u " //
			+ "SET b.owner = ? " //
			+ "WHERE b.owner = u.name " //
			+ "AND u.uuid = ? " //
			+ "AND b.type = 'U'";
	private static final String UPDATE_NAME_USERS = "UPDATE users SET name = ? WHERE uuid = ? LIMIT 1";

	public static void updateName(String uuid, String name) throws HeavenException
	{
		try (PreparedStatement ps = HeavenRP.getConnection().prepareStatement(UPDATE_NAME_BANK))
		{
			ps.setString(1, name);
			ps.setString(2, uuid);

			if (ps.executeUpdate() != 1)
				throw new UserNotFoundException(name);

			try (PreparedStatement ps2 = HeavenRP.getConnection().prepareStatement(UPDATE_NAME_USERS))
			{
				ps2.setString(1, name);
				ps2.setString(2, uuid);

				if (ps2.executeUpdate() != 1)
					throw new UserNotFoundException(name);
			}
		}
		catch (final SQLException ex)
		{
			ex.printStackTrace();
			throw new SQLErrorException();
		}
	}
}