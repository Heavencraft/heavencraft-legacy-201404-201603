package fr.heavencraft.Utils;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

import org.bukkit.Bukkit;
import org.bukkit.Location;

import fr.heavencraft.exceptions.HeavenException;
import fr.heavencraft.railways.HeavenRailway;

public class User
{
	private final int _id;
	private final String _uuid;
	private final String _name;
	private int _balance;

	User(ResultSet rs) throws SQLException
	{
		_id = rs.getInt("id");
		_uuid = rs.getString("uuid");
		_name = rs.getString("name");
		_balance = rs.getInt("balance");
	}

	public int getId()
	{
		return _id;
	}

	public String getUUID()
	{
		return _uuid;
	}

	public String getName()
	{
		return _name;
	}

	/*
	 * Pièces d'or
	 */

	public int getBalance()
	{
		return _balance;
	}

	public void updateBalance(int delta) throws HeavenException
	{
		if (_balance < 0)
			throw new HeavenException("Vous avez moins de 0 pièces d'or sur vous. O_o");

		if (_balance + delta < 0)
			throw new HeavenException("Vous fouillez dans votre bourse... Vous n'avez pas assez.");

		_balance += delta;
		update();
	}
	
	/*
	 * Mise à jour de la base de données
	 */

	private void update()
	{
		try (PreparedStatement ps = HeavenRailway.getConnection().prepareStatement(
				"UPDATE users SET balance = ? WHERE id = ?;"))
		{
			ps.setInt(1, _balance);
			ps.setInt(2, _id);

			ps.executeUpdate();
			ps.close();
		}
		catch (final SQLException ex)
		{
			ex.printStackTrace();
		}
	}

}