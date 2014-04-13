package fr.heavencraft.heavenrp.worlds;

import fr.heavencraft.Utils;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.block.Biome;

public class WorldsManager
{
	public static final int RESOURCES_SIZE = 5000;
	
	private static Location _spawn;
	private static Location _spawnNether;
	private static Location _spawnTheEnd;
	
	private static Location _tuto;
	static Random rnd = new Random();

	public static void init()
	{
		if (!isLoaded("world_nether"))
		{
			WorldCreator creator = new WorldCreator("world_nether");
			creator.environment(World.Environment.NETHER);
			creator.createWorld();
		}

		if (!isLoaded("world_resources"))
		{
			WorldCreator creator = new WorldCreator("world_resources");
			creator.environment(World.Environment.NORMAL);
			//creator.seed(9139863690993604117l);
			creator.createWorld();
		}

		if (!isLoaded("world_travaux"))
		{
			WorldCreator creator = new WorldCreator("world_travaux");
			creator.environment(World.Environment.NORMAL);
			creator.createWorld();
		}

		if (!isLoaded("world_old"))
		{
			WorldCreator creator = new WorldCreator("world_old");
			creator.generator(new EmptyChunkGenerator());
			creator.environment(World.Environment.NORMAL);
			creator.createWorld();
		}
		
		if (!isLoaded("world_origine"))
		{
			WorldCreator creator = new WorldCreator("world_origine");
			creator.generator(new EmptyChunkGenerator());
			creator.environment(World.Environment.NORMAL);
			creator.createWorld();
		}

		_spawn			= new Location(getWorld(), 145.5D, 67D, 130.5D, 270F, 0F);
		_spawnNether	= new Location(getNether(), 86D, 63D, 101D, 235F, 0F);
		_spawnTheEnd	= new Location(getTheEnd(), 4.5D, 67D, 23.5D, 0F, 0F);
		_tuto			= new Location(getWorld(), -818D, 35D, -728D, 0F, 0F);
	}

	public static Location getSpawn()
	{
		return _spawn;
	}

	public static Location getSpawnNether()
	{
		return _spawnNether;
	}

	public static Location getSpawnTheEnd()
	{
		return _spawnTheEnd;
	}

	public static Location getTutoLocation()
	{
		return _tuto;
	}

	public static Location getResourcesSpawn()
	{
		int x;
		int z;
		do
		{
			x = rnd.nextInt(5000) - 2500;
			z = rnd.nextInt(5000) - 2500;
		} while ((getResources().getBiome(x, z) == Biome.OCEAN)
				|| (getResources().getBiome(x, z) == Biome.DEEP_OCEAN));

		return Utils.getSafeDestination(new Location(getResources(), x, 100.0D, z));
	}

	public static World getWorld()
	{
		return Bukkit.getWorld("world");
	}

	public static World getNether()
	{
		return Bukkit.getWorld("world_nether");
	}

	public static World getTheEnd()
	{
		return Bukkit.getWorld("world_the_end");
	}

	public static World getResources()
	{
		return Bukkit.getWorld("world_resources");
	}

	public static World getTravaux()
	{
		return Bukkit.getWorld("world_travaux");
	}

	private static boolean isLoaded(String name)
	{
		return Bukkit.getWorld(name) != null;
	}
}