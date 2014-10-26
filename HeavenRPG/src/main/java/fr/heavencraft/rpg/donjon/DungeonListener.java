package fr.heavencraft.rpg.donjon;

import org.bukkit.Bukkit;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.ItemDespawnEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import fr.heavencraft.rpg.HeavenRPG;
import fr.heavencraft.rpg.donjon.Dungeon.DungeonRoom;

public class DungeonListener implements Listener {
	public DungeonListener()
	{
		Bukkit.getPluginManager().registerEvents(this, HeavenRPG.getInstance());
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onCreatureSpawn(CreatureSpawnEvent event)
	{
		if (!(event.getEntity() instanceof LivingEntity))
			return;

		//exclusions:
		if(event.getEntity().getType() == EntityType.BAT ||
				event.getEntity().getType() == EntityType.CREEPER ||
				event.getEntity().getType() == EntityType.ENDERMAN)
			return;
		
		DungeonRoom dgr = DungeonManager.getRoomByLocation(event.getEntity().getLocation());
		if(dgr != null)
			dgr.add_mob(event.getEntity());
	}
	
	@EventHandler
	public void customDespawn(ItemDespawnEvent event)
	{
		if (!(event.getEntity() instanceof LivingEntity)) 
			return;

		DungeonRoom dgr = DungeonManager.getRoomByEntity(event.getEntity());

		if(dgr != null)
			dgr.remove_mob(event.getEntity());
	}

	@EventHandler (ignoreCancelled = true, priority = EventPriority.LOW)
	public void onEntityDeath(EntityDeathEvent event) {
		if (!(event.getEntity() instanceof LivingEntity)) 
			return;

		DungeonRoom dgr = DungeonManager.getRoomByEntity(event.getEntity());

		if(dgr != null)
			dgr.remove_mob(event.getEntity());
	}
	
	@EventHandler (ignoreCancelled = true, priority = EventPriority.LOW)
	public void onEntityExplode(EntityExplodeEvent event) {
		if (!(event.getEntity() instanceof LivingEntity)) 
			return;

		DungeonRoom dgr = DungeonManager.getRoomByEntity(event.getEntity());

		if(dgr != null)
			dgr.remove_mob(event.getEntity());
	}
	
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerDamage(EntityDamageEvent e) {

		if (!(e.getEntity() instanceof Player))
			return;
		Player victim = (Player) e.getEntity();
		Dungeon dg = DungeonManager.getDungeonByUser(victim);
		if(dg == null)
			return;
		
		if (victim.getHealth() - e.getDamage() <= 0)
		{
			e.setDamage(0);
			dg.handlePlayerDeath(victim);
			e.setCancelled(true);
		}
	}
	
	@EventHandler (ignoreCancelled = true, priority = EventPriority.LOW)
	public void onPlayerDisconnect(PlayerQuitEvent event) {
		Dungeon dg = DungeonManager.getDungeonByUser(event.getPlayer());
		if(dg == null)
			return;
		dg.handlePlayerDisconnect(event.getPlayer());
		
	}


}
