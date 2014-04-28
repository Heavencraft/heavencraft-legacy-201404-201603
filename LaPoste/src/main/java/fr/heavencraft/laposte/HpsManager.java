package fr.heavencraft.laposte;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class HpsManager {
	public static void removeBalance(String name, int hps) throws Exception
	{
		if (getBalance(name) < hps)
		{
			throw new Exception("Vous n'avez pas assez d'argent sur votre compte.");
		}
		try
		{
			PreparedStatement ps = LaPoste.getMainConnection().prepareStatement(
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

	public static int getBalance(String name) throws Exception
	{
		try
		{
			PreparedStatement ps = LaPoste.getMainConnection().prepareStatement(
					"SELECT balance FROM heavencraft_users WHERE username = ?");
			ps.setString(1, name);

			ResultSet rs = ps.executeQuery();

			if (!rs.next())
			{
				throw new Exception("Vous n'avez pas de compte sur le site.");
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
