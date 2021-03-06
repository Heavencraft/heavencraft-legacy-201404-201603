package fr.heavencraft.heavenfun;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

import fr.heavencraft.utils.DevUtil;

public class ServerListener implements Listener
{
	public ServerListener()
	{
		DevUtil.registerListener(this);
	}

	@EventHandler
	private void onPlayerRespawn(PlayerRespawnEvent event)
	{
		event.setRespawnLocation(HeavenFun.getSpawn());
	}

	@EventHandler
	private void onPlayerJoin(PlayerJoinEvent event)
	{
		event.getPlayer().teleport(HeavenFun.getSpawn());
	}
}