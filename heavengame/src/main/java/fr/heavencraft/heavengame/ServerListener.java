package fr.heavencraft.heavengame;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.weather.ThunderChangeEvent;
import org.bukkit.event.weather.WeatherChangeEvent;

public class ServerListener implements Listener
{
	private final static String WELCOME_FORMAT = ChatColor.GREEN + "Bienvenue sur le serveur %1$s !";

	public ServerListener(HeavenGame plugin)
	{
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event)
	{
		Player player = event.getPlayer();

		event.setJoinMessage("");
		player.teleport(HeavenGame.getSpawn());

		player.sendMessage(String.format(WELCOME_FORMAT, HeavenGame.getServerType().getName()));

		switch (HeavenGame.getServerType())
		{
			case HUNGER_GAMES:
				player.performCommand("sg join");
				break;

			default:
				break;
		}
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
		event.setRespawnLocation(HeavenGame.getSpawn());
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