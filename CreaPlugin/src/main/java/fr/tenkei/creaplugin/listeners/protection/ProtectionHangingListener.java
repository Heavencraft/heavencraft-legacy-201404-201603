package fr.tenkei.creaplugin.listeners.protection;

import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.hanging.HangingBreakByEntityEvent;
import org.bukkit.event.hanging.HangingPlaceEvent;

import fr.tenkei.creaplugin.MyPlugin;
import fr.tenkei.creaplugin.utils.Message;
import fr.tenkei.creaplugin.utils.Stuff;



public class ProtectionHangingListener implements Listener {

	public ProtectionHangingListener(MyPlugin plugin)
	{
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void onHangingPlace(HangingPlaceEvent event)
	{
		Player player = event.getPlayer();
		Block block = event.getBlock();

		if (!Stuff.canBeDestroyed(player, block))
		{
			Message.sendMessage(player, "Cet endroit est protégé.");
			event.setCancelled(true);
		}
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void onHangingBreakByEntity(HangingBreakByEntityEvent event)
	{
		Player player = event.getRemover() instanceof Player ? (Player) event.getRemover() : null;
		Block block = event.getEntity().getLocation().getBlock();

		if (!Stuff.canBeDestroyed(player, block))
		{
			Message.sendMessage(player, "Cet endroit est protégé.");
			event.setCancelled(true);
		}
	}
}
