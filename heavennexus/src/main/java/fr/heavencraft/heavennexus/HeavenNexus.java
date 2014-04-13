package fr.heavencraft.heavennexus;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.java.JavaPlugin;

import fr.heavencraft.heavennexus.generators.EmptyChunkGenerator;
import fr.heavencraft.heavennexus.listeners.ChatListener;
import fr.heavencraft.heavennexus.listeners.ColorSignListener;
import fr.heavencraft.heavennexus.listeners.JumpListener;
import fr.heavencraft.heavennexus.listeners.NameTagListener;
import fr.heavencraft.heavennexus.listeners.RedstoneLampListener;
import fr.heavencraft.heavennexus.listeners.ServerListener;
import fr.heavencraft.heavennexus.listeners.WatchListener;
import fr.heavencraft.heavennexus.listeners.WeatherListener;
import fr.heavencraft.heavennexus.listeners.sign.ChestSignListener;

public class HeavenNexus extends JavaPlugin {
	
	@Override
	public void onEnable()
	{
		super.onEnable();
		
		new ChatListener(this);
		new ColorSignListener(this);
		new JumpListener(this);
		new NameTagListener(this);
		new RedstoneLampListener(this);
		new ServerListener(this);
		new WatchListener(this);
		new WeatherListener(this);
		
		new ChestSignListener(this);
	}

	@Override
	public ChunkGenerator getDefaultWorldGenerator(String worldName, String id)
	{
		if (worldName.equalsIgnoreCase("world"))
			return new EmptyChunkGenerator();
		else
			return super.getDefaultWorldGenerator(worldName, id);
	}
	
	private static Location _spawn;
	
	public static Location getSpawn()
	{
		if (_spawn == null)
			_spawn = new Location(Bukkit.getWorld("world"), 0.5, 169, 0.5, 270, 0);
		
		return _spawn;
	}
}
