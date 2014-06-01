package fr.heavencraft.heavennexus.listeners;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import fr.heavencraft.heavennexus.HeavenNexus;

public class ChatListener implements Listener {
	
	private final static String CHAT_MESSAGE = "[%1$s] %2$s";
	
	public ChatListener(HeavenNexus plugin)
	{
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event)
	{
		// Géré par le proxy
		event.setJoinMessage("");
	}

	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event)
	{
		// Géré par le proxy
		event.setQuitMessage("");
	}

	@EventHandler(ignoreCancelled = true)
	public void onPlayerChat(AsyncPlayerChatEvent event)
	{
		event.setFormat(CHAT_MESSAGE);
	}
	
	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent event)
	{
		event.setDeathMessage("");
	}
}
