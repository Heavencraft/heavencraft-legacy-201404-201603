package fr.heavencraft.heavenNoel;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByBlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import fr.heavencraft.HeavenNoel;

public class PlayerListener implements Listener {
	public PlayerListener()
	{
		Bukkit.getPluginManager().registerEvents(this, HeavenNoel.getInstance());
	}

	// When a player joins the server, create a RPGPlayer for them
	@EventHandler(priority = EventPriority.NORMAL)
	public void onJoinCreateHallowPlayer(PlayerJoinEvent e) {
		e.setJoinMessage("");
		e.getPlayer().setGameMode(GameMode.SURVIVAL);
		e.getPlayer().setFlying(false);
		e.getPlayer().teleport(RacerManager.getLobby());
		e.getPlayer().setGameMode(GameMode.SURVIVAL);
		e.getPlayer().getInventory().clear();
		e.getPlayer().setHealth(20);
		e.getPlayer().setSaturation(20);
	}

	// When a player leaves the server willingly, delete the RPGPlayer of them
	public void onLeaveDeleteHallowPlayer(final PlayerQuitEvent e) {
		e.setQuitMessage("");
		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(HeavenNoel.getInstance(), new Runnable()
		{
			@Override
			public void run() {

				Racer IP = RacerManager.getRacer(e.getPlayer());
				RacerManager.removeRacer(IP);
			}
		}, 2L);
	}

	// When a player leaves the server by kick, delete the RPGPlayer of them
	@EventHandler(priority = EventPriority.NORMAL)
	public void onKickedDeleteHallowPlayer(final PlayerKickEvent e) {
		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(HeavenNoel.getInstance(), new Runnable()
		{

			@Override
			public void run() {

				Racer IP = RacerManager.getRacer(e.getPlayer());
				RacerManager.removeRacer(IP);
			}
		}, 2L);
	}

	
	
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerDamage(EntityDamageEvent e) {
			e.setDamage(0);
			e.setCancelled(true);
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerDamageByEntity(EntityDamageByEntityEvent e) {
		e.setCancelled(true);
		return;
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerDamageByEntity(EntityDamageByBlockEvent e) {
		e.setCancelled(true);
		return;
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerDeath(PlayerDeathEvent e) {
		e.setDroppedExp(0);
		e.setDeathMessage("");
	}


	@EventHandler public void onFoodLevelChange(FoodLevelChangeEvent event){
		if (event.isCancelled()) 
			return;
		if (!(event.getEntity() instanceof Player)) 
			return;	  
		event.setCancelled(true);
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerDropItem(PlayerDropItemEvent e) {
		e.setCancelled(true);
	}
}
