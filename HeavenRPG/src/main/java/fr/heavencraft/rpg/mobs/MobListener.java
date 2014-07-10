package fr.heavencraft.rpg.mobs;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;

import fr.heavencraft.rpg.HeavenRPG;
import fr.heavencraft.rpg.zones.ZoneManager;

public class MobListener implements Listener {

	public MobListener()
	{
		Bukkit.getPluginManager().registerEvents(this, HeavenRPG.getInstance());
	}


	@EventHandler
	public void onCreatureSpawn(CreatureSpawnEvent event)
	{
		if(ZoneManager.getZone(event.getEntity().getLocation()) != null)	
		{
			MobManager.createMob(event.getEntity(), ZoneManager.getZone(event.getEntity().getLocation()).getZoneLevel());
			MobManager.showMobHealthBar(MobManager.getRPGMob(event.getEntity()));
		}
	}

	@EventHandler (ignoreCancelled = true, priority = EventPriority.HIGHEST)
	public void onEntityRegain(EntityRegainHealthEvent event) {
		Entity entity = event.getEntity();
		
		if (!(entity instanceof LivingEntity))
			return;
		if (entity instanceof Player) 
			return;	
		
		if (entity instanceof LivingEntity) {
			MobManager.showMobHealthBar(MobManager.getRPGMob((LivingEntity) entity));
		}
	}

	@EventHandler (ignoreCancelled = true, priority = EventPriority.HIGHEST)
	public void onEntityDamageEvent(EntityDamageEvent event) {
		Entity entity = event.getEntity();

		if (!(entity instanceof LivingEntity))
			return;
		if (entity instanceof Player) 
			return;	
		
		LivingEntity living = (LivingEntity) entity;
		if (living.getNoDamageTicks() > living.getMaximumNoDamageTicks() / 2F)
			return;
		
		MobManager.showMobHealthBar(MobManager.getRPGMob(living));
	}

	@EventHandler (ignoreCancelled = true, priority = EventPriority.LOW)
	 public void onEntityDeath(EntityDeathEvent event) {
		 if (event.getEntity() instanceof LivingEntity) {
			 MobManager.hideBar(MobManager.getRPGMob(event.getEntity()));
		 }
	 }
	

}
