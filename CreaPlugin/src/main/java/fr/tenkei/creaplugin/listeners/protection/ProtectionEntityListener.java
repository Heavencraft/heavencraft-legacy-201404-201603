package fr.tenkei.creaplugin.listeners.protection;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityExplodeEvent;

import fr.tenkei.creaplugin.MyPlugin;
import fr.tenkei.creaplugin.utils.Stuff;

public class ProtectionEntityListener implements Listener
{

	public ProtectionEntityListener(MyPlugin plugin)
	{
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void onEntityExplode(EntityExplodeEvent event)
	{
		List<Block> toRemove = new ArrayList<Block>();

		for (Block block : event.blockList())
		{
			if (!Stuff.canBeDestroyed(null, block))
			{
				toRemove.add(block);
			}
		}

		for (Block block : toRemove)
			event.blockList().remove(block);
	}

	@EventHandler(ignoreCancelled = true)
	public void onCreatureSpawn(CreatureSpawnEvent event)
	{
		event.setCancelled(true);
	}
}
