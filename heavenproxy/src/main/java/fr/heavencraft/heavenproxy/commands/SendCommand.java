package fr.heavencraft.heavenproxy.commands;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import fr.heavencraft.heavenproxy.Utils;
import fr.heavencraft.heavenproxy.exceptions.HeavenException;
import fr.heavencraft.heavenproxy.mute.MuteManager;

public class SendCommand extends HeavenCommand {
	
	private final static String FROM = "§6Vous avez reçu §c%1$s§6 de la part de §c%2$s§6.";
	private final static String TO = "§6Vous avez envoyé §c%1$s§6 à §c%2$s§6.";
	
	public SendCommand()
	{
		super("send", null, new String[]{});
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
		String toSend = Utils.ArrayToString(args, 1, " ");
		
		sender.sendMessage(String.format(TO, toSend, player.getName()));
		player.sendMessage(String.format(FROM, toSend, sender.getName()));
	}
	
	private void sendUsage(CommandSender sender)
	{
		Utils.sendMessage(sender, "/{send} <joueur> <quelque chose>");
	}
}
