package fr.lorgan17.lorganserver.listeners.protection;

import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.hanging.HangingBreakByEntityEvent;
import org.bukkit.event.hanging.HangingPlaceEvent;

import fr.lorgan17.lorganserver.LorganServer;

public class ProtectionHangingListener implements Listener {

	public ProtectionHangingListener(LorganServer plugin)
	{
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void onHangingPlace(HangingPlaceEvent event)
	{
		Player player = event.getPlayer();
		Block block = event.getBlock();

		/*if (event.getEntity().getType() == EntityType.ITEM_FRAME)
		{
			LorganServer.sendMessage(player, "Les {item frame} c'est le mal !");
			event.setCancelled(true);
		}
		
		else*/ if (!LorganServer.canBeDestroyed(player, block))
		{
			LorganServer.sendMessage(player, "Cet endroit est protégé.");
			event.setCancelled(true);
		}
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void onHangingBreakByEntity(HangingBreakByEntityEvent event)
	{
		Player player = event.getRemover() instanceof Player ? (Player) event.getRemover() : null;
		Block block = event.getEntity().getLocation().getBlock();

		if (!LorganServer.canBeDestroyed(player, block))
		{
			LorganServer.sendMessage(player, "Cet endroit est protégé.");
			event.setCancelled(true);
		}
	}
}
