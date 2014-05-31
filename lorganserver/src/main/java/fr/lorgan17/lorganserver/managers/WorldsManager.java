package fr.lorgan17.lorganserver.managers;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

public class WorldsManager {
	
	private static World _world;
	private static World _world_nether;
	private static World _world_the_end;

	public static Location getSpawn()
	{
		return getWorld().getSpawnLocation();
	}
	
	public static void setSpawn(Location spawn)
	{
		getWorld().setSpawnLocation(spawn.getBlockX(), spawn.getBlockY(), spawn.getBlockZ());
	}
	
	public static World getWorld()
	{
		if (_world == null)
			_world = Bukkit.getWorld("world");
		
		return _world;
	}
	
	public static World getNether()
	{
		if (_world_nether == null)
			_world_nether = Bukkit.getWorld("world_nether");
		
		return _world_nether;
	}
	
	public static World getTheEnd()
	{
		if (_world_the_end == null)
			_world_the_end = Bukkit.getWorld("world_the_end");
		
		return _world_the_end;
	}
}
