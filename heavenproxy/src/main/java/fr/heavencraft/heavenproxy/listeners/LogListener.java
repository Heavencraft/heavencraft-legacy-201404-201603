package fr.heavencraft.heavenproxy.listeners;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

import fr.heavencraft.heavenproxy.HeavenProxy;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.event.EventHandler;
import net.md_5.bungee.event.EventPriority;

/*
 * Listener qui log les actions des joueurs dans la base de données
 */
public class LogListener implements Listener
{
	private enum Action
	{
		LOGIN,
		CHAT,
		COMMAND
	}
	
	public LogListener(Plugin plugin)
	{
		plugin.getProxy().getPluginManager().registerListener(plugin, this);
	}
	
	@EventHandler(priority=EventPriority.LOW)
	public void onPostLogin(PostLoginEvent event)
	{
		log(event.getPlayer(), Action.LOGIN, event.getPlayer().getAddress().getAddress().toString());
	}
	
	@EventHandler(priority=EventPriority.LOW)
	public void onChat(ChatEvent event)
	{
		if (event.isCancelled())
			return;
		
		if (!(event.getSender() instanceof ProxiedPlayer))
			return;
		
		ProxiedPlayer player = (ProxiedPlayer)event.getSender();
		
		if (event.isCommand())
			log(player, Action.COMMAND, event.getMessage());
		else
			log(player, Action.CHAT, event.getMessage());
	}

	private static void log(ProxiedPlayer player, Action action, String data)
	{
		String server = player.getServer() == null ? "" : player.getServer().getInfo().getName();
		String playerName = player.getName();
		
		try
		{
			PreparedStatement ps = HeavenProxy.getConnection().prepareStatement("INSERT INTO logs (timestamp, server, player, action, data) VALUES (?, ?, ?, ?, ?);");
			ps.setTimestamp(1, new Timestamp(new Date().getTime()));
			ps.setString(2, server);
			ps.setString(3, playerName);
			ps.setInt(4, action.ordinal());
			ps.setString(5, data);
			
			ps.executeUpdate();
			ps.close();
		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
		}
	}
}