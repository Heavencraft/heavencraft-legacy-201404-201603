package fr.tenkei.creaplugin.listeners.protection;

import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.vehicle.VehicleDestroyEvent;

import fr.tenkei.creaplugin.MyPlugin;
import fr.tenkei.creaplugin.utils.Stuff;

public class ProtectionVehicleListener implements Listener {

	public ProtectionVehicleListener(MyPlugin plugin)
	{
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler(priority = EventPriority.LOWEST)
	private void onVehicleDestroy(VehicleDestroyEvent event)
	{
		Player player = event.getAttacker() instanceof Player ? (Player) event.getAttacker() : null;
		Block block = event.getVehicle().getLocation().getBlock();

		if (!Stuff.canBeDestroyed(player, block)) {
			event.setCancelled(true);
		}	
	}
}
