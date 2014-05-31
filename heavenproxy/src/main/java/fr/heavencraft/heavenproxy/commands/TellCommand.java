package fr.heavencraft.heavenproxy.commands;

import java.util.HashMap;
import java.util.Map;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import fr.heavencraft.heavenproxy.Utils;
import fr.heavencraft.heavenproxy.exceptions.HeavenException;

public class TellCommand extends HeavenCommand
{
	private final static String FROM = "§d[de %1$s]§r %2$s";
	private final static String TO = "§d[à %1$s]§r %2$s";

	public TellCommand()
	{
		super("tell", null, new String[] { "m", "msg", "t", "w", "whisper" });
	}

	@Override
	protected void onCommand(CommandSender sender, String[] args) throws HeavenException
	{
		if (args.length < 2)
		{
			sendUsage(sender);
			return;
		}

		ProxiedPlayer target = Utils.getPlayer(args[0]);
		String message = Utils.ArrayToString(args, 1, " ");

		sendPrivateMessage(sender, target, message);
	}

	private static void sendUsage(CommandSender sender)
	{
		Utils.sendMessage(sender, "/{m} <joueur> <message> : envoie un message privé à un joueur.");
	}

	private static final Map<String, String> lastFrom = new HashMap<String, String>();

	private static void sendPrivateMessage(CommandSender sender, CommandSender target, String message)
	{
		sender.sendMessage(TextComponent.fromLegacyText(String.format(TO, target.getName(), message)));
		target.sendMessage(TextComponent.fromLegacyText(String.format(FROM, sender.getName(), message)));

		lastFrom.put(target.getName(), sender.getName());
	}

	public static void reply(CommandSender sender, String message) throws HeavenException
	{
		CommandSender target = Utils.getPlayer(lastFrom.get(sender.getName()));

		sendPrivateMessage(sender, target, message);
	}
}