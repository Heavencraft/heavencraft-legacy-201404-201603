package fr.heavencraft.heavenproxy.commands;

import fr.heavencraft.heavenproxy.Utils;
import fr.heavencraft.heavenproxy.exceptions.HeavenException;
import fr.heavencraft.heavenproxy.managers.KickManager;
import fr.heavencraft.heavenproxy.managers.LogsManager;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class KickCommand extends HeavenCommand {
	
	private final static String KICK_MESSAGE = "Vous avez été exclu du serveur par %1$s :\n\n%2$s";
	private final static String KICK = "K|%1$s|%2$s";
	
	public KickCommand()
	{
		super("kick", "heavencraft.commands.kick", new String[] {"gkick"});
	}

	@Override
	protected void onCommand(CommandSender sender, String[] args) throws HeavenException
	{
		ProxiedPlayer player;
		String reason;
		
		switch (args.length)
		{
		case 0:
			sendUsage(sender);
			return;
		case 1:
			player = Utils.getPlayer(args[0]);
			reason = "";
			break;
		default:
			player = Utils.getPlayer(args[0]);
			reason = Utils.ArrayToString(args, 1, " ");
			break;
		}
		
		kickPlayer(player, sender.getName(), reason);
	}
	
	public static void kickPlayer(ProxiedPlayer player, String kickedBy, String reason)
	{
		KickManager.addReason(player.getName(), String.format(KICK, kickedBy, reason));
		LogsManager.addKick(kickedBy, player.getName(), reason);
		
		player.disconnect(String.format(KICK_MESSAGE, kickedBy, reason));
	}

	private static void sendUsage(CommandSender sender)
	{
		Utils.sendMessage(sender, "/{kick} <joueur>");
		Utils.sendMessage(sender, "/{kick} <joueur> <raison>");
	}
}