package fr.lorgan17.lorganserver.listeners.protection;

import static fr.heavencraft.utils.DevUtil.registerListener;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.vehicle.VehicleDestroyEvent;

import fr.heavencraft.utils.ChatUtil;
import fr.lorgan17.lorganserver.LorganServer;

public class ProtectionVehicleListener implements Listener
{
	public ProtectionVehicleListener()
	{
		registerListener(this);
	}

	@EventHandler(priority = EventPriority.LOWEST)
	private void onVehicleDestroy(VehicleDestroyEvent event)
	{
		Player player = event.getAttacker() instanceof Player ? (Player) event.getAttacker() : null;
		Block block = event.getVehicle().getLocation().getBlock();

		if (!LorganServer.canBeDestroyed(player, block))
		{
			ChatUtil.sendMessage(player, "Cet endroit est protégé.");
			event.setCancelled(true);
		}
	}
}
