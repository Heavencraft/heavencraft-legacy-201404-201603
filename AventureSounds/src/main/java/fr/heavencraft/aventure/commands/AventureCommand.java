package fr.heavencraft.aventure.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;

import fr.heavencraft.aventure.HeavenAventure;
import fr.heavencraft.aventure.Utils;

public abstract class AventureCommand implements CommandExecutor {
	public AventureCommand(String name)
	{
		this(name, "");
	}
	public AventureCommand(String name, String permission)
	{
		try
		{
			PluginCommand command = HeavenAventure.getInstance().getCommand(name);				
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
		catch (Exception ex)
		{
			Utils.sendMessage(sender, ex.getMessage());
		}
		
		return true;
	}
	
	protected abstract void onPlayerCommand(Player player, String[] args) throws Exception;
	
	protected abstract void onConsoleCommand(CommandSender sender, String[] args) throws Exception;
	
	protected abstract void sendUsage(CommandSender sender);
}
