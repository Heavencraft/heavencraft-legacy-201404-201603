package fr.lorgan17.lorganserver.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.lorgan17.lorganserver.LorganServer;
import fr.lorgan17.lorganserver.exceptions.LorganException;

public abstract class LorganCommand implements CommandExecutor {

	protected LorganServer _plugin;
	
	public LorganCommand(String name)
	{
		_plugin = LorganServer.getInstance();
		_plugin.getCommand(name).setExecutor(this);
		_plugin.getCommand(name).setPermissionMessage("");
	}
	
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
	{
		try
		{
			if (sender instanceof Player)
				onPlayerCommand((Player) sender, args);
			else
				onConsoleCommand(sender, args);
		}
		
		catch (LorganException ex)
		{
			LorganServer.sendMessage(sender, ex.getMessage());
		}
		
		return true;
	}
	
	protected abstract void onPlayerCommand(Player player, String[] args) throws LorganException;
	
	protected abstract void onConsoleCommand(CommandSender sender, String[] args) throws LorganException;
	
	protected abstract void sendUsage(CommandSender sender);
}
