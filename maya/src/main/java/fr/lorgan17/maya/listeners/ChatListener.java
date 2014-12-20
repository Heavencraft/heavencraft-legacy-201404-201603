package fr.lorgan17.maya.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import fr.lorgan17.maya.MayaListener;

/**
 * ChatListener Disable minecraft chat messages.
 * 
 * @author lorgan17
 */
public class ChatListener extends MayaListener
{
	@EventHandler
	private void onPlayerJoin(PlayerJoinEvent event)
	{
		event.setJoinMessage("");
	}

	@EventHandler
	private void onPlayerQuit(PlayerQuitEvent event)
	{
		event.setQuitMessage("");
	}

	@EventHandler(ignoreCancelled = true)
	private void onPlayerKick(PlayerKickEvent event)
	{
		event.setLeaveMessage("");
	}

	@EventHandler
	private void onPlayerDeath(PlayerDeathEvent event)
	{
		event.setDeathMessage("");
	}
}