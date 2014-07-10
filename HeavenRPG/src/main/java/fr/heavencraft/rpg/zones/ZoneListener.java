package fr.heavencraft.rpg.zones;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;

import fr.heavencraft.rpg.HeavenRPG;

public class ZoneListener implements Listener{

	public ZoneListener()
	{
		Bukkit.getPluginManager().registerEvents(this, HeavenRPG.getInstance());
	}


	



	
}



