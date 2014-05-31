package fr.heavencraft.heavenmuseum.managers;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.World.Environment;

public class WorldsManager
{
	private final static String[] WORLDS = { "world", "world_v1", "world_v2", "world_v3", "world_creative" };
	
	public static void init()
	{
		EmptyChunkGenerator chunkGenerator = new EmptyChunkGenerator();

		for (String worldName : WORLDS)
		{
			if (!isLoaded(worldName))
			{
				System.out.println("Loading world : " + worldName);
				
				WorldCreator creator = new WorldCreator(worldName);
				creator.generator(chunkGenerator);
				creator.environment(Environment.NORMAL);
				
				World world = creator.createWorld();
				world.setGameRuleValue("doFireTick", "false");
				world.setGameRuleValue("mobGriefing", "false");
				world.setGameRuleValue("doMobSpawning", "false");
			}
		}
	}

	private static boolean isLoaded(String name)
	{
		return Bukkit.getWorld(name) != null;
	}
}