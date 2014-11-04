package fr.heavencraft.heavenhallow.player;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Entity;
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
import org.bukkit.event.player.PlayerRespawnEvent;

import fr.heavencraft.HeavenHallow;

public class HallowlayerListener implements Listener {
	public HallowlayerListener()
	{
		Bukkit.getPluginManager().registerEvents(this, HeavenHallow.getInstance());
	}

	// When a player joins the server, create a RPGPlayer for them
	@EventHandler(priority = EventPriority.NORMAL)
	public void onJoinCreateHallowPlayer(PlayerJoinEvent e) {
		e.setJoinMessage("");
		e.getPlayer().setGameMode(GameMode.SURVIVAL);
		e.getPlayer().setFlying(false);
		Player p = e.getPlayer();
		HallowPlayer IP = new HallowPlayer(p);
		HallowPlayerManager.createHallowPlayer(IP);
	}

	// When a player leaves the server willingly, delete the RPGPlayer of them
	@EventHandler(priority = EventPriority.NORMAL)
	public void onLeaveDeleteHallowPlayer(final PlayerQuitEvent e) {
		e.setQuitMessage("");
		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(HeavenHallow.getInstance(), new Runnable()
		{
			@Override
			public void run() {

				HallowPlayer IP = HallowPlayerManager.getHalowPlayer(e.getPlayer());
				HallowPlayerManager.removeHallowPlayer(IP);
			}
		}, 2L);
	}

	// When a player leaves the server by kick, delete the RPGPlayer of them
	@EventHandler(priority = EventPriority.NORMAL)
	public void onKickedDeleteHallowPlayer(final PlayerKickEvent e) {
		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(HeavenHallow.getInstance(), new Runnable()
		{

			@Override
			public void run() {

				HallowPlayer IP = HallowPlayerManager.getHalowPlayer(e.getPlayer());
				HallowPlayerManager.removeHallowPlayer(IP);
			}
		}, 2L);
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerDamage(EntityDamageEvent e) {
		if (!(e.getEntity() instanceof Player))
			return;	
		Player victim = (Player) e.getEntity();		

		if (victim.getHealth() - e.getDamage() <= 0)
		{
			e.setDamage(0);
			HallowPlayerManager.handlePlayerRespawn(victim);
			e.setCancelled(true);
		}
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerDamageByEntity(EntityDamageByEntityEvent e) {
		if (!(e.getEntity() instanceof Player))
			return;	

		Player victim = (Player) e.getEntity();		

		if (victim.getHealth() - e.getDamage() <= 0)
		{
			e.setDamage(0);
			HallowPlayerManager.handlePlayerRespawn(victim);
			e.setCancelled(true);
		}
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerDamageByEntity(EntityDamageByBlockEvent e) {
		if (!(e.getEntity() instanceof Player))
			return;	

		Player victim = (Player) e.getEntity();		

		if (victim.getHealth() - e.getDamage() <= 0)
		{
			e.setDamage(0);
			HallowPlayerManager.handlePlayerRespawn(victim);
			e.setCancelled(true);
		}
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
