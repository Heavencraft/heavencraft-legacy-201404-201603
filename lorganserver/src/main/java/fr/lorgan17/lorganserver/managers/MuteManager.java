package fr.lorgan17.lorganserver.managers;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import fr.lorgan17.lorganserver.LorganServer;

public class MuteManager implements Listener {

	List<String> _players = new ArrayList<String>();
	
	public MuteManager(LorganServer plugin)
	{
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}
	
	public boolean isMuted(String name)
	{
		return _players.contains(name.toLowerCase());
	}
	
	public void mute(String name)
	{
		name = name.toLowerCase();
		
		if (!_players.contains(name))
			_players.add(name);
	}
	
	public void unmute(String name)
	{
		_players.remove(name.toLowerCase());
	}
	
	@EventHandler(ignoreCancelled = true)
	private void onPlayerChat(AsyncPlayerChatEvent event)
	{
		Player player = event.getPlayer();
		
		if (isMuted(player.getName()))
		{
			LorganServer.sendMessage(player, "Vous avez été rendu {muet} par un modérateur.");
			event.setCancelled(true);
		}
	}
}
