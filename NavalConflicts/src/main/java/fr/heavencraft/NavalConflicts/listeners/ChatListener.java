package fr.heavencraft.NavalConflicts.listeners;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;

import fr.heavencraft.NavalConflicts.NavalConflicts;

public class ChatListener implements Listener {
	
	public ChatListener()
	{
		Bukkit.getPluginManager().registerEvents(this, NavalConflicts.getInstance());
	}
	
//	@EventHandler
//	public void onPlayerJoin(PlayerJoinEvent event)
//	{
//		event.setJoinMessage("");
//
//		event.getPlayer().sendMessage(WELCOME_FORMAT);
//	}
//
//	@EventHandler
//	public void onPlayerQuit(PlayerQuitEvent event)
//	{
//		event.setQuitMessage("");
//	}
//
//	@EventHandler(ignoreCancelled = true)
//	public void onPlayerKick(PlayerKickEvent event)
//	{
//		event.setLeaveMessage("");
//	}


}
