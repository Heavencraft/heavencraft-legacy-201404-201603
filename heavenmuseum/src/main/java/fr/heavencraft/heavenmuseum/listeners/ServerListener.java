package fr.heavencraft.heavenmuseum.listeners;

import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.weather.ThunderChangeEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.event.world.ChunkLoadEvent;

import fr.heavencraft.utils.ChatUtil;
import fr.heavencraft.utils.DevUtil;

public class ServerListener implements Listener
{
	private final static String WELCOME_FORMAT = ChatColor.GREEN + "Bienvenue sur le serveur Musée !";

	public ServerListener()
	{
		DevUtil.registerListener(this);
	}

	/*
	 * Chat
	 */

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event)
	{
		Player player = event.getPlayer();

		event.setJoinMessage("");
		player.sendMessage(WELCOME_FORMAT);
		ChatUtil.sendMessage(player, "Commandes : /{spawn}, /{rejoindre}, /{accepter}");
	}

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
	 * Feu
	 */

	@EventHandler(ignoreCancelled = true)
	public void onBlockIgnite(BlockIgniteEvent event)
	{
		event.setCancelled(true);
	}

	/*
	 * Mobs
	 */

	@EventHandler
	private void onChunkLoad(ChunkLoadEvent event)
	{
		for (Entity entity : event.getChunk().getEntities())
			if (entity instanceof Monster)
				entity.remove();
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