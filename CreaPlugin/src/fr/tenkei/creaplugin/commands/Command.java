package fr.tenkei.creaplugin.commands;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.tenkei.creaplugin.MyPlugin;
import fr.tenkei.creaplugin.exceptions.MyException;
import fr.tenkei.creaplugin.exceptions.PlayerNotConnectedException;
import fr.tenkei.creaplugin.managers.entities.User;
import fr.tenkei.creaplugin.utils.Message;
import fr.tenkei.creaplugin.utils.Stuff;


public abstract class Command implements CommandExecutor{

	protected MyPlugin _plugin;
	
	private CommandSender _sender;

	public Command(String name, MyPlugin plugin)
	{
		_plugin = plugin;
		plugin.getCommand(name).setExecutor(this);
		plugin.getCommand(name).setPermissionMessage("Vous n'avez pas la permissions...");
	}

	public boolean onCommand(CommandSender sender, org.bukkit.command.Command command, String label, String[] args)
	{
		_sender = sender;
		try
		{
			if (sender instanceof Player){
				onPlayerCommand((Player) sender, args);
			}else
				onConsoleCommand(sender, args);
		}
		catch (MyException ex)
		{
			Message.sendMessage(sender, ex.getMessage());
		}

		return true;
	}

	protected abstract void onPlayerCommand(Player player, String[] args) throws MyException;

	protected abstract void onConsoleCommand(CommandSender sender, String[] args) throws MyException;

	protected abstract void sendUsage(CommandSender sender);
	
	protected Player getPlayer(String name) throws PlayerNotConnectedException
	{
		return Stuff.getPlayer(name);
	}

	protected User getUser() throws MyException
	{
		return getUser(_sender.getName());
	}

	protected User getUser(String name) throws MyException
	{
		return _plugin.getManagers().getUserManager().getUser(name);
	}
}