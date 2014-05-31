package fr.lorgan17.lorganserver.commands.user;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.lorgan17.lorganserver.LorganServer;
import fr.lorgan17.lorganserver.commands.LorganCommand;
import fr.lorgan17.lorganserver.exceptions.LorganException;

public class TellCommand extends LorganCommand {

	private final static String FROM = "§d[de %1$s]§r %2$s";
	private final static String TO = "§d[à %1$s]§r %2$s";
	
	public TellCommand()
	{
		super("tell");
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
		
		LorganServer.sendMessage(sender, String.format(TO, player.getName(), message));
		LorganServer.sendMessage(player, String.format(FROM, sender.getName(), message));
	}

	@Override
	protected void sendUsage(CommandSender sender)
	{
		LorganServer.sendMessage(sender, "/{tell} <joueur> <message>");
	}
}
