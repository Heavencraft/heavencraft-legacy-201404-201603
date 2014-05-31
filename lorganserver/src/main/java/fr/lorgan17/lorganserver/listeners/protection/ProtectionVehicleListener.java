package fr.lorgan17.lorganserver.listeners.protection;

import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.vehicle.VehicleDestroyEvent;

import fr.lorgan17.lorganserver.LorganServer;

public class ProtectionVehicleListener implements Listener {

	public ProtectionVehicleListener(LorganServer plugin)
	{
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler(priority = EventPriority.LOWEST)
	private void onVehicleDestroy(VehicleDestroyEvent event)
	{
		Player player = event.getAttacker() instanceof Player ? (Player) event.getAttacker() : null;
		Block block = event.getVehicle().getLocation().getBlock();

		if (!LorganServer.canBeDestroyed(player, block))
		{
			LorganServer.sendMessage(player, "Cet endroit est protégé.");
			event.setCancelled(true);
		}	
	}
}
