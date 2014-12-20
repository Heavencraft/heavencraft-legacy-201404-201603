package fr.lorgan17.maya.listeners;

import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.ExplosionPrimeEvent;
import org.bukkit.event.entity.ProjectileHitEvent;

import fr.lorgan17.maya.MayaListener;

/**
 * ExplosionListener Increase the power of TNT and add explosive arrows.
 * 
 * @author lorgan17
 */
public class ExplosionListener extends MayaListener
{
	@EventHandler
	public void onExplosionPrime(ExplosionPrimeEvent event)
	{
		if (event.getEntityType() == EntityType.PRIMED_TNT)
		{
			event.setRadius(8);
		}
	}

	@EventHandler
	public void onProjectileHit(ProjectileHitEvent event)
	{
		if (event.getEntityType() == EntityType.ARROW)
		{
			Entity entity = event.getEntity();
			entity.getWorld().createExplosion(entity.getLocation(), 12);
		}
	}
}