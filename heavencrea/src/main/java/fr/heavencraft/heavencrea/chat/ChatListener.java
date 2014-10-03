package fr.heavencraft.heavencrea.chat;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import fr.heavencraft.utils.DevUtil;

public class ChatListener implements Listener
{
	public ChatListener()
	{
		DevUtil.registerListener(this);
	}

	@EventHandler(ignoreCancelled = true)
	private void onPlayerJoint(PlayerJoinEvent event)
	{
		event.setJoinMessage("");
	}

	@EventHandler(ignoreCancelled = true)
	private void onPlayerDeath(PlayerDeathEvent event)
	{
		event.setDeathMessage("");
	}

	@EventHandler(ignoreCancelled = true)
	private void onPlayerQuit(PlayerQuitEvent event)
	{
		event.setQuitMessage("");
	}

	@EventHandler(ignoreCancelled = true)
	private void onPlayerKick(PlayerKickEvent event)
	{
		event.setLeaveMessage("");
	}
}