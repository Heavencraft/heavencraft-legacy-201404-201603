package fr.heavencraft.heavennexus.listeners;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

import fr.heavencraft.heavennexus.HeavenNexus;
import fr.heavencraft.utils.DevUtil;

public class ServerListener implements Listener
{
	public ServerListener()
	{
		DevUtil.registerListener(this);
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event)
	{
		event.getPlayer().teleport(HeavenNexus.getSpawn());
	}

	@EventHandler
	public void onPlayerRespawn(PlayerRespawnEvent event)
	{
		event.setRespawnLocation(HeavenNexus.getSpawn());
	}

	@EventHandler(ignoreCancelled = true)
	public void onBlockPhysics(BlockPhysicsEvent event)
	{
		if (event.getChangedType() == Material.PORTAL)
			event.setCancelled(true);
	}
}