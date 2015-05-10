package fr.heavencraft.HellCraft;

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

import fr.heavencraft.HellCraft.exceptions.HellException;
import fr.heavencraft.HellCraft.exceptions.PlayerNotConnectedException;

public class Utils
{
	private static final String BEGIN = "{";
	private static final String END = "}";
	private static final String ERROR_COLOR = ChatColor.RED.toString();
	private static final String NORMAL_COLOR = ChatColor.GOLD.toString();
	private static final String BROADCAST_COLOR = ChatColor.AQUA.toString();
	private static final String BROADCAST_COLOR_H = ChatColor.GREEN.toString();

	public static String getExactName(String name)
	{
		final Player player = Bukkit.getPlayer(name);

		return player == null ? name : player.getName();
	}

	public static Player getPlayer(String name) throws PlayerNotConnectedException
	{
		final Player player = Bukkit.getPlayer(name);

		if (player == null)
		{
			throw new PlayerNotConnectedException(name);
		}
		return player;
	}

	public static void teleportPlayer(Player player, Entity entity)
	{
		teleportPlayer(player, entity.getLocation());
	}

	public static void teleportPlayer(Player player, Location location)
	{
		if ((player.isInsideVehicle()) && ((player.getVehicle() instanceof Horse)))
		{
			final Horse horse = (Horse) player.getVehicle();

			horse.eject();

			horse.teleport(location);
			horse.setHealth(20.0D);
			player.teleport(location);

			sendMessage(player, "Ton cheval a ŽtŽ tŽlŽportŽ avec toi. S'il n'est pas lˆ, {dŽco reco}.");

			horse.setPassenger(player);
		}
		else
		{
			player.teleport(location);
		}
	}

	public static void sendMessage(CommandSender sender, String message)
	{
		if (sender != null)
			sender.sendMessage(NORMAL_COLOR + message.replace("{", ERROR_COLOR).replace("}", NORMAL_COLOR));
	}

	public static void sendMessage(CommandSender sender, String message, Object[] args)
	{
		sendMessage(sender, String.format(message, args));
	}

	public static void sendMessage(String playerName, String message)
	{
		try
		{
			sendMessage(getPlayer(playerName), message);
		}
		catch (final PlayerNotConnectedException localPlayerNotConnectedException)
		{
		}
	}

	public static void sendMessage(String playerName, String message, Object[] args)
	{
		try
		{
			sendMessage(getPlayer(playerName), message, args);
		}
		catch (final PlayerNotConnectedException localPlayerNotConnectedException)
		{
		}
	}

	public static void broadcastMessage(String message)
	{
		Bukkit.broadcastMessage(BROADCAST_COLOR + message.replace("{", BROADCAST_COLOR_H).replace("}", BROADCAST_COLOR));
	}

	public static void broadcastMessage(String message, Object[] args)
	{
		broadcastMessage(String.format(message, args));
	}

	public static int toInt(String s) throws HellException
	{
		try
		{
			return Integer.parseInt(s);
		}
		catch (final NumberFormatException ex)
		{
			throw new HellException("Le nombre {%1$s} est incorrect.", s);
		}
	}

	public static int toUint(String s) throws HellException
	{
		final int i = toInt(s);

		if (i < 0)
			throw new HellException("Le nombre {%1$s} est incorrect.", s);

		return i;
	}

	public static String ArrayToString(String[] array, int start, String separator)
	{
		String result = "";

		for (int i = start; i != array.length; i++)
		{
			result = result + (result == "" ? "" : separator) + array[i];
		}
		return result;
	}

	public static boolean isToday(Date date)
	{
		if (date == null)
		{
			return false;
		}
		final Calendar calToday = Calendar.getInstance();
		final Calendar calDate = Calendar.getInstance();
		calDate.setTime(date);

		return (calToday.get(0) == calDate.get(0)) && (calToday.get(1) == calDate.get(1)) && (calToday.get(6) == calDate.get(6));
	}

	public static Block stringToBlock(String str)
	{
		final String[] blockData = str.split(":");

		if (blockData.length != 4)
		{
			return null;
		}
		final String worldName = blockData[0];
		final World world = Bukkit.getWorld(worldName);

		if (world == null)
		{
			return null;
		}
		final int X = Integer.parseInt(blockData[1]);
		final int Y = Integer.parseInt(blockData[2]);
		final int Z = Integer.parseInt(blockData[3]);

		return world.getBlockAt(X, Y, Z);
	}

	public static String blockToString(Block block)
	{
		final Location location = block.getLocation();
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
		{
			return false;
		}
		if (blockA.getWorld() != blockB.getWorld())
		{
			return false;
		}
		return true;
	}

	public static boolean isNumeric(String str, String str2)
	{
		return (isNumeric(str)) && (isNumeric(str2));
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
		final Block below = world.getBlockAt(x, y - 1, z);
		if ((below.getType() == Material.LAVA) || (below.getType() == Material.STATIONARY_LAVA))
		{
			return true;
		}

		if (below.getType() == Material.FIRE)
		{
			return true;
		}

		if ((world.getBlockAt(x, y, z).getType() != Material.AIR) || (world.getBlockAt(x, y + 1, z).getType() != Material.AIR))
		{
			return true;
		}
		return isBlockAboveAir(world, x, y, z);
	}

	public static Location getSafeDestination(Location loc)
	{
		final World world = loc.getWorld();
		int x = loc.getBlockX();
		int y = loc.getBlockY();
		final int z = loc.getBlockZ();

		while (isBlockAboveAir(world, x, y, z))
		{
			y--;
			if (y < 0)
			{
				return null;
			}
		}
		while (isBlockUnsafe(world, x, y, z))
		{
			y++;
			if (y >= 255)
			{
				x++;
			}
		}
		while (isBlockUnsafe(world, x, y, z))
		{
			y--;
			if (y <= 1)
			{
				y = 255;
				x++;
			}
		}
		return new Location(world, x + 0.5D, y, z + 0.5D, loc.getYaw(), loc.getPitch());
	}
}