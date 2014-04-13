package fr.heavencraft.heavengame;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;

public class HeavenGame extends JavaPlugin
{
	@Override
	public void onEnable()
	{
		super.onEnable();

		new ServerListener(this);
	}

	public static Location getSpawn()
	{
		return new Location(Bukkit.getWorld("world"), 0.5, 4, 0.5);
	}

	public static String getServerName()
	{
		if (Bukkit.getPluginManager().getPlugin("Backbone") != null)
			return "Backbone";
		else if (Bukkit.getPluginManager().getPlugin("Infected") != null)
			return "Infecté";
		else if (Bukkit.getPluginManager().getPlugin("TNTRun") != null)
			return "TNT Run";
		else if (Bukkit.getPluginManager().getPlugin("MarioKart") != null)
			return "Mario Kart";
		else
			return "HeavenGame";
	}
}