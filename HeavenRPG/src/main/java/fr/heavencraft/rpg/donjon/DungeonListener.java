package fr.heavencraft.rpg.donjon;

import org.bukkit.Bukkit;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.ItemDespawnEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import fr.heavencraft.Utils.DevUtils;
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
		
		return;
		/*
		if (!(e.getEntity() instanceof Player))
			return;	
		
		Player victim = (Player) e.getEntity();
		
		Dungeon dg = DungeonManager.getDungeonByUser(victim);
		if(dg == null)
			return;
		
		if(DungeonManager.is_debug())
			DevUtils.log("~~onPlayerDamage: || Victim Health: {%1$s} || EventCausedDamage: {%2$s} || Is Deadly: {%3$b}", victim.getHealth() + " ",e.getDamage()+ " ", ((victim.getHealth() - e.getDamage()) <= 0) + " " );
		
		if ((victim.getHealth() - e.getDamage()) <= 0)
		{
			e.setDamage(0);
			dg.handlePlayerDeath(victim);
			e.setCancelled(true);
		} */
	}
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerDamageByEntity(EntityDamageByEntityEvent e) {
		if (!(e.getEntity() instanceof Player))
			return;	
		
		
		if((e.getDamager() instanceof Arrow)) {
			Arrow a = (Arrow)e.getDamager();	
			if (!(a.getShooter() instanceof Player)) 
			      return;
			e.setDamage(0);
			e.setCancelled(true);
			return;     
		}
			 
		
		Player victim = (Player) e.getEntity();		
		Dungeon dg = DungeonManager.getDungeonByUser(victim);
		if(dg == null)
			return;
		
		//On annule les dégats caussées par les joueurs entre-eux
		if(e.getDamager().getType() == EntityType.PLAYER) {
			e.setDamage(0);
			e.setCancelled(true);
			return;
		}
				
		if(DungeonManager.is_debug())
			DevUtils.log("~~onPlayerDamageByEntity: || Victim Health: {%1$s} || EventCausedDamage: {%2$s} || Is Deadly: {%3$s}",victim.getHealth() + " ",e.getDamage()+ " ", ((victim.getHealth() - e.getDamage()) <= 0) + " " );
		
		if (victim.getHealth() - e.getDamage() <= 0)
		{
			e.setDamage(0);
			dg.handlePlayerDeath(victim);
			e.setCancelled(true);
			return;
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
