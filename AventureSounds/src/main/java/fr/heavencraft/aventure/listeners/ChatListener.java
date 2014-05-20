package fr.heavencraft.aventure.listeners;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import fr.heavencraft.aventure.HeavenAventure;
import fr.heavencraft.webserver.SoundEffectsManager;

public class ChatListener implements Listener
{
	private final static String WELCOME_FORMAT = ChatColor.GREEN + "Bienvenue sur le serveur Nexus!";

	public ChatListener()
	{
		Bukkit.getPluginManager().registerEvents(this, HeavenAventure.getInstance());
	}

	@EventHandler(priority = EventPriority.LOW)
	public void onPlayerJoin(PlayerJoinEvent event)
	{
		Bukkit.broadcastMessage("JOIN EVENT");
		
		event.setJoinMessage("");
		event.getPlayer().sendMessage(WELCOME_FORMAT);
		Player p = event.getPlayer();
		
		p.sendMessage(ChatColor.WHITE + "" + ChatColor.BOLD + "▮■▮■▮■▮■▮■▮■▮■▮■▮■▮■▮■▮■▮■▮■▮■▮■▮■▮■▮■▮■▮■▮■▮■▮■▮■▮■▮");
		p.sendMessage(ChatColor.AQUA + "");
		p.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "Vous voulez profiter d'une expérience encore plus immersive?");
		p.sendMessage(ChatColor.AQUA + "");
		p.sendMessage(ChatColor.AQUA + "" + ChatColor.BOLD + "Cliquez se lien:");
		p.sendMessage(ChatColor.WHITE + "" + ChatColor.UNDERLINE + "http://" + "heavencraft.fr" + ":8080/index.html?name=" + p.getName() + "&sessionId=" + new Random().nextInt(10000));
		p.sendMessage(ChatColor.AQUA + "");
		p.sendMessage(ChatColor.DARK_RED + "" + ChatColor.BOLD + "Gardez la page ouverte, et le son de vos haut-parleurs audible!");
		p.sendMessage(ChatColor.AQUA + "");
		p.sendMessage(ChatColor.WHITE + "" + ChatColor.BOLD + "▮■▮■▮■▮■▮■▮■▮■▮■▮■▮■▮■▮■▮■▮■▮■▮■▮■▮■▮■▮■▮■▮■▮■▮■▮■▮■▮");
	}

	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event)
	{
//		event.setQuitMessage("");
	}

	@EventHandler(ignoreCancelled = true)
	public void onPlayerKick(PlayerKickEvent event)
	{
//		event.setLeaveMessage("");
	}

	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent event)
	{
//		event.setDeathMessage("");
	}
	
	@EventHandler
	public void onPlayerChat(PlayerChatEvent event)
	{
		Player p = event.getPlayer();
		SoundEffectsManager.playToPlayer(p, "play");
	}
}