package fr.heavencraft.HellCraft.HPS;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import fr.heavencraft.HellCraft.HellCraft;
import fr.heavencraft.HellCraft.exceptions.HellException;

public class HpsManager
{
	public static void removeBalance(String name, int hps) throws HellException
	{
		if (getBalance(name) < hps)
		{
			throw new HellException("Vous n'avez pas assez d'argent sur votre compte.");
		}
		try
		{
			final PreparedStatement ps = HellCraft.getMainConnection().prepareStatement(
					"UPDATE heavencraft_users SET balance = balance - ? WHERE username = ?");
			ps.setInt(1, hps);
			ps.setString(2, name);

			ps.executeUpdate();
		}
		catch (final SQLException ex)
		{
			ex.printStackTrace();
			throw new UnknownError();
		}
	}

	public static int getBalance(String name) throws HellException
	{
		try
		{
			final PreparedStatement ps = HellCraft.getMainConnection().prepareStatement(
					"SELECT balance FROM heavencraft_users WHERE username = ?");
			ps.setString(1, name);

			final ResultSet rs = ps.executeQuery();

			if (!rs.next())
			{
				throw new HellException("Vous n'avez pas de compte sur le site.");
			}
			return rs.getInt("balance");
		}
		catch (final SQLException ex)
		{
			ex.printStackTrace();
		}
		throw new UnknownError();
	}
}