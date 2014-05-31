package fr.lorgan17.lorganserver.commands.mod;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.lorgan17.lorganserver.LorganServer;
import fr.lorgan17.lorganserver.commands.LorganCommand;
import fr.lorgan17.lorganserver.exceptions.LorganException;

public class KickCommand extends LorganCommand {

	public KickCommand()
	{
		super("kick");
	}

	@Override
	protected void onPlayerCommand(Player player, String[] args) throws LorganException
	{
		onConsoleCommand(player, args);
	}

	@Override
	protected void onConsoleCommand(CommandSender sender, String[] args) throws LorganException
	{
		if (args.length < 2)
		{
			sendUsage(sender);
			return;
		}
		
		Player player = LorganServer.getPlayer(args[0]);
		String message = "";
		
		for (int i = 1; i != args.length; i++)
			message += args[i] + " ";
		
		player.kickPlayer(message);
	}

	@Override
	protected void sendUsage(CommandSender sender)
	{
		LorganServer.sendMessage(sender, "/{kick} <joueur> <raison>");
	}
}
