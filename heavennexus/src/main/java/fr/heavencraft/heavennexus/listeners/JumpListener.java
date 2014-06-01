package fr.heavencraft.heavennexus.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerToggleSprintEvent;

import fr.heavencraft.heavennexus.HeavenNexus;

public class JumpListener implements Listener {
	
	public JumpListener(HeavenNexus plugin) {
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler(ignoreCancelled = true)
	public void onPlayerToggleSprint(PlayerToggleSprintEvent event) {
		if (!event.isSprinting())
			return;
		
		Player player = event.getPlayer();
		
		if (!player.getLocation().getBlock().getRelative(BlockFace.DOWN).getType().equals(Material.SPONGE))
			return;
		
		player.setVelocity(player.getVelocity().setY(10));
	}

	@EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
	public void onEntityDamage(EntityDamageEvent event)
	{
		if (!event.getEntityType().equals(EntityType.PLAYER) || !event.getCause().equals(DamageCause.FALL))
			return;
		
		event.setCancelled(true);
	}
}