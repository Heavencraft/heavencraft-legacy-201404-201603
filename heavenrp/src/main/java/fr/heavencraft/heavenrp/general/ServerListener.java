package fr.heavencraft.heavenrp.general;

import java.util.regex.Pattern;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

import fr.heavencraft.exceptions.HeavenException;
import fr.heavencraft.exceptions.UserNotFoundException;
import fr.heavencraft.heavenrp.HeavenRP;
import fr.heavencraft.heavenrp.general.users.UsersManager;
import fr.heavencraft.heavenrp.worlds.WorldsManager;

public class ServerListener implements Listener
{
	public ServerListener()
	{
		Bukkit.getPluginManager().registerEvents(this, HeavenRP.getInstance());
	}
	
	@EventHandler
	private void onPlayerLogin(PlayerLoginEvent event)
	{
		String playerName = event.getPlayer().getName();

		try
		{
			UsersManager.getByName(playerName);
		}
		catch (UserNotFoundException ex)
		{
			UsersManager.createUser(playerName);
		}
		catch (HeavenException ex)
		{
			ex.printStackTrace();
		}
	}

	@EventHandler(priority = EventPriority.MONITOR)
	private void onPlayerQuit(PlayerQuitEvent event)
	{
		UsersManager.removeFromCache(event.getPlayer().getName());
	}

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	private void onPlayerKick(PlayerKickEvent event)
	{
		UsersManager.removeFromCache(event.getPlayer().getName());
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
