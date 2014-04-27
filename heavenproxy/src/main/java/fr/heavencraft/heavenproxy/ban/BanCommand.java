package fr.heavencraft.heavenproxy.ban;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import fr.heavencraft.heavenproxy.Utils;
import fr.heavencraft.heavenproxy.chat.DisconnectReasonManager;
import fr.heavencraft.heavenproxy.commands.HeavenCommand;
import fr.heavencraft.heavenproxy.exceptions.HeavenException;
import fr.heavencraft.heavenproxy.exceptions.PlayerNotConnectedException;
import fr.heavencraft.heavenproxy.listeners.LogListener;

public class BanCommand extends HeavenCommand
{
	private final static String BAN_MESSAGE = "Vous avez été banni du serveur par %1$s :\n\n%2$s";
	private final static String BAN = "B|%1$s|%2$s";

	public BanCommand()
	{
		super("ban", "heavencraft.commands.ban", new String[] { "gban" });
	}

	@Override
	protected void onCommand(CommandSender sender, String[] args) throws HeavenException
	{
		String playerName;
		String reason;

		switch (args.length)
		{
			case 0:
				sendUsage(sender);
				return;
			case 1:
				playerName = Utils.getRealName(args[0]);
				reason = "";
				break;
			default:
				playerName = Utils.getRealName(args[0]);
				reason = Utils.ArrayToString(args, 1, " ");
				break;
		}

		banPlayer(playerName, sender.getName(), reason);
		Utils.sendMessage(sender, "{%1$s} a été banni d'Heavencraft.", playerName);
	}

	public static void banPlayer(String playerName, String bannedBy, String reason) throws HeavenException
	{
		BanManager.banPlayer(playerName, bannedBy, reason);
		LogListener.addBan(playerName, bannedBy, reason);

		try
		{
			ProxiedPlayer player = Utils.getPlayer(playerName);

			DisconnectReasonManager.addReason(player.getName(), String.format(BAN, bannedBy, reason));
			Utils.kickPlayer(player, String.format(BAN_MESSAGE, bannedBy, reason));
		}
		catch (PlayerNotConnectedException ex)
		{
		}
	}

	private static void sendUsage(CommandSender sender)
	{
		Utils.sendMessage(sender, "/{ban} <joueur>");
		Utils.sendMessage(sender, "/{ban} <joueur> <raison>");
	}
}