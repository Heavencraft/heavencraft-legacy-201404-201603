package fr.heavencraft.heavenrp.hps;

import fr.heavencraft.exceptions.HeavenException;
import fr.heavencraft.exceptions.UnknownErrorException;
import fr.heavencraft.heavenrp.HeavenRP;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class HpsManager
{
	public static void removeBalance(String name, int hps) throws HeavenException
	{
		if (getBalance(name) < hps)
		{
			throw new HeavenException("Vous n'avez pas assez d'argent sur mon compte.");
		}
		try
		{
			PreparedStatement ps = HeavenRP.getMainConnection().prepareStatement(
					"UPDATE heavencraft_users SET balance = balance - ? WHERE username = ?");
			ps.setInt(1, hps);
			ps.setString(2, name);

			ps.executeUpdate();
		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
			throw new UnknownErrorException();
		}
	}

	public static int getBalance(String name) throws HeavenException
	{
		try
		{
			PreparedStatement ps = HeavenRP.getMainConnection().prepareStatement(
					"SELECT balance FROM heavencraft_users WHERE username = ?");
			ps.setString(1, name);

			ResultSet rs = ps.executeQuery();

			if (!rs.next())
			{
				throw new HeavenException("Vous n'avez pas de compte sur le site.");
			}
			return rs.getInt("balance");
		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
		}
		throw new UnknownErrorException();
	}
}