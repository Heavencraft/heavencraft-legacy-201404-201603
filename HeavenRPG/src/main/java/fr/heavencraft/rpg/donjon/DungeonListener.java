package fr.heavencraft.rpg.donjon;

import org.bukkit.Bukkit;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

import fr.heavencraft.rpg.HeavenRPG;
import fr.heavencraft.rpg.donjon.Dungeon.DungeonRoom;

public class DungeonListener implements Listener {
	public DungeonListener()
	{
		Bukkit.getPluginManager().registerEvents(this, HeavenRPG.getInstance());
	}
	
	@EventHandler
	public void onCreatureSpawn(CreatureSpawnEvent event)
	{
		if (!(event.getEntity() instanceof LivingEntity))
			return;
		
		DungeonRoom dgr = DungeonManager.getRoomByLocation(event.getEntity().getLocation());
		if(dgr != null)
		{
			Bukkit.broadcastMessage("Spawn de mob dans room: " + dgr.get_index());
			dgr.add_mob(event.getEntity());
		}
		
		
	}
	
	
}
