package fr.heavencraft.heavennexus.listeners;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class NameTagListener implements Listener {

	public NameTagListener(JavaPlugin plugin)
	{
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}

	// @EventHandler
	// public void onNameTag(AsyncPlayerReceiveNameTagEvent event)
	// {
	// Player player = event.getNamedPlayer();
	//
	// if (!player.hasPermission("heavencraft.accueil") || player.isOp())
	// return;
	//
	// String playerName = player.getName();
	//
	// if (playerName.length() > 14)
	// return;
	//
	// event.setTag(ChatColor.GOLD + playerName);
	// }
}