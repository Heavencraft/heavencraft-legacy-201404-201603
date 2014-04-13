package fr.lorgan17.heavenrp.listeners;

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

import fr.heavencraft.heavenrp.HeavenRP;


public class JumpListener implements Listener {
	
	public JumpListener(HeavenRP plugin) {
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler(ignoreCancelled = true)
	public void onPlayerToggleSprint(PlayerToggleSprintEvent event) {
		if (!event.isSprinting())
			return;
		
		Player player = event.getPlayer();
		
		if (!player.getLocation().getBlock().getRelative(BlockFace.DOWN).getType().equals(Material.SPONGE))
			return;
		
		player.setVelocity(player.getVelocity().setY(5));
	}

	@EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
	public void onEntityDamage(EntityDamageEvent event)
	{
		if (!event.getEntityType().equals(EntityType.PLAYER) || !event.getCause().equals(DamageCause.FALL))
			return;
		
		// Dépends du lag du joueur :(
		if (!event.getEntity().getLocation().getBlock().getRelative(0, -1, 0).getType().equals(Material.SPONGE)
				&& !event.getEntity().getLocation().getBlock().getRelative(0, -2, 0).getType().equals(Material.SPONGE)
				&& !event.getEntity().getLocation().getBlock().getRelative(0, -3, 0).getType().equals(Material.SPONGE)
				&& !event.getEntity().getLocation().getBlock().getRelative(0, -4, 0).getType().equals(Material.SPONGE)
				&& !event.getEntity().getLocation().getBlock().getRelative(0, -5, 0).getType().equals(Material.SPONGE))
			return;
		
		event.setCancelled(true);
	}
}