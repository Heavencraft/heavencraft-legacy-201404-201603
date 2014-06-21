package fr.heavencraft.listeners;

import static fr.heavencraft.utils.DevUtil.registerListener;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockRedstoneEvent;
import org.bukkit.event.player.PlayerInteractEvent;

/**
 * This listener allow players to light a redstone lamp with a lighter
 * 
 * @author lorgan17
 */
public class RedstoneLampListener implements Listener
{
	public RedstoneLampListener()
	{
		registerListener(this);
	}

	@EventHandler(ignoreCancelled = true)
	public void onPlayerInteract(PlayerInteractEvent event)
	{
		if (event.getAction() != Action.RIGHT_CLICK_BLOCK)
			return;

		Block block = event.getClickedBlock();

		if (block.getType() != Material.REDSTONE_LAMP_OFF)
			return;

		if (!event.hasItem() || event.getItem().getType() != Material.FLINT_AND_STEEL)
			return;

		block.setType(Material.REDSTONE_LAMP_ON);
		event.setCancelled(true);
	}

	@EventHandler
	public void onBlockRedstone(BlockRedstoneEvent event)
	{
		Block block = event.getBlock();

		if (block.getType() != Material.REDSTONE_LAMP_ON)
			return;

		if (event.getNewCurrent() != 0)
			return;

		for (BlockFace f : BlockFace.values())
		{
			Block block2 = block.getRelative(f);

			if (block2 != null && isRedstoneMaterial(block2.getType()))
				return;
		}

		event.setNewCurrent(15);
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