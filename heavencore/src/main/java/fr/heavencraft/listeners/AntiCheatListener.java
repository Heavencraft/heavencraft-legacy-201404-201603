package fr.heavencraft.listeners;

import static fr.heavencraft.utils.DevUtil.registerListener;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;

public class AntiCheatListener implements Listener
{
	public AntiCheatListener()
	{
		registerListener(this);
	}

	// OPTI : doit s'exécuter avant l'anti-lag.
	@EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
	private void onCreatureSpawn(CreatureSpawnEvent event)
	{
		switch (event.getEntityType())
		{
			case IRON_GOLEM:
				if (event.getSpawnReason() == SpawnReason.VILLAGE_DEFENSE)
					event.setCancelled(true);
				break;

			case VILLAGER:
				if (event.getSpawnReason() == SpawnReason.BREEDING)
					event.setCancelled(true);
				break;

			default:
				break;
		}
	}

	@EventHandler(ignoreCancelled = true)
	private void onBlockBreak(BlockBreakEvent event)
	{
		if (event.getBlock().getType() == Material.MOB_SPAWNER)
			event.setCancelled(true);
	}
}