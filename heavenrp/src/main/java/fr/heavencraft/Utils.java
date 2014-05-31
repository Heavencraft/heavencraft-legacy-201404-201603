package fr.heavencraft;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

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
import org.bukkit.event.Listener;

import com.mojang.api.profiles.HttpProfileRepository;
import com.mojang.api.profiles.Profile;
import com.mojang.api.profiles.ProfileRepository;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldedit.bukkit.selections.Selection;
import com.sk89q.worldguard.bukkit.WGBukkit;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;

import fr.heavencraft.exceptions.HeavenException;
import fr.heavencraft.exceptions.PlayerNotConnectedException;
import fr.heavencraft.exceptions.UUIDNotFoundException;
import fr.heavencraft.heavenrp.HeavenRP;

public class Utils
{

	private final static String BEGIN = "{";
	private final static String END = "}";
	private final static String ERROR_COLOR = ChatColor.RED.toString();
	private final static String NORMAL_COLOR = ChatColor.GOLD.toString();
	private final static String BROADCAST_COLOR = ChatColor.AQUA.toString();
	private final static String BROADCAST_COLOR_H = ChatColor.GREEN.toString();

	/*
	 * Bukkit
	 */

	public static void registerListener(Listener listener)
	{
		Bukkit.getPluginManager().registerEvents(listener, HeavenRP.getInstance());
		logInfo("%1$s initialized", listener.getClass().getSimpleName());
	}

	public static void logInfo(String format, Object... args)
	{
		HeavenRP.getInstance().getLogger().info(String.format(format, args));
	}

	/*
	 * Player
	 */

	public static String getExactName(String name)
	{
		Player player = Bukkit.getPlayer(name);

		return player == null ? name : player.getName();
	}

	public static String getUUID(Player player)
	{
		return player.getUniqueId().toString().replaceAll("-", "");
	}

	public static String getUUID(String playerName) throws HeavenException
	{
		try
		{
			return getUUID(getPlayer(playerName));
		}
		catch (PlayerNotConnectedException e)
		{
			ProfileRepository repository = new HttpProfileRepository("minecraft");
			Profile[] profiles = repository.findProfilesByNames(playerName);

			if (profiles.length == 1)
				return profiles[0].getId();
			else
				throw new UUIDNotFoundException(playerName);
		}
	}

	public static Player getPlayer(String name) throws PlayerNotConnectedException
	{
		Player player = Bukkit.getPlayer(name);

		if (player == null)
			throw new PlayerNotConnectedException(name);

		return player;
	}

	public static void teleportPlayer(Player player, Entity entity)
	{
		teleportPlayer(player, entity.getLocation());
	}

	public static void teleportPlayer(final Player player, Location location)
	{
		if (player.isInsideVehicle() && player.getVehicle() instanceof Horse)
		{
			final Horse horse = (Horse) player.getVehicle();

			horse.eject();

			horse.teleport(location);
			horse.setHealth(horse.getMaxHealth());
			player.teleport(location);

			Utils.sendMessage(player, "Ton cheval a été téléporté avec toi. S'il n'est pas là, {déco reco}.");

			horse.setPassenger(player);
		}

		else
			player.teleport(location);
	}

	public static WorldGuardPlugin getWorldGuard()
	{
		return WGBukkit.getPlugin();
	}

	public static WorldEditPlugin getWorldEdit()
	{
		return (WorldEditPlugin) Bukkit.getServer().getPluginManager().getPlugin("WorldEdit");
	}

	public static Selection getWESelection(Player player) throws HeavenException
	{
		Selection selection = Utils.getWorldEdit().getSelection(player);

		if (selection == null)
			throw new HeavenException("Vous devez sélectionner une zone avec le bâton.");

		return selection;
	}

	public static void sendMessage(CommandSender sender, String message)
	{
		if (sender != null)
			sender.sendMessage(NORMAL_COLOR + message.replace(BEGIN, ERROR_COLOR).replace(END, NORMAL_COLOR));
	}

	public static void sendMessage(CommandSender sender, String message, Object... args)
	{
		sendMessage(sender, String.format(message, args));
	}

	public static void sendMessage(List<CommandSender> senders, String message, Object... args)
	{
		for (CommandSender sender : senders)
			sendMessage(sender, message, args);
	}

	public static void sendMessage(String playerName, String message)
	{
		try
		{
			sendMessage(getPlayer(playerName), message);
		}
		catch (PlayerNotConnectedException ex)
		{
		}
	}

	public static void sendMessage(String playerName, String message, Object... args)
	{
		try
		{
			sendMessage(getPlayer(playerName), message, args);
		}
		catch (PlayerNotConnectedException ex)
		{
		}
	}

	public static void broadcastMessage(String message)
	{
		Bukkit.broadcastMessage(BROADCAST_COLOR
				+ message.replace(BEGIN, BROADCAST_COLOR_H).replace(END, BROADCAST_COLOR));
	}

	public static void broadcastMessage(String message, Object... args)
	{
		broadcastMessage(String.format(message, args));
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

	public static String ArrayToString(String[] array, int start, String separator)
	{
		String result = "";

		for (int i = start; i != array.length; i++)
			result += (result == "" ? "" : separator) + array[i];

		return result;
	}

	public static boolean isToday(Date date)
	{
		if (date == null)
			return false;

		Calendar calToday = Calendar.getInstance();
		Calendar calDate = Calendar.getInstance();
		calDate.setTime(date);

		return calToday.get(Calendar.ERA) == calDate.get(Calendar.ERA)
				&& calToday.get(Calendar.YEAR) == calDate.get(Calendar.YEAR)
				&& calToday.get(Calendar.DAY_OF_YEAR) == calDate.get(Calendar.DAY_OF_YEAR);
	}

	/*
	 * Pour le StoresManager du HeavenPlugin
	 */

	public static Block stringToBlock(String str)
	{
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

	public static String blockToString(Block block)
	{
		Location location = block.getLocation();
		return location.getWorld().getName() + ":" + location.getBlockX() + ":" + location.getBlockY() + ":"
				+ location.getBlockZ();
	}

	public static boolean blocksEquals(Block blockA, Block blockB)
	{
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

	public static boolean isNumeric(String str, String str2)
	{
		return isNumeric(str) && isNumeric(str2);
	}

	public static boolean isNumeric(String str)
	{
		return str.matches("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
	}

	public static boolean isInteger(String str)
	{
		return str.matches("[0-9]+");
	}

	private static boolean isBlockAboveAir(World world, int x, int y, int z)
	{
		return world.getBlockAt(x, y - 1, z).getType() == Material.AIR;
	}

	private static boolean isBlockUnsafe(World world, int x, int y, int z)
	{
		Block below = world.getBlockAt(x, y - 1, z);
		if ((below.getType() == Material.LAVA) || (below.getType() == Material.STATIONARY_LAVA))
		{
			return true;
		}

		if (below.getType() == Material.FIRE)
		{
			return true;
		}

		if ((world.getBlockAt(x, y, z).getType() != Material.AIR)
				|| (world.getBlockAt(x, y + 1, z).getType() != Material.AIR))
		{
			return true;
		}
		return isBlockAboveAir(world, x, y, z);
	}

	public static Location getSafeDestination(Location loc)
	{
		World world = loc.getWorld();
		int x = loc.getBlockX();
		int y = loc.getBlockY();
		int z = loc.getBlockZ();

		while (isBlockAboveAir(world, x, y, z))
		{
			y--;
			if (y >= 0)
				continue;
			return null;
		}

		while (isBlockUnsafe(world, x, y, z))
		{
			y++;
			if (y < 255)
				continue;
			x++;
		}

		while (isBlockUnsafe(world, x, y, z))
		{
			y--;
			if (y > 1)
				continue;
			y = 255;
			x++;
		}

		return new Location(world, x + 0.5D, y, z + 0.5D, loc.getYaw(), loc.getPitch());
	}
}
