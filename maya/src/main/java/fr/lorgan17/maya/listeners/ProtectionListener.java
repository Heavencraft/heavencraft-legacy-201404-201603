package fr.lorgan17.maya.listeners;

import java.util.Iterator;

import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;

import fr.lorgan17.maya.MayaListener;
import fr.lorgan17.maya.managers.ProtectionManager;

public class ProtectionListener extends MayaListener
{
	@EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
	public void onBlockBreak(BlockBreakEvent event)
	{
		if (ProtectionManager.isProtected(event.getBlock()))
			event.setCancelled(true);
	}

	@EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
	public void onBlockPlace(BlockPlaceEvent event)
	{
		if (ProtectionManager.isProtected(event.getBlock()))
			event.setCancelled(true);
	}

	@EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
	public void onBlockIgnite(BlockIgniteEvent event)
	{
		if (ProtectionManager.isProtected(event.getBlock()))
			event.setCancelled(true);
	}

	@EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
	public void onPlayerBucketEmpty(PlayerBucketEmptyEvent event)
	{
		if (ProtectionManager.isProtected(event.getBlockClicked()))
			event.setCancelled(true);
	}

	@EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
	public void onEntityExplode(EntityExplodeEvent event)
	{
		Iterator<Block> iterator = event.blockList().iterator();

		while (iterator.hasNext())
		{
			Block block = iterator.next();

			if (ProtectionManager.isProtected(block))
				iterator.remove();
		}
	}

	@EventHandler
	public void onEntityDamage(EntityDamageEvent event)
	{
		if (ProtectionManager.isProtected(event.getEntity().getLocation().getBlock()))
			event.setCancelled(true);
	}

	@EventHandler
	public void onBlockPhysics(BlockPhysicsEvent event)
	{
		event.setCancelled(true);
	}
	/*
	 * @EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
	 * public void onBlockDamage(BlockDamageEvent event) { if
	 * (isProtected(event.getBlock())) event.setCancelled(true); }
	 * 
	 * 
	 * 
	 * @EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
	 * public void onBlockBurn(BlockBurnEvent event) { if
	 * (isProtected(event.getBlock())) event.setCancelled(true); }
	 */

}