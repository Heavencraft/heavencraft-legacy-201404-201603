package fr.heavencraft.heavenproxy.commands;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import fr.heavencraft.heavenproxy.Utils;
import fr.heavencraft.heavenproxy.exceptions.HeavenException;
import fr.heavencraft.heavenproxy.mute.MuteManager;

public class MeCommand extends HeavenCommand {
	private final static String ME_MESSAGE = " * %1$s %2$s";
	
	public MeCommand()
	{
		super("me", "", new String[] {});
	}

	@Override
	protected void onCommand(CommandSender sender, String[] args) throws HeavenException
	{
		if (args.length == 0)
		{
			sendUsage(sender);
			return;
		}

		// Si le joueur est mute
		if (MuteManager.isMuted(sender.getName()))
			return;
		
		String message = String.format(ME_MESSAGE, sender.getName(), Utils.ArrayToString(args, 0, " "));
		ProxyServer.getInstance().broadcast(message);
	}
	
	private void sendUsage(CommandSender sender)
	{
		Utils.sendMessage(sender, "/{me} <message>");
	}
}