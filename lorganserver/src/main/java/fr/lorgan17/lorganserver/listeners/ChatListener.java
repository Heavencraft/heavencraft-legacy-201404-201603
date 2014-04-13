package fr.lorgan17.lorganserver.listeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import fr.lorgan17.lorganserver.LorganServer;
import fr.lorgan17.lorganserver.entities.User;
import fr.lorgan17.lorganserver.exceptions.LorganException;
import fr.lorgan17.lorganserver.managers.WorldsManager;

public class ChatListener implements Listener {
	
	private final static String LEAVE_MESSAGE = ChatColor.GOLD + " * %1$s a été exclu du serveur : %2$s.";
	
	public ChatListener(LorganServer plugin)
	{
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler(ignoreCancelled = true)
	private void onPlayerJoin(PlayerJoinEvent event)
	{
		Player player = event.getPlayer();
		
		if (!player.hasPlayedBefore())
			player.teleport(WorldsManager.getSpawn());
		
        player.sendMessage(ChatColor.GREEN + " * Bienvenue sur Heavencraft Origines !");
        
        event.setJoinMessage("");
	}
	
	@EventHandler(ignoreCancelled = true)
	private void onPlayerQuit(PlayerQuitEvent event)
	{
		event.setQuitMessage("");
	}

	@EventHandler(ignoreCancelled = true)
	private void onPlayerKick(PlayerKickEvent event)
	{
		event.setLeaveMessage(String.format(LEAVE_MESSAGE, event.getPlayer().getName(), event.getReason()));
	}
	
	@EventHandler(ignoreCancelled = true)
	private void onPlayerChat(AsyncPlayerChatEvent event) throws LorganException
	{
		User user = User.getUserByName(event.getPlayer().getName());
		event.setFormat(user.getColor() + "[%1$s]§r %2$s");
	}
}
