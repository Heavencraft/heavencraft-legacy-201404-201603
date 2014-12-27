package fr.heavencraft.heavenguard.bukkit.listeners;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

import fr.heavencraft.exceptions.HeavenException;
import fr.heavencraft.heavenguard.bukkit.HeavenGuard;
import fr.heavencraft.utils.DevUtil;

public class BlockListener implements Listener
{
	public BlockListener()
	{
		DevUtil.registerListener(this);
	}

	@EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
	private void onBlockPlace(BlockPlaceEvent event)
	{
		if (!canBuildAt(event.getPlayer(), event.getBlock()))
			event.setCancelled(true);
	}

	@EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
	private void onBlockBreak(BlockBreakEvent event)
	{
		if (!canBuildAt(event.getPlayer(), event.getBlock()))
			event.setCancelled(true);
	}

	private static boolean canBuildAt(Player player, Block block)
	{
		try
		{
			return HeavenGuard.getRegionManager().canBuildAt(player.getUniqueId(), //
					block.getWorld().getName(), block.getX(), block.getY(), block.getZ());
		}
		catch (HeavenException e)
		{
			return false;
		}
	}
}