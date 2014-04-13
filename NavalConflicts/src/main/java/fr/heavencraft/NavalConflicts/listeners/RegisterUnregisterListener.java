package fr.heavencraft.NavalConflicts.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import fr.heavencraft.NavalConflicts.NavalConflicts;
import fr.heavencraft.NavalConflicts.Handlers.Player.NCPlayer;
import fr.heavencraft.NavalConflicts.Handlers.Player.NCPlayerManager;

public class RegisterUnregisterListener implements Listener  {
	public RegisterUnregisterListener()
	{
		Bukkit.getPluginManager().registerEvents(this, NavalConflicts.getInstance());
	}
	
	// When a player joins the server, create a NCPlayer for them
		@EventHandler(priority = EventPriority.NORMAL)
		public void onJoinCreateNCPlayer(PlayerJoinEvent e) {
			Player p = e.getPlayer();
			NCPlayer IP = new NCPlayer(p);
			NCPlayerManager.createNCPlayer(IP);
		}

		// When a player leaves the server willingly, delete the NCPlayer of them
		@EventHandler(priority = EventPriority.NORMAL)
		public void onLeaveDeleteNCPlayer(final PlayerQuitEvent e) {
			Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(NavalConflicts.getInstance(), new Runnable()
			{

				@Override
				public void run() {

					NCPlayer IP = NCPlayerManager.getNCPlayer(e.getPlayer());
					NCPlayerManager.removeNCPlayer(IP);
				}
			}, 2L);
		}

		// When a player leaves the server by kick, delete the NCPlayer of them
		@EventHandler(priority = EventPriority.NORMAL)
		public void onKickedDeleteNCPlayer(final PlayerKickEvent e) {
			Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(NavalConflicts.getInstance(), new Runnable()
			{

				@Override
				public void run() {

					NCPlayer IP = NCPlayerManager.getNCPlayer(e.getPlayer());
					NCPlayerManager.removeNCPlayer(IP);
				}
			}, 2L);
		}
}
