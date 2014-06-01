package fr.heavencraft.heavennexus.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockRedstoneEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import fr.heavencraft.heavennexus.HeavenNexus;

public class RedstoneLampListener implements Listener {
	
	public RedstoneLampListener(HeavenNexus plugin)
	{
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler(ignoreCancelled = true)
	public void onPlayerInteract(PlayerInteractEvent event)
	{
		if (event.getAction() != Action.RIGHT_CLICK_BLOCK)
			return;
		
		Block b = event.getClickedBlock();
		
		if (b.getType() != Material.REDSTONE_LAMP_OFF)
			return;
		
		if (!event.hasItem() || event.getItem().getType() != Material.FLINT_AND_STEEL)
			return;
		
		b.setType(Material.REDSTONE_LAMP_ON);
		event.setCancelled(true);
	}
	
	@EventHandler
	public void onBlockRedstone(BlockRedstoneEvent e)
	{
		Block b = e.getBlock();
		
		if (b.getType() != Material.REDSTONE_LAMP_ON)
			return;
			
		if (e.getNewCurrent() != 0)
			return;
		
		Block b2;
		
		for (BlockFace f : BlockFace.values())
		{
			b2 = b.getRelative(f);
			
			if (b2 != null && isRedstoneMaterial(b2.getType()))
				return;
		}
		
		e.setNewCurrent(15);
	}
	
	private static boolean isRedstoneMaterial(Material m)
	{
		switch (m)
		{
			case DETECTOR_RAIL:
			case REDSTONE_WIRE:
			case REDSTONE_TORCH_OFF:
			case REDSTONE_TORCH_ON:
			case DIODE_BLOCK_OFF:
			case DIODE_BLOCK_ON:
			case LEVER:
			case STONE_BUTTON:
			case WOOD_BUTTON:
			case ACTIVATOR_RAIL:
			case DAYLIGHT_DETECTOR:
			case REDSTONE_BLOCK:
			case REDSTONE_COMPARATOR_OFF:
			case REDSTONE_COMPARATOR_ON:
				return true;
			default:
				return false;
		}	
	}
}