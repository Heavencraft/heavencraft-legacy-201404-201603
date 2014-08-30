package fr.heavencraft;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class Utils
{

	/*
	 * Bukkit
	 */

	/*
	 * Player
	 */

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

	/**
	 * Permet de mettre en forme des strings longs, pour les mettre dans des livres
	 * 
	 * @param text
	 * @param lineLength
	 * @return
	 */
	public static List<String> wrapWords(String text, int lineLength)
	{
		String[] intendedLines = text.split("\\n");
		ArrayList<String> lines = new ArrayList<>();
		for (String intendedLine : intendedLines)
		{
			String[] words = intendedLine.split(" ");
			StringBuilder buffer = new StringBuilder();

			for (String word : words)
			{
				if (word.length() >= lineLength)
				{
					if (buffer.length() != 0)
					{
						lines.add(buffer.toString());
					}
					lines.add(word);
					buffer = new StringBuilder();
					continue;
				}
				if (buffer.length() + word.length() >= lineLength)
				{
					lines.add(buffer.toString());
					buffer = new StringBuilder();
				}
				if (buffer.length() != 0)
				{
					buffer.append(' ');
				}
				buffer.append(word);
			}
			lines.add(buffer.toString());
		}

		return lines;
	}

	/**
	 * Retourne le nombre d'emplacements vides dans un inventaire
	 * 
	 * @param inventory
	 * @return
	 */
	public static int getEmptySlots(Inventory inventory)
	{
		int slotsLibres = 0;
		for (ItemStack is : inventory.getContents())
		{
			if (is == null)
				slotsLibres++;
		}
		return slotsLibres;
	}
}
