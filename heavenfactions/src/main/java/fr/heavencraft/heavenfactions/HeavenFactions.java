package fr.heavencraft.heavenfactions;

import org.bukkit.plugin.java.JavaPlugin;

import fr.heavencraft.heavenfactions.commands.CommandsManager;
import fr.heavencraft.heavenfactions.listeners.ChatListener;

public class HeavenFactions extends JavaPlugin
{
	private static HeavenFactions _instance;
	
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
}