package fr.heavencraft.heavenmuseum;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;

import fr.heavencraft.heavenmuseum.listeners.ServerListener;
import fr.heavencraft.heavenmuseum.managers.WorldsManager;

public class HeavenMuseum extends JavaPlugin
{
	@Override
	public void onEnable()
	{
		super.onEnable();

		new ServerListener(this);

		WorldsManager.init();
	}

	public static Location getSpawn()
	{
		return new Location(Bukkit.getWorld("world"), 0.5, 4, 0.5);
	}
}