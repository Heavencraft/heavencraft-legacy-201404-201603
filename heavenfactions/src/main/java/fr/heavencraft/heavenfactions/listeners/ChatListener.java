package fr.heavencraft.heavenfactions.listeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import fr.heavencraft.heavenfactions.HeavenFactions;

public class ChatListener implements Listener
{
	private final static String WELCOME_FORMAT = ChatColor.GREEN + "Bienvenue sur le serveur Factions !";

	public ChatListener()
	{
		Bukkit.getPluginManager().registerEvents(this, HeavenFactions.getInstance());
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event)
	{
		event.setJoinMessage("");

		event.getPlayer().sendMessage(WELCOME_FORMAT);
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
}