package fr.lorgan17.lorganserver.listeners;

import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

import fr.heavencraft.exceptions.HeavenException;
import fr.heavencraft.utils.DevUtil;
import fr.heavencraft.utils.PlayerUtil;
import fr.lorgan17.lorganserver.entities.User;
import fr.lorgan17.lorganserver.managers.WorldsManager;

public class ServerListener implements Listener
{
	public ServerListener()
	{
		DevUtil.registerListener(this);
	}

	@EventHandler
	public void onPlayerLogin(PlayerLoginEvent event)
	{
		final Player player = event.getPlayer();
		final String name = player.getName();
		final UUID uuid = player.getUniqueId();

		try
		{
			final User user = User.getUserByUniqueId(uuid);
			user.updateName(name);
		}
		catch (final HeavenException ex)
		{
			User.createUser(uuid, name);
		}
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event)
	{
		final Player player = event.getPlayer();

		if (!player.hasPlayedBefore())
			PlayerUtil.teleportPlayer(player, WorldsManager.getSpawn());

		player.sendMessage(ChatColor.GREEN + " * Bienvenue sur Heavencraft Origines !");
	}

	@EventHandler(ignoreCancelled = true)
	private void onEntityDamageByEntity(EntityDamageByEntityEvent event)
	{
		if (event.getEntity().getWorld().equals(WorldsManager.getNether()))
			return;

		if (!(event.getEntity() instanceof Player))
			return;

		final Entity attacker = event.getDamager();

		if (attacker instanceof Player || (attacker instanceof Arrow && ((Arrow) attacker).getShooter() instanceof Player))
			event.setCancelled(true);
	}

	@EventHandler
	public void onPlayerRespawn(PlayerRespawnEvent event)
	{
		event.setRespawnLocation(WorldsManager.getSpawn());
	}
}