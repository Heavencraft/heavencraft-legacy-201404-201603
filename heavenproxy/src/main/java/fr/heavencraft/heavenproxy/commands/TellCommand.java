package fr.heavencraft.heavenproxy.commands;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import fr.heavencraft.heavenproxy.Utils;
import fr.heavencraft.heavenproxy.exceptions.HeavenException;
import fr.heavencraft.heavenproxy.mute.MuteManager;

public class TellCommand extends HeavenCommand {
	private final static String FROM	= "§d[de %1$s]§r %2$s";
	private final static String TO		= "§d[à %1$s]§r %2$s";
	
	public TellCommand()
	{
		super("tell", null, new String[] {"m", "msg", "t", "w"});
	}
	
	@Override
	protected void onCommand(CommandSender sender, String[] args) throws HeavenException
	{
		if (args.length < 2)
		{
			sendUsage(sender);
			return;
		}

		// Si le joueur est mute
		if (MuteManager.isMuted(sender.getName()))
			return;
		
		ProxiedPlayer player = Utils.getPlayer(args[0]);
		String message = Utils.ArrayToString(args, 1, " ");

		sender.sendMessage(String.format(TO, player.getName(), message));
		player.sendMessage(String.format(FROM, sender.getName(), message));
	}
	
	private static void sendUsage(CommandSender sender)
	{
		Utils.sendMessage(sender, "/{m} <joueur> <message> : envoie un message privé à un joueur.");
	}
}