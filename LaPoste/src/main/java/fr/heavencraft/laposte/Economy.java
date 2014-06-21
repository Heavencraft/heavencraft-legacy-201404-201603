package fr.heavencraft.laposte;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.bukkit.entity.Player;


public class Economy {

	public int getBalance(Player p)
	{
		try
		{
			PreparedStatement ps = LaPoste.getMainConnection().prepareStatement(
					"SELECT * FROM users WHERE name = ? LIMIT 1;");
			ps.setString(1, p.getName());
			ResultSet rs = ps.executeQuery();
			if (!rs.next())
				throw new Exception(p.getName());

			return rs.getInt("balance");
		}

		catch (SQLException ex)
		{
			ex.printStackTrace();
			throw new Exception ext(ex.toString());
		}
	}
}
