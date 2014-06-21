package fr.heavencraft.heavennexus.listeners;

import org.bukkit.event.Listener;

import fr.heavencraft.utils.DevUtil;

public class NameTagListener implements Listener
{
	public NameTagListener()
	{
		DevUtil.registerListener(this);
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