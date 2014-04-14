package fr.heavencraft.NavalConflicts.listeners;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleSneakEvent;

import fr.heavencraft.NavalConflicts.NavalConflicts;
import fr.heavencraft.NavalConflicts.Handlers.Lobby;
import fr.heavencraft.NavalConflicts.Handlers.Player.NCPlayer;
import fr.heavencraft.NavalConflicts.Handlers.Player.NCPlayerManager;

public class ScoreBoardToggleListener implements Listener {
	public ScoreBoardToggleListener()
	{
		Bukkit.getPluginManager().registerEvents(this, NavalConflicts.getInstance());
	}
	
	// Show stats when a player sneaks
		@EventHandler(priority = EventPriority.NORMAL)
		public void onPlayerToggleSneak(PlayerToggleSneakEvent e) {
			if (Lobby.isInGame(e.getPlayer()))
			{
				NCPlayer ip = NCPlayerManager.getNCPlayer(e.getPlayer());
				ip.getScoreBoard().switchShowing();
				ip.getScoreBoard().showProperBoard();
			}
		}
		
}
