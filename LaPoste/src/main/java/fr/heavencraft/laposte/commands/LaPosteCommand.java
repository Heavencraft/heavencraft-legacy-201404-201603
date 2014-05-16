package fr.heavencraft.laposte.commands;

import java.util.logging.Level;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;

import fr.heavencraft.laposte.LaPoste;
import fr.heavencraft.laposte.Utils;

public abstract class LaPosteCommand implements CommandExecutor {
	public LaPosteCommand(String name)
	{
		this(name, "");
	}
	public LaPosteCommand(String name, String permission)
	{
		try
		{
			PluginCommand command = LaPoste.getInstance().getCommand(name);				
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
			ex.printStackTrace();
		}
		
		return true;
	}
	
	protected abstract void onPlayerCommand(Player player, String[] args) throws Exception;
	
	protected abstract void onConsoleCommand(CommandSender sender, String[] args) throws Exception;
	
	protected abstract void sendUsage(CommandSender sender);
}
