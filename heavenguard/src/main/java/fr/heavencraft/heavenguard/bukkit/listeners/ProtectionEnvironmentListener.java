package fr.heavencraft.heavenguard.bukkit.listeners;

import java.util.Iterator;

import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBurnEvent;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.EntityExplodeEvent;

import fr.heavencraft.common.logs.HeavenLog;
import fr.heavencraft.heavenguard.bukkit.HeavenGuard;
import fr.heavencraft.utils.DevUtil;

public class ProtectionEnvironmentListener implements Listener
{
	private static final HeavenLog log = HeavenLog.getLogger(ProtectionEnvironmentListener.class);

	public ProtectionEnvironmentListener()
	{
		DevUtil.registerListener(this);
	}

	/*
	 * BlockEvent
	 */

	@EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
	private void onBlockBurn(BlockBurnEvent event)
	{
		log.info(event.getClass().getSimpleName());

		if (isProtected(event.getBlock()))
			event.setCancelled(true);
	}

	/*
	 * EntityEvent
	 */

	@EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
	private void onEntityChangeBlock(EntityChangeBlockEvent event)
	{
		log.info(event.getClass().getSimpleName());

		// BUGFIX : Pour faire tomber le sable, le gravier, etc...
		if (event.getEntityType() == EntityType.FALLING_BLOCK)
			return;

		// BUGFIX : Pour que les moutons puissent se nourrir
		if (event.getEntityType() == EntityType.SHEEP)
			return;

		if (isProtected(event.getBlock()))
			event.setCancelled(true);
	}

	// @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
	// private void onEntityInteract(EntityInteractEvent event)
	// {
	// log.info(event.getClass().getSimpleName());
	//
	// if (event.getEntityType() == EntityType.PLAYER)
	// return;
	//
	// if (isProtected(event.getBlock()))
	// event.setCancelled(true);
	// }

	@EventHandler(priority = EventPriority.LOWEST)
	public void onEntityExplode(EntityExplodeEvent event)
	{
		log.info(event.getClass().getSimpleName());

		for (Iterator<Block> it = event.blockList().iterator(); it.hasNext();)
		{
			if (isProtected(it.next()))
				it.remove();
		}
	}

	private static boolean isProtected(Block block)
	{
		return HeavenGuard.getRegionManager().isProtectedAgainstEnvironment(block.getWorld().getName(), block.getX(),
				block.getY(), block.getZ());
	}
}