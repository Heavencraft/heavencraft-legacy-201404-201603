package fr.tenkei.creaplugin.managers;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import fr.tenkei.creaplugin.exceptions.MyException;
import fr.tenkei.creaplugin.utils.ConnectionManager;

public class HpsManager {

	public static final int TAUX_JETON = 80; 
	// 1 Hps <-> 750 Jeton
	public static void removeBalance(String name, int hps) throws MyException
	{
		if (getBalance(name) < hps)
			throw new MyException("Vous n'avez pas assez d'argent sur votre compte.");

		try
		{
			PreparedStatement ps = ConnectionManager.getMainConnection().prepareStatement("UPDATE heavencraft_users SET balance = balance - ? WHERE username = ?");
			ps.setInt(1, hps);
			ps.setString(2, name);

			ps.executeUpdate();
		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
			throw new MyException("Erreur SQL. Nous sommes désolé.");
		}
	}

	public static int getBalance(String name) throws MyException
	{
		try
		{
			PreparedStatement ps = ConnectionManager.getMainConnection().prepareStatement("SELECT balance FROM heavencraft_users WHERE username = ?");
			ps.setString(1, name);
			
			ResultSet rs = ps.executeQuery();
			
			if (!rs.next())
				throw new MyException("Vous n'avez pas de compte sur le site.");
			
			return rs.getInt("balance");
		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
			throw new MyException("Vous n'avez pas assez d'argent sur mon compte.");
		}
	}
}