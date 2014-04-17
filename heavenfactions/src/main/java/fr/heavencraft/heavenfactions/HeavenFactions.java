package fr.heavencraft.heavenfactions;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import fr.heavencraft.heavenfactions.commands.CommandsManager;
import fr.heavencraft.heavenfactions.listeners.ChatListener;

public class HeavenFactions extends JavaPlugin
{
	private static HeavenFactions _instance;
	private static Connection _mainConnection;
	
	@Override
	public void onEnable()
	{
		_instance = this;
		
		new CommandsManager();
		
		new ChatListener();
	}

	public static HeavenFactions getInstance()
	{
		return _instance;
	}
	
	public static Connection getMainConnection() {
		try {
			if ((_mainConnection == null) || (_mainConnection.isClosed())) {
				_mainConnection = DriverManager
						.getConnection("jdbc:mysql://localhost:3306/mc-db?user=mc-sql&password=MfGJQMBzmAS5xYhH&zeroDateTimeBehavior=convertToNull&?autoReconnect=true");
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
			Bukkit.shutdown();
		}
		return _mainConnection;
	}
}