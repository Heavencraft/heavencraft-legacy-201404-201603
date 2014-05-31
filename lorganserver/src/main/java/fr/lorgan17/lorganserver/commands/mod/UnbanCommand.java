package fr.lorgan17.lorganserver.commands.mod;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.lorgan17.lorganserver.LorganServer;
import fr.lorgan17.lorganserver.commands.LorganCommand;
import fr.lorgan17.lorganserver.entities.User;
import fr.lorgan17.lorganserver.exceptions.LorganException;

public class UnbanCommand extends LorganCommand {

	public UnbanCommand()
	{
		super("unban");
	}

	@Override
	protected void onPlayerCommand(Player player, String[] args) throws LorganException
	{
		onConsoleCommand(player, args);
	}

	@Override
	protected void onConsoleCommand(CommandSender sender, String[] args) throws LorganException
	{
		if (args.length != 1)
		{
			sendUsage(sender);
			return;
		}
		
		User.getUserByName(args[0]).unban();
		
		LorganServer.sendMessage(sender, "Le joueur {" + args[0] + "} a été débanni.");
	}

	@Override
	protected void sendUsage(CommandSender sender)
	{
		LorganServer.sendMessage(sender, "/{unban} <joueur>");
	}
}
