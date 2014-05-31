package fr.lorgan17.lorganserver.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent.Result;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

import fr.lorgan17.lorganserver.LorganServer;
import fr.lorgan17.lorganserver.entities.User;
import fr.lorgan17.lorganserver.exceptions.LorganException;
import fr.lorgan17.lorganserver.managers.OnlineLogManager;
import fr.lorgan17.lorganserver.managers.WorldsManager;

public class ServerListener implements Listener {

	public ServerListener(LorganServer plugin)
	{
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler(ignoreCancelled = true)
	private void onPlayerPreLogin(AsyncPlayerPreLoginEvent event)
	{
		try
		{
			User user = User.getUserByName(event.getName());
			
			if (user.isBanned())
			{
				event.setLoginResult(Result.KICK_BANNED);
				event.setKickMessage("Vous êtes banni du serveur.\n" + user.getBannedReason());
			}
		}
		catch (LorganException ex)
		{
			User.createUser(event.getName());
		}
	}
	
	@EventHandler
	public void onPlayerLogin(PlayerLoginEvent event)
	{
		try
		{
			User user = User.getUserByName(event.getPlayer().getName());
			
			if (user.isBanned())
			{
				event.setResult(PlayerLoginEvent.Result.KICK_BANNED);
				event.setKickMessage("Vous êtes banni du serveur.\n" + user.getBannedReason());
			}
		}
		catch (LorganException ex)
		{
			User.createUser(event.getPlayer().getName());
		}
	}
	
	/*
	 * Mise à jour de la table onlinelog
	 */
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) throws LorganException
	{
		OnlineLogManager.Update();
		
		Player player = event.getPlayer();
		String playerName = player.getName();
		
		if (playerName.length() > 14)
			return;
		
		String color = User.getUserByName(player.getName()).getColor();
		
		if (color.equalsIgnoreCase("§f"))
			return;
		
		player.setPlayerListName(color + playerName);
	}
	
	@EventHandler(ignoreCancelled = true)
	public void onPlayerKick(PlayerKickEvent event)
	{
		OnlineLogManager.Update();
	}
	
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event)
	{
		OnlineLogManager.Update();
	}

	@EventHandler(ignoreCancelled = true)
	private void onEntityDamageByEntity(EntityDamageByEntityEvent event)
	{
		if (event.getEntity().getWorld().equals(WorldsManager.getNether()))
			return;
		
		if (!(event.getEntity() instanceof Player))
			return;
		
		Entity attacker = event.getDamager();
		
		if (attacker instanceof Player
				|| (attacker instanceof Arrow && ((Arrow)attacker).getShooter() instanceof Player))
			event.setCancelled(true);
	}
	
	@EventHandler(ignoreCancelled = true)
	public void onPlayerRespawn(PlayerRespawnEvent event)
	{
		event.setRespawnLocation(WorldsManager.getSpawn());
	}
}
