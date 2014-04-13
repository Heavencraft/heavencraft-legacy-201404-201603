package fr.lorgan17.lorganserver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import fr.lorgan17.lorganserver.commands.CommandsManager;
import fr.lorgan17.lorganserver.entities.Region;
import fr.lorgan17.lorganserver.exceptions.PlayerNotConnectedException;
import fr.lorgan17.lorganserver.listeners.ChatListener;
import fr.lorgan17.lorganserver.listeners.ServerListener;
import fr.lorgan17.lorganserver.listeners.protection.ProtectionBlockListener;
import fr.lorgan17.lorganserver.listeners.protection.ProtectionEntityListener;
import fr.lorgan17.lorganserver.listeners.protection.ProtectionHangingListener;
import fr.lorgan17.lorganserver.listeners.protection.ProtectionPlayerListener;
import fr.lorgan17.lorganserver.listeners.protection.ProtectionVehicleListener;
import fr.lorgan17.lorganserver.managers.MuteManager;
import fr.lorgan17.lorganserver.managers.SaveManager;
import fr.lorgan17.lorganserver.managers.SelectionManager;
import fr.lorgan17.lorganserver.managers.WorldsManager;

public class LorganServer extends JavaPlugin {

	private final static String BEGIN = "{";
	private final static String END = "}";
	private final static String ERROR_COLOR = ChatColor.RED.toString();
	private final static String NORMAL_COLOR = ChatColor.GOLD.toString();
	
	private final static String SURVIVAL_DB_URL = "jdbc:mysql://localhost:3306/minecraft-survie?user=mc-sql&password=MfGJQMBzmAS5xYhH";
	private final static String MAIN_DB_URL = "jdbc:mysql://localhost:3306/mc-db?user=mc-sql&password=MfGJQMBzmAS5xYhH";
	
	private static LorganServer _instance;

	// Base de données survie
	private static Connection _connection;
	
	// Base de données principale
	private static Connection _mainConnection;
	
	private SelectionManager _selectionManager;
	private MuteManager _muteManager;
	
	@Override
	public void onEnable()
	{
		super.onEnable();
		
		_instance = this;
		
		new CommandsManager();
		new SaveManager();
		
		_muteManager = new MuteManager(this);
		_selectionManager = new SelectionManager(this);
		
		
		new ServerListener(this);
		new ChatListener(this);
		new ProtectionPlayerListener(this);
		new ProtectionBlockListener(this);
		new ProtectionEntityListener(this);
		new ProtectionHangingListener(this);
		new ProtectionVehicleListener(this);
	}

	public SelectionManager getSelectionManager()
	{
		return _selectionManager;
	}
	
	public MuteManager getMuteManager()
	{
		return _muteManager;
	}

	public static LorganServer getInstance()
	{
		return _instance;
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
	
	public static Connection getMainConnection()
	{
		try
		{
			if (_mainConnection == null || _mainConnection.isClosed())
			{
				_mainConnection = DriverManager.getConnection(MAIN_DB_URL);
			}
		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
			Bukkit.shutdown();
		}
		
		return _mainConnection;
	}
	
	public static void sendMessage(CommandSender sender, String message)
	{
		if (sender != null)
			sender.sendMessage(NORMAL_COLOR + message.replace(BEGIN, ERROR_COLOR).replace(END, NORMAL_COLOR));
	}
	
	public static void broadcastMessage(String message)
	{
		Bukkit.broadcastMessage("§b[Heavencraft]§r " + message);
	}
	
	public static void sendConsoleMessage(String message)
	{
		Bukkit.getConsoleSender().sendMessage("§b[LorganServer]§r " + message);
	}
	
	public static Player getPlayer(String name) throws PlayerNotConnectedException
	{
		Player player = Bukkit.getPlayer(name);
		
		if (player == null)
			throw new PlayerNotConnectedException(name);
		
		return player;
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
		else
			return region.isMember(player.getName(), false);
	}
}
