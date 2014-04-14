package fr.heavencraft.NavalConflicts.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;

import fr.heavencraft.NavalConflicts.NavalConflicts;
import fr.heavencraft.NavalConflicts.Utils;
import fr.heavencraft.NavalConflicts.exceptions.NavalException;


public abstract class NavalCommand implements CommandExecutor {
	public NavalCommand(String name)
	{
		this(name, "");
	}
	public NavalCommand(String name, String permission)
	{
		try
		{
			PluginCommand command = NavalConflicts.getInstance().getCommand(name);				
			command.setExecutor(this);
			command.setPermissionMessage("");
			command.setPermission(permission);
		}
		catch (Exception ex) {}
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
		catch (NavalException ex)
		{
			Utils.sendMessage(sender, ex.getMessage());
		}
		
		return true;
	}
	
	protected abstract void onPlayerCommand(Player player, String[] args) throws NavalException;
	
	protected abstract void onConsoleCommand(CommandSender sender, String[] args) throws NavalException;
	
	protected abstract void sendUsage(CommandSender sender);
	
	
}
