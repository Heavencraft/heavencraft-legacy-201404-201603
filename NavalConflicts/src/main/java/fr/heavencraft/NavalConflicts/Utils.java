package fr.heavencraft.NavalConflicts;

import java.util.Calendar;
import java.util.Date;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Player;

import fr.heavencraft.NavalConflicts.exceptions.NavalException;
import fr.heavencraft.NavalConflicts.exceptions.PlayerNotConnectedException;

public class Utils {

	private final static String BEGIN = "{";
	private final static String END = "}";
	private final static String ERROR_COLOR = ChatColor.RED.toString();
	private final static String NORMAL_COLOR = ChatColor.GOLD.toString();
	private final static String BROADCAST_COLOR = ChatColor.AQUA.toString();
	private final static String BROADCAST_COLOR_H = ChatColor.GREEN.toString();

	public enum Severity{
		/**
		 * Represents an information message.
		 */
		INFO,
		/**
		 * Represents a warning message, where something went wrong (perhaps,
		 * the recipient typed something wrong, or the configuration is using
		 * default values).
		 */
		WARNING,
		/**
		 * Represents an error message, where something failed catastrophically.
		 * Should be used sparingly, i.e. in cases of complete and total
		 * inability to do something, such as create default configuration.
		 */
		SEVERE
	}
	
	
	public static String getExactName(String name) {
		Player player = Bukkit.getPlayer(name);

		return player == null ? name : player.getName();
	}

	public static Player getPlayer(String name)
			throws PlayerNotConnectedException {
		Player player = Bukkit.getPlayer(name);

		if (player == null)
			throw new PlayerNotConnectedException(name);

		return player;
	}

	public static void teleportPlayer(Player player, Entity entity) {
		teleportPlayer(player, entity.getLocation());
	}

	public static void teleportPlayer(final Player player, Location location) {
		if (player.isInsideVehicle() && player.getVehicle() instanceof Horse) {
			final Horse horse = (Horse) player.getVehicle();

			horse.eject();

			horse.teleport(location);
			horse.setHealth(20);
			player.teleport(location);

			Utils.sendMessage(player,
					"Ton cheval a été téléporté avec toi. S'il n'est pas lˆ, {déco reco}.");

			horse.setPassenger(player);
		}

		else
			player.teleport(location);
	}

	public static void sendMessage(CommandSender sender, String message) {
		if (sender != null)
			sender.sendMessage(NORMAL_COLOR
					+ message.replace(BEGIN, ERROR_COLOR).replace(END,
							NORMAL_COLOR));
	}

	public static void sendMessage(CommandSender sender, String message,
			Object... args) {
		sendMessage(sender, String.format(message, args));
	}

	public static void sendMessage(String playerName, String message) {
		try {
			sendMessage(getPlayer(playerName), message);
		} catch (PlayerNotConnectedException ex) {
		}
	}

	public static void sendMessage(String playerName, String message,
			Object... args) {
		try {
			sendMessage(getPlayer(playerName), message, args);
		} catch (PlayerNotConnectedException ex) {
		}
	}

	public static void broadcastMessage(String message) {
		Bukkit.broadcastMessage(BROADCAST_COLOR
				+ message.replace(BEGIN, BROADCAST_COLOR_H).replace(END,
						BROADCAST_COLOR));
	}

	public static void broadcastMessage(String message, Object... args) {
		broadcastMessage(String.format(message, args));
	}
	
	public static void broadcastConsole(final CommandSender sender, final Severity severity, final String message) {
		switch(severity){
		default:
		case INFO:
			sender.sendMessage(ChatColor.GOLD + "[NavalConquest] " + ChatColor.GREEN + message);
			break;
		case WARNING:
			sender.sendMessage(ChatColor.GOLD + "[NavalConquest] " + ChatColor.RED + message);
			break;
		case SEVERE:
			sender.sendMessage(ChatColor.GOLD + "[NavalConquest] " + ChatColor.DARK_RED + message);
			break;
	}
	}
	

	public static int toInt(String s) throws NavalException {
		try {
			return Integer.parseInt(s);
		} catch (NumberFormatException ex) {
			throw new NavalException("Le nombre {%1$s} est incorrect.", s);
		}
	}

	public static int toUint(String s) throws NavalException {
		int i = toInt(s);

		if (i < 0)
			throw new NavalException("Le nombre {%1$s} est incorrect.", s);

		return i;
	}

	public static String ArrayToString(String[] array, int start,
			String separator) {
		String result = "";

		for (int i = start; i != array.length; i++)
			result += (result == "" ? "" : separator) + array[i];

		return result;
	}

	public static boolean isToday(Date date) {
		if (date == null)
			return false;

		Calendar calToday = Calendar.getInstance();
		Calendar calDate = Calendar.getInstance();
		calDate.setTime(date);

		return calToday.get(Calendar.ERA) == calDate.get(Calendar.ERA)
				&& calToday.get(Calendar.YEAR) == calDate.get(Calendar.YEAR)
				&& calToday.get(Calendar.DAY_OF_YEAR) == calDate
						.get(Calendar.DAY_OF_YEAR);
	}

	/*
	 * Pour le StoresManager du HeavenPlugin
	 */

	public static Block stringToBlock(String str) {
		String[] blockData = str.split(":");

		if (blockData.length != 4)
			return null;

		String worldName = blockData[0];
		World world = Bukkit.getWorld(worldName);

		if (world == null)
			return null;

		int X = Integer.parseInt(blockData[1]);
		int Y = Integer.parseInt(blockData[2]);
		int Z = Integer.parseInt(blockData[3]);

		return world.getBlockAt(X, Y, Z);
	}

	public static String blockToString(Block block) {
		Location location = block.getLocation();
		return location.getWorld().getName() + ":" + location.getBlockX() + ":"
				+ location.getBlockY() + ":" + location.getBlockZ();
	}

	public static boolean blocksEquals(Block blockA, Block blockB) {
		if (blockA.getX() != blockB.getX())
			return false;
		if (blockA.getY() != blockB.getY())
			return false;
		if (blockA.getZ() != blockB.getZ())
			return false;

		if (blockA.getWorld() != blockB.getWorld())
			return false;

		return true;
	}

	public static boolean isNumeric(String str, String str2) {
		return isNumeric(str) && isNumeric(str2);
	}

	public static boolean isNumeric(String str) {
		return str.matches("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
	}

	public static boolean isInteger(String str) {
		return str.matches("[0-9]+");
	}

	private static boolean isBlockAboveAir(World world, int x, int y, int z) {
		return world.getBlockAt(x, y - 1, z).getType() == Material.AIR;
	}

	private static boolean isBlockUnsafe(World world, int x, int y, int z) {
		Block below = world.getBlockAt(x, y - 1, z);
		if ((below.getType() == Material.LAVA)
				|| (below.getType() == Material.STATIONARY_LAVA)) {
			return true;
		}

		if (below.getType() == Material.FIRE) {
			return true;
		}

		if ((world.getBlockAt(x, y, z).getType() != Material.AIR)
				|| (world.getBlockAt(x, y + 1, z).getType() != Material.AIR)) {
			return true;
		}
		return isBlockAboveAir(world, x, y, z);
	}

	public static Location getSafeDestination(Location loc) {
		World world = loc.getWorld();
		int x = loc.getBlockX();
		int y = loc.getBlockY();
		int z = loc.getBlockZ();

		while (isBlockAboveAir(world, x, y, z)) {
			y--;
			if (y >= 0)
				continue;
			return null;
		}

		while (isBlockUnsafe(world, x, y, z)) {
			y++;
			if (y < 255)
				continue;
			x++;
		}

		while (isBlockUnsafe(world, x, y, z)) {
			y--;
			if (y > 1)
				continue;
			y = 255;
			x++;
		}

		return new Location(world, x + 0.5D, y, z + 0.5D, loc.getYaw(),
				loc.getPitch());
	}
	
	/**
	 * 
	 * @param string
	 * @return the string with a capital first letter and the rest lowercase
	 */
	public static String getWord(String string) {
		if (string != null)
		{
			string = string.replaceAll("_", " ");
			string = string.toLowerCase();
			string = string.replaceFirst(String.valueOf(string.charAt(0)), String.valueOf(string.charAt(0)).toUpperCase());

		}
		return string;
	}
	public static String format(String string){
		return (string.replaceAll("&", "§"));
	}
}
