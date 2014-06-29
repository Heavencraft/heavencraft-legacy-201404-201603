package fr.heavencraft.heavenmuseum.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

import fr.heavencraft.heavenmuseum.managers.WorldsManager;
import fr.heavencraft.utils.DevUtil;

public class PlayerListener implements Listener
{
	public PlayerListener()
	{
		DevUtil.registerListener(this);
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event)
	{
		Player player = event.getPlayer();

		if (!player.hasPlayedBefore())
			player.teleport(WorldsManager.getSpawn());

		player.setHealth(player.getMaxHealth());
		player.setFoodLevel(20);
		player.setAllowFlight(true);
	}

	@EventHandler
	public void onPlayerRespawn(PlayerRespawnEvent event)
	{
		event.setRespawnLocation(WorldsManager.getSpawn());
	}

	@EventHandler(ignoreCancelled = true)
	private void onEntityDamage(EntityDamageEvent event)
	{
		event.setCancelled(true);
	}

	@EventHandler(ignoreCancelled = true)
	private void onFoodLevelChange(FoodLevelChangeEvent event)
	{
		event.setCancelled(true);
	}
}
