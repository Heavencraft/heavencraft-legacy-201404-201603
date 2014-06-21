package fr.heavencraft.utils;

import com.sk89q.worldguard.bukkit.WGBukkit;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;

public class WorldGuardUtil
{

	public static WorldGuardPlugin getWorldGuard()
	{
		return WGBukkit.getPlugin();
	}
}
