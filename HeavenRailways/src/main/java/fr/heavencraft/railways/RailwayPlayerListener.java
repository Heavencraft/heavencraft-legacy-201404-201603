package fr.heavencraft.railways;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class RailwayPlayerListener implements Listener{
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		// Remove this player form station list.
		PlayerStationManager.removeUser(event.getPlayer().getUniqueId());
	}
}
