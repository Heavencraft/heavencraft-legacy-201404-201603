package fr.heavencraft.HellCraft;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import fr.heavencraft.HellCraft.commands.CommandsManager;
import fr.heavencraft.HellCraft.listeners.ChatListener;
import fr.heavencraft.HellCraft.listeners.PlayerListener;

public class HellCraft extends JavaPlugin
{
	private static HellCraft _instance;
	private static Connection _mainConnection;

	public static Connection getMainConnection()
	{
		try
		{
			if ((_mainConnection == null) || (_mainConnection.isClosed()))
			{
				_mainConnection = DriverManager
						.getConnection("jdbc:mysql://localhost:3306/mc-db?user=mc-sql&password=9e781e41f865901850d5c3060063c8ca&zeroDateTimeBehavior=convertToNull&?autoReconnect=true");
			}
		}
		catch (final SQLException ex)
		{
			ex.printStackTrace();
			Bukkit.shutdown();
		}
		return _mainConnection;
	}

	@Override
	public void onEnable()
	{
		_instance = this;

		new CommandsManager();

		new ChatListener();
		new PlayerListener();
	}

	public static HellCraft getInstance()
	{
		return _instance;
	}
}