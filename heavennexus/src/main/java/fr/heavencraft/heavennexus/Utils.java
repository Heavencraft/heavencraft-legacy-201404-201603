package fr.heavencraft.heavennexus;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class Utils {

	private final static String BEGIN = "{";
	private final static String END = "}";
	private final static String ERROR_COLOR = ChatColor.RED.toString();
	private final static String NORMAL_COLOR = ChatColor.GOLD.toString();
	
	public static void sendMessage(CommandSender sender, String message)
	{
		if (sender != null)
			sender.sendMessage(NORMAL_COLOR + message.replace(BEGIN, ERROR_COLOR).replace(END, NORMAL_COLOR));
	}
	
	public static void sendMessage(CommandSender sender, String message, Object... args)
	{
		sendMessage(sender, String.format(message, args));
	}
}