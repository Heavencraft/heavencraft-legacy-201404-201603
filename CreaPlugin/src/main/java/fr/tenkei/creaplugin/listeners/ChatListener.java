package fr.tenkei.creaplugin.listeners;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import fr.tenkei.creaplugin.MyPlugin;

public class ChatListener implements Listener {
	
	public ChatListener(MyPlugin plugin) {
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler(ignoreCancelled = true)
	private void onPlayerJoint(PlayerJoinEvent event)
	{
		event.setJoinMessage("");
	}
	
	@EventHandler(ignoreCancelled = true)
	private void onPlayerDeath(PlayerDeathEvent event)
	{
		event.setDeathMessage("");
	}
	
	@EventHandler(ignoreCancelled = true)
	private void onPlayerQuit(PlayerQuitEvent event)
	{
		event.setQuitMessage("");
	}

	@EventHandler(ignoreCancelled = true)
	private void onPlayerKick(PlayerKickEvent event)
	{
		event.setLeaveMessage("");
	}

	/*@EventHandler(ignoreCancelled = true)
	private void onPlayerChat(AsyncPlayerChatEvent event) throws HeavenException
	{
		String message = event.getMessage();
		if (event.getPlayer().hasPermission(MyPlugin.archiModo))
			if (message.contains("&"))
			{
				Matcher matcher = Pattern.compile("\\&([0-9A-Ja-j])").matcher(message);
				message = matcher.replaceAll("§$1");
			}
		event.setMessage(message);
		//event.setFormat(_plugin.getUsersManager().getUser(event.getPlayer().getName()).getColor() + "[%1$s]§r %2$s");
	}*/
}
