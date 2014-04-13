package fr.heavencraft.heavenproxy;

import com.mojang.api.profiles.HttpProfileRepository;
import com.mojang.api.profiles.Profile;
import com.mojang.api.profiles.ProfileCriteria;
import com.mojang.api.profiles.ProfileRepository;

import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import fr.heavencraft.heavenproxy.exceptions.HeavenException;
import fr.heavencraft.heavenproxy.exceptions.PlayerNotConnectedException;

public class Utils {
	
	private final static String BEGIN = "{";
	private final static String END = "}";
	private final static String GOLD = ChatColor.GOLD.toString();
	private final static String RED = ChatColor.RED.toString();
	
	private final static String MINECRAFT = "minecraft";
	
	public static ProxiedPlayer getPlayer(String name) throws PlayerNotConnectedException
	{
		for (ProxiedPlayer player : BungeeCord.getInstance().getPlayers())
			if (player.getName().toLowerCase().startsWith(name.toLowerCase()))
				return player;
		
		throw new PlayerNotConnectedException(name);
	}
	
	public static boolean IsConnected(String name)
	{
		try {
			getPlayer(name);
			return true;
		} catch (PlayerNotConnectedException e) {
			return false;
		}
	}
	
	public static String getRealName(String name)
	{
		try
		{
			return getPlayer(name).getName();
		}
		catch (PlayerNotConnectedException ex)
		{
			return name;
		}
	}
	
	public static int toInt(String s) throws HeavenException
	{
		try
		{
			return Integer.parseInt(s);
		}
		catch (NumberFormatException ex)
		{
			throw new HeavenException("Le nombre {%1$s} est incorrect.", s);
		}
	}
	
	public static int toUint(String s) throws HeavenException
	{
		int i = toInt(s);
		
		if (i < 0)
			throw new HeavenException("Le nombre {%1$s} est incorrect.", s);
			
		return i;
	}
	
	public static String getUUID(String playerName)
	{
        ProfileRepository repository = new HttpProfileRepository();
        ProfileCriteria criteria = new ProfileCriteria(playerName, MINECRAFT);

        Profile[] profiles = repository.findProfilesByCriteria(criteria);
        
        if (profiles.length == 1)
        	return profiles[0].getId();
        else
        	return "";
	}
	
	public static void sendMessage(CommandSender sender, String message)
	{
		if (sender != null)
		{
			message = GOLD + message.replace(BEGIN, RED).replace(END, GOLD);
			
			for (String line : message.split("\n"))
				sender.sendMessage(line);
		}
	}
	
	public static void sendMessage(CommandSender sender, String message, Object... args)
	{
		sendMessage(sender, String.format(message, args));
	}

	public static void broadcastMessage(String message)
	{
		message = GOLD + message.replace(BEGIN, RED).replace(END, GOLD);

		for (String line : message.split("\n"))
			BungeeCord.getInstance().broadcast(line);
	}
	
	public static void broadcastMessage(String message, Object... args)
	{
		broadcastMessage(String.format(message, args));
	}
	
	public static void broadcast(String message, String permission)
	{
		for (ProxiedPlayer player : BungeeCord.getInstance().getPlayers())
			if (player.hasPermission(permission))
				player.sendMessage(message);
	}
	
	public static String ArrayToString(String[] array, int start, String separator)
	{
		String result = "";
		
		for (int i = start; i != array.length; i++)
			result += (result == "" ? "" : separator) + array[i];
		
		return result;
	}
	
	public static String getPrefix(ProxiedPlayer player)
	{
		String serverName = player.getServer().getInfo().getName();
		
		if (serverName.equalsIgnoreCase("nexus"))
			return "Nex";
		else if (serverName.equalsIgnoreCase("semirp"))
			return "SRP";
		else if (serverName.equalsIgnoreCase("origines"))
			return "Ori ";
		else if (serverName.equalsIgnoreCase("creafun"))
			return "Créa";
		else if (serverName.equalsIgnoreCase("factions"))
			return "Fac";
		else if (serverName.equalsIgnoreCase("infected"))
			return "Inf";
		else if (serverName.equalsIgnoreCase("mariokart"))
			return "MK";
		else if (serverName.equalsIgnoreCase("tntrun"))
			return "TNT";
		else if (serverName.equalsIgnoreCase("ultrahard"))
			return "UH";
		else if (serverName.equalsIgnoreCase("backbone"))
			return "BB";
		else if (serverName.equalsIgnoreCase("paintball"))
			return "PB";
		else
			return serverName.substring(0, 3);
	}
	
	public static boolean isInteger(String s)
	{
		try
		{
			Integer.parseInt(s);
			return true;
		}
		catch (NumberFormatException ex)
		{
			return false;
		}
	}
}
