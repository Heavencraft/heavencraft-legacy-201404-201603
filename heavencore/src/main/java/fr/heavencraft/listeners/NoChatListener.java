package fr.heavencraft.listeners;

import static fr.heavencraft.utils.DevUtil.registerListener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * This listener disables the chat messages.
 * 
 * @author lorgan17
 */
public class NoChatListener implements Listener
{
	public NoChatListener()
	{
		registerListener(this);
	}

	@EventHandler(priority = EventPriority.MONITOR)
	private void onPlayerJoin(PlayerJoinEvent event)
	{
		event.setJoinMessage("");
	}

	@EventHandler(priority = EventPriority.MONITOR)
	private void onPlayerQuit(PlayerQuitEvent event)
	{
		event.setQuitMessage("");
	}

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	private void onPlayerKick(PlayerKickEvent event)
	{
		event.setLeaveMessage("");
	}

	@EventHandler(priority = EventPriority.MONITOR)
	private void onPlayerDeath(PlayerDeathEvent event)
	{
		event.setDeathMessage("");
	}

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	private void onPlayerChat(AsyncPlayerChatEvent event)
	{
		event.setCancelled(true);
	}
}