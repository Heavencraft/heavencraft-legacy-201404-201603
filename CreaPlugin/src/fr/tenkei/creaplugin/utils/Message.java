package fr.tenkei.creaplugin.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class Message {

	private final static String BEGIN = "{";
	private final static String END = "}";
	private final static  String OTHER = "$";
	private final static String ERROR_COLOR = ChatColor.RED.toString();
	private final static String NORMAL_COLOR = ChatColor.GOLD.toString();
	private final static String WHITE_COLOR = ChatColor.WHITE.toString();;
	
	public static void broadcastMessage(String message)
	{
		Bukkit.broadcastMessage(NORMAL_COLOR + " * " + message.replace(BEGIN, ERROR_COLOR).replace(END, NORMAL_COLOR));
	}
	  
	public static void broadcastEventMessage(String sMessage)
	{
		Bukkit.broadcastMessage(ChatColor.AQUA + "[Creative] " + sMessage.replace("{", ChatColor.GRAY.toString()).replace("}", ChatColor.WHITE.toString()));
	}
	
	public static void sendMessage(CommandSender sender, String message)
	{
		sender.sendMessage(NORMAL_COLOR + message.replace(BEGIN, ERROR_COLOR).replace(END, NORMAL_COLOR).replace(OTHER, WHITE_COLOR));
	}
	
	public static void sendError(CommandSender sender, String error)
	{
		sender.sendMessage(ERROR_COLOR + error);
	}
}
