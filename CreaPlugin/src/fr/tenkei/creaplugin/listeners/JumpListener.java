package fr.tenkei.creaplugin.listeners;

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

import fr.tenkei.creaplugin.MyPlugin;

public class JumpListener implements Listener {

	public JumpListener(MyPlugin plugin){	
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}
	
	
	@EventHandler(ignoreCancelled = true)
	public void onPlayerToggleSprint(PlayerToggleSprintEvent e)
	{
		if (!e.isSprinting())
			return;
		
		Player p = e.getPlayer();
		
		if (!p.getLocation().getBlock().getRelative(BlockFace.DOWN).getType().equals(Material.SPONGE))
			return;
		
		p.setVelocity(p.getVelocity().setY(5));
	}

	@EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
	public void onEntityDamage(EntityDamageEvent e)
	{
		if (!e.getEntityType().equals(EntityType.PLAYER) || !e.getCause().equals(DamageCause.FALL))
			return;
		
		// Dépends du lag du joueur :(
		if (!e.getEntity().getLocation().getBlock().getRelative(0, -1, 0).getType().equals(Material.SPONGE)
				&& !e.getEntity().getLocation().getBlock().getRelative(0, -2, 0).getType().equals(Material.SPONGE)
				&& !e.getEntity().getLocation().getBlock().getRelative(0, -3, 0).getType().equals(Material.SPONGE)
				&& !e.getEntity().getLocation().getBlock().getRelative(0, -4, 0).getType().equals(Material.SPONGE)
				&& !e.getEntity().getLocation().getBlock().getRelative(0, -5, 0).getType().equals(Material.SPONGE))
			return;

		e.setCancelled(true);
	}
}
