package fr.heavencraft.heavenrp.shops;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.bukkit.Location;

import fr.heavencraft.exceptions.HeavenException;
import fr.heavencraft.exceptions.SQLErrorException;
import fr.heavencraft.heavenrp.HeavenRP;
import fr.heavencraft.heavenrp.economy.bankaccount.BankAccountsManager.BankAccount;

public class Stock2Provider
{
	public static void createChest(String name, BankAccount account, Location chestLocation, Location signLocation)
			throws HeavenException
	{
		try (PreparedStatement ps = HeavenRP.getConnection().prepareStatement(
				"INSERT INTO shop2_chest VALUES (?, ?, ?, ?, ?"))
		{
			ps.setInt(1, chestLocation.getBlockX());
			ps.setInt(2, chestLocation.getBlockY());
			ps.setInt(3, chestLocation.getBlockZ());
			ps.setString(4, name);
			ps.setInt(5, account.getId());

			if (ps.executeUpdate() != 1)
				throw new HeavenException("Le coffre n'a pas pu être créé");
		}
		catch (final SQLException ex)
		{
			ex.printStackTrace();
			throw new SQLErrorException();
		}
	}

	public static void getChestBySignLocation()
	{

	}

	public static Stock2 getStockBySignLocation(Location location) throws HeavenException
	{
		try (PreparedStatement ps = HeavenRP.getConnection().prepareStatement(
				"SELECT * FROM stocks WHERE sign_w = ?, sign_x = ?, sign_y = ?, sign_z = ? LIMIT 1"))
		{
			ps.setString(1, location.getWorld().getName());
			ps.setInt(2, location.getBlockX());
			ps.setInt(3, location.getBlockY());
			ps.setInt(4, location.getBlockZ());

			final ResultSet rs = ps.executeQuery();

			if (!rs.next())
				throw new HeavenException("Aucun coffre trouvé.");

			return new Stock2(rs);
		}
		catch (final SQLException ex)
		{
			ex.printStackTrace();
			throw new SQLErrorException();
		}
	}

	public static Stock2 getStockByChestLocation(Location location) throws HeavenException
	{
		try (PreparedStatement ps = HeavenRP.getConnection().prepareStatement(
				"SELECT * FROM stocks WHERE chest_w = ?, chest_x = ?, chest_y = ?, chest_z = ? LIMIT 1"))
		{
			ps.setString(1, location.getWorld().getName());
			ps.setInt(2, location.getBlockX());
			ps.setInt(3, location.getBlockY());
			ps.setInt(4, location.getBlockZ());

			final ResultSet rs = ps.executeQuery();

			if (!rs.next())
				throw new HeavenException("Aucun coffre trouvé.");

			return new Stock2(rs);
		}
		catch (final SQLException ex)
		{
			ex.printStackTrace();
			throw new SQLErrorException();
		}
	}
}