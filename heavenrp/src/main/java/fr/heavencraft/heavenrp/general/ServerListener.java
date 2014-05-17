package fr.heavencraft.heavenrp.general;

import java.util.regex.Pattern;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

import fr.heavencraft.heavenrp.HeavenRP;
import fr.heavencraft.heavenrp.worlds.WorldsManager;

public class ServerListener implements Listener
{
	public ServerListener()
	{
		Bukkit.getPluginManager().registerEvents(this, HeavenRP.getInstance());
	}

	@EventHandler(ignoreCancelled = true)
	public void onBlockPhysics(BlockPhysicsEvent event)
	{
		if (event.getChangedType() == Material.PORTAL)
			event.setCancelled(true);
	}

	@EventHandler(ignoreCancelled = true)
	public void onPlayerRespawn(PlayerRespawnEvent event)
	{
		event.setRespawnLocation(WorldsManager.getSpawn());
	}

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	private void onSignChange(SignChangeEvent event)
	{
		if (event.getPlayer().hasPermission("heavenrp.moderator.colorsign"))
		{
			Pattern pattern = Pattern.compile("\\&([0-9A-Fa-f])");

			for (int i = 0; i != 4; i++)
				event.setLine(i, pattern.matcher(event.getLine(i)).replaceAll("§$1"));
		}
	}
}
