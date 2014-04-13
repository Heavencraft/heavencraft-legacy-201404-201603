package fr.heavencraft.heavenrp.antilag;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.event.world.ChunkLoadEvent;

import fr.heavencraft.Utils;
import fr.heavencraft.heavenrp.worlds.WorldsManager;

public class AntiCheatListener implements Listener
{
	public AntiCheatListener()
	{
		Utils.registerListener(this);
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

	// BUGFIX : Etrune a mis de l'émeraude et du diamant dans le sous-sol de la map SRP.
	@EventHandler
	private void onChunkLoad(ChunkLoadEvent event)
	{
		if (!event.getWorld().equals(WorldsManager.getWorld()))
			return;

		for (int x = 0; x != 16; x++)
			for (int z = 0; z != 16; z++)
				for (int y = 0; y != 32; y++)
				{
					Block block = event.getChunk().getBlock(x, y, z);

					if (block.getType() == Material.EMERALD_ORE || block.getType() == Material.DIAMOND_ORE)
						block.setType(Material.STONE);
				}
	}
}