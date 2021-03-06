package fr.heavencraft.utils;

import java.util.Collection;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldedit.bukkit.selections.Selection;

import fr.heavencraft.HeavenPlugin;
import fr.heavencraft.exceptions.HeavenException;

public class DevUtil
{
	private static HeavenPlugin plugin;

	public static HeavenPlugin getPlugin()
	{
		return plugin;
	}

	public static void setPlugin(HeavenPlugin plugin)
	{
		DevUtil.plugin = plugin;
	}

	public static void registerListener(Listener listener)
	{
		Bukkit.getPluginManager().registerEvents(listener, plugin);
		logInfo("%1$s initialized", listener.getClass().getSimpleName());
	}

	public static void logInfo(String format, Object... args)
	{
		plugin.getLogger().info(String.format(format, args));
	}

	public static void logError(String format, Object... args)
	{
		plugin.getLogger().severe(String.format(format, args));
	}

	public static WorldEditPlugin getWorldEdit()
	{
		return (WorldEditPlugin) Bukkit.getServer().getPluginManager().getPlugin("WorldEdit");
	}

	public static Selection getWESelection(Player player) throws HeavenException
	{
		final Selection selection = getWorldEdit().getSelection(player);

		if (selection == null)
			throw new HeavenException("Vous devez sélectionner une zone avec le bâton.");

		return selection;
	}

	/*
	 * Input
	 */

	public static int toInt(String s) throws HeavenException
	{
		try
		{
			return Integer.parseInt(s);
		}
		catch (final NumberFormatException ex)
		{
			throw new HeavenException("Le nombre {%1$s} est incorrect.", s);
		}
	}

	public static int toUint(String s) throws HeavenException
	{
		final int i = toInt(s);

		if (i < 0)
			throw new HeavenException("Le nombre {%1$s} est incorrect.", s);

		return i;
	}

	public static String arrayToString(Collection<String> array, int start, String separator)
	{
		return arrayToString(array.toArray(new String[array.size()]), start, separator);
	}

	public static String arrayToString(String[] array, int start, String separator)
	{
		String result = "";

		for (int i = start; i != array.length; i++)
			result += (result == "" ? "" : separator) + array[i];

		return result;
	}
}