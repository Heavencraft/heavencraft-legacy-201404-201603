package fr.lorgan17.maya.listeners;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.ExplosionPrimeEvent;
import org.bukkit.event.entity.ProjectileHitEvent;

import fr.lorgan17.maya.MayaPlugin;
import fr.lorgan17.maya.managers.ProtectionManager;

public class EntityListener implements Listener
{
	public EntityListener(MayaPlugin plugin)
	{
		Bukkit.getPluginManager().registerEvents(this, plugin);
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

	@EventHandler
	public void onExplosionPrime(ExplosionPrimeEvent event)
	{
		if (event.getEntityType() == EntityType.PRIMED_TNT)
		{
			event.setRadius(8);
		}
	}

	@EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
	public void onEntityExplode(EntityExplodeEvent event)
	{
		List<Block> toRemove = new ArrayList<Block>();

		for (Block block : event.blockList())
		{
			if (ProtectionManager.isProtected(block))
				toRemove.add(block);
		}

		for (Block block : toRemove)
			event.blockList().remove(block);
	}
}