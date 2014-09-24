package fr.tenkei.creaplugin.managers;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;

import fr.tenkei.creaplugin.utils.CreativeGenerator;

public class WorldsManager
{

	private static World _world_creative;
	private static World _world_travaux;

	private static World _world_biome;

	private static World _world_creativeOld;

	public WorldsManager()
	{
		if (!isLoaded("build"))
		{
			WorldCreator creator = new WorldCreator("build");
			creator.environment(Environment.NORMAL);
			creator.type(WorldType.FLAT);
			creator.createWorld();
		}

		if (!isLoaded("world_creative"))
		{
			WorldCreator creator = new WorldCreator("world_creative");
			creator.environment(Environment.NORMAL);
			creator.generator(new CreativeGenerator());
			creator.createWorld();
		}

		if (!isLoaded("world_biome"))
		{
			WorldCreator creator = new WorldCreator("world_biome");
			creator.environment(Environment.NORMAL);
			creator.type(WorldType.NORMAL);
			creator.createWorld();
		}
	}

	public static void setSpawn(Location spawn, World world)
	{
		world.setSpawnLocation(spawn.getBlockX(), spawn.getBlockY(), spawn.getBlockZ());
	}

	public static World getTheCreative()
	{
		if (_world_creative == null)
			_world_creative = Bukkit.getWorld("world_creative");

		return _world_creative;
	}

	public static World getTheTravaux()
	{
		if (_world_travaux == null)
			_world_travaux = Bukkit.getWorld("build");

		return _world_travaux;
	}

	public static World getTheCreativeBiome()
	{
		if (_world_biome == null)
			_world_biome = Bukkit.getWorld("world_biome");

		return _world_biome;
	}

	public static World getTheCreativeOld()
	{
		if (_world_creativeOld == null)
			_world_creativeOld = Bukkit.getWorld("world_creative");

		return _world_creativeOld;
	}

	private boolean isLoaded(String name)
	{
		return Bukkit.getWorld(name) != null;
	}

	public static Location getTutoLocation()
	{
		return new Location(getTheCreative(), -699.5, 100, -699.5);
	}
}