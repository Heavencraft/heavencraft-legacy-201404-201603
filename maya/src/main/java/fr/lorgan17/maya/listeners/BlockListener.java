package fr.lorgan17.maya.listeners;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;

import fr.lorgan17.maya.MayaPlugin;
import fr.lorgan17.maya.managers.ProtectionManager;

public class BlockListener implements Listener
{
	public BlockListener(MayaPlugin plugin)
	{
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}
	
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
	
	@EventHandler
	public void onBlockPhysics(BlockPhysicsEvent event)
	{
		event.setCancelled(true);
	}
/*
	@EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
	public void onBlockDamage(BlockDamageEvent event)
	{
		if (isProtected(event.getBlock()))
			event.setCancelled(true);
	}



	@EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
	public void onBlockBurn(BlockBurnEvent event)
	{
		if (isProtected(event.getBlock()))
			event.setCancelled(true);
	}
	*/

}