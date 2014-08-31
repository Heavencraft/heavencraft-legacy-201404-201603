package fr.lorgan17.lorganserver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import fr.heavencraft.HeavenPlugin;
import fr.heavencraft.listeners.AntiLagListener;
import fr.heavencraft.listeners.NoChatListener;
import fr.heavencraft.tasks.SaveTaskNoWorldGuard;
import fr.lorgan17.lorganserver.commands.CommandsManager;
import fr.lorgan17.lorganserver.entities.Region;
import fr.lorgan17.lorganserver.listeners.ServerListener;
import fr.lorgan17.lorganserver.listeners.protection.ProtectionBlockListener;
import fr.lorgan17.lorganserver.listeners.protection.ProtectionEntityListener;
import fr.lorgan17.lorganserver.listeners.protection.ProtectionHangingListener;
import fr.lorgan17.lorganserver.listeners.protection.ProtectionPlayerListener;
import fr.lorgan17.lorganserver.listeners.protection.ProtectionVehicleListener;
import fr.lorgan17.lorganserver.managers.SelectionManager;
import fr.lorgan17.lorganserver.managers.WorldsManager;

public class LorganServer extends HeavenPlugin
{
	private final static String SURVIVAL_DB_URL = "jdbc:mysql://localhost:3306/minecraft-survie?user=mc-sql&password=9e781e41f865901850d5c3060063c8ca";
	private static Connection _connection;

	@Override
	public void onEnable()
	{
		super.onEnable();

		new CommandsManager();

		new SelectionManager();

		// From Heavencore
		new AntiLagListener();
		new NoChatListener();
		new SaveTaskNoWorldGuard();

		new ServerListener();
		new ProtectionPlayerListener();
		new ProtectionBlockListener();
		new ProtectionEntityListener();
		new ProtectionHangingListener();
		new ProtectionVehicleListener();
	}

	public static Connection getConnection()
	{
		try
		{
			if (_connection == null || _connection.isClosed())
			{
				_connection = DriverManager.getConnection(SURVIVAL_DB_URL);
			}
		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
			Bukkit.shutdown();
		}

		return _connection;
	}

	public static boolean canBeDestroyed(Player player, Block block)
	{
		if (!block.getWorld().equals(WorldsManager.getWorld()))
			return true;

		Region region = Region.getRegionByLocation(block.getX(), block.getZ());

		if (region == null)
			return true;
		else if (player == null)
			return false;
		else if (player.hasPermission(OriginesPermissions.PROTECTION_BYPASS))
			return true;
		else
			return region.isMember(player.getName(), false);
	}
}
