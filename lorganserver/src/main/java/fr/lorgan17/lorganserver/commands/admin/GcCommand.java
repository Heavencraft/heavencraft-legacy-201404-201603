package fr.lorgan17.lorganserver.commands.admin;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.lorgan17.lorganserver.LorganServer;
import fr.lorgan17.lorganserver.commands.LorganCommand;
import fr.lorgan17.lorganserver.exceptions.LorganException;

public class GcCommand extends LorganCommand {

	public GcCommand()
	{
		super("gc");
	}

	@Override
	protected void onPlayerCommand(Player player, String[] args) throws LorganException
	{
		onConsoleCommand(player, args);
	}

	@Override
	protected void onConsoleCommand(CommandSender sender, String[] args) throws LorganException
	{
		System.gc();
		
		LorganServer.sendMessage(sender, "Le {Garbage Collector} a été appelé.");
	}

	@Override
	protected void sendUsage(CommandSender sender)
	{
	}
}
