package fr.heavencraft.heavennexus;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.java.JavaPlugin;

import fr.heavencraft.generators.EmptyChunkGenerator;
import fr.heavencraft.heavennexus.listeners.JumpListener;
import fr.heavencraft.heavennexus.listeners.NameTagListener;
import fr.heavencraft.heavennexus.listeners.ServerListener;
import fr.heavencraft.heavennexus.listeners.WatchListener;
import fr.heavencraft.heavennexus.listeners.WeatherListener;
import fr.heavencraft.heavennexus.listeners.sign.ChestSignListener;
import fr.heavencraft.listeners.ColoredSignsListener;
import fr.heavencraft.listeners.NoChatListener;
import fr.heavencraft.listeners.RedstoneLampListener;
import fr.heavencraft.utils.DevUtil;

public class HeavenNexus extends JavaPlugin
{
	@Override
	public void onEnable()
	{
		super.onEnable();
		DevUtil.setPlugin(this);

		// From HeavenCore
		new ColoredSignsListener();
		new NoChatListener();
		new RedstoneLampListener();

		new JumpListener(this);
		new NameTagListener();
		new ServerListener();
		new WatchListener(this);
		new WeatherListener(this);

		new ChestSignListener();
	}

	@Override
	public ChunkGenerator getDefaultWorldGenerator(String worldName, String id)
	{
		if (worldName.equalsIgnoreCase("world"))
			return new EmptyChunkGenerator();
		else
			return super.getDefaultWorldGenerator(worldName, id);
	}

	public static Location getSpawn()
	{
		return new Location(Bukkit.getWorld("world"), 0.5, 169, 0.5, 270, 0);
	}
}