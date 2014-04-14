package fr.heavencraft.NavalConflicts.HPS;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import fr.heavencraft.NavalConflicts.NavalConflicts;
import fr.heavencraft.NavalConflicts.exceptions.NavalException;

public class HpsManager {
	public static void removeBalance(String name, int hps) throws NavalException
	{
		if (getBalance(name) < hps)
		{
			throw new NavalException("Vous n'avez pas assez d'argent sur votre compte.");
		}
		try
		{
			PreparedStatement ps = NavalConflicts.getMainConnection().prepareStatement(
					"UPDATE heavencraft_users SET balance = balance - ? WHERE username = ?");
			ps.setInt(1, hps);
			ps.setString(2, name);

			ps.executeUpdate();
		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
			throw new UnknownError();
		}
	}

	public static int getBalance(String name) throws NavalException
	{
		try
		{
			PreparedStatement ps = NavalConflicts.getMainConnection().prepareStatement(
					"SELECT balance FROM heavencraft_users WHERE username = ?");
			ps.setString(1, name);

			ResultSet rs = ps.executeQuery();

			if (!rs.next())
			{
				throw new NavalException("Vous n'avez pas de compte sur le site.");
			}
			return rs.getInt("balance");
		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
		}
		throw new UnknownError();
	}
}
