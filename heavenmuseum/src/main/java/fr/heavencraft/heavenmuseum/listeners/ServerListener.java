package fr.heavencraft.heavenmuseum.listeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.weather.ThunderChangeEvent;
import org.bukkit.event.weather.WeatherChangeEvent;

import fr.heavencraft.heavenmuseum.HeavenMuseum;

public class ServerListener implements Listener
{
	private final static String WELCOME_FORMAT = ChatColor.GREEN + "Bienvenue sur le serveur Musée !";

	public ServerListener(HeavenMuseum plugin)
	{
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event)
	{
		Player player = event.getPlayer();

		event.setJoinMessage("");
		player.teleport(HeavenMuseum.getSpawn());
		player.setHealth(player.getMaxHealth());
		player.setFoodLevel(20);

		player.sendMessage(WELCOME_FORMAT);
	}

	/*
	 * Chat
	 */

	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event)
	{
		event.setQuitMessage("");
	}

	@EventHandler(ignoreCancelled = true)
	public void onPlayerKick(PlayerKickEvent event)
	{
		event.setLeaveMessage("");
	}

	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent event)
	{
		event.setDeathMessage("");
	}

	/*
	 * Respawn
	 */

	@EventHandler
	public void onPlayerRespawn(PlayerRespawnEvent event)
	{
		event.setRespawnLocation(HeavenMuseum.getSpawn());
	}

	/*
	 * No more hungerr
	 */

	public void onFoodLevelChange(FoodLevelChangeEvent event)
	{
		event.setCancelled(true);
	}

	/*
	 * Feu
	 */

	@EventHandler(ignoreCancelled = true)
	public void onBlockIgnite(BlockIgniteEvent event)
	{
		event.setCancelled(true);
	}

	/*
	 * Météo
	 */

	@EventHandler(ignoreCancelled = true)
	public void onWeatherChange(WeatherChangeEvent event)
	{
		if (event.toWeatherState())
			event.setCancelled(true);
	}

	@EventHandler(ignoreCancelled = true)
	public void onThunderChange(ThunderChangeEvent event)
	{
		if (event.toThunderState())
			event.setCancelled(true);
	}
}