package fr.heavencraft.heavenproxy.mute;

import net.md_5.bungee.api.CommandSender;
import fr.heavencraft.heavenproxy.Utils;
import fr.heavencraft.heavenproxy.commands.HeavenCommand;
import fr.heavencraft.heavenproxy.exceptions.HeavenException;
import fr.heavencraft.heavenproxy.exceptions.PlayerNotConnectedException;

public class MuteCommand extends HeavenCommand
{
	public MuteCommand()
	{
		super("mute", "heavencraft.commands.mute", new String[] {});
	}

	@Override
	protected void onCommand(CommandSender sender, String[] args) throws HeavenException
	{
		if (args.length != 2)
		{
			sendUsage(sender);
			return;
		}

		String player = Utils.getRealName(args[0]);
		int minutes = Utils.toUint(args[1]);
		
		if (minutes > 60)
			minutes = 60;

		MuteManager.mutePlayer(player, minutes);

		Utils.sendMessage(sender, "Le joueur {%1$s} a été mute pour {%2$s} minutes.", player, minutes);

		try
		{
			Utils.sendMessage(Utils.getPlayer(player), "Vous avez été mute pour {%1$s} minutes.", minutes);
		}
		catch (PlayerNotConnectedException ex)
		{
		}
	}

	private static void sendUsage(CommandSender sender)
	{
		Utils.sendMessage(sender, "/{mute} <joueur> <temps en minutes>");
	}
}