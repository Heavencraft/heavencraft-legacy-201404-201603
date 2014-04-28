package fr.heavencraft.heavenproxy.listeners;

import java.net.InetAddress;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.logging.Logger;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import net.md_5.bungee.event.EventPriority;
import fr.heavencraft.heavenproxy.HeavenProxy;
import fr.heavencraft.heavenproxy.Utils;

/*
 * Listener qui enregistre des informations dans la base de données (NSA)
 */
public class LogListener implements Listener
{
	private static final String TAG = "[LogListener] ";

	private final Logger log = Utils.getLogger();

	private enum Action
	{
		LOGIN,
		CHAT,
		COMMAND,
		MOD_HISTORY
	}

	public LogListener()
	{
		Utils.registerListener(this);

		log.info(TAG + "Initialized");
	}

	@EventHandler(priority = EventPriority.LOW)
	public void onPostLogin(final PostLoginEvent event)
	{
		log.info(TAG + event);
		
		ProxyServer.getInstance().getScheduler().runAsync(HeavenProxy.getInstance(), new Runnable()
		{
			
			@Override
			public void run()
			{
				InetAddress address = event.getPlayer().getAddress().getAddress();
				
				log(event.getPlayer(), Action.LOGIN, address.toString() + " " + Utils.getCity(address));
			}
		});
	}

	@EventHandler(priority = EventPriority.LOW)
	public void onChat(ChatEvent event)
	{
		if (event.isCancelled())
			return;

		log.info(TAG + event);

		if (!(event.getSender() instanceof ProxiedPlayer))
			return;

		ProxiedPlayer player = (ProxiedPlayer) event.getSender();

		if (event.isCommand())
			log(player, Action.COMMAND, event.getMessage());
		else
			log(player, Action.CHAT, event.getMessage());
	}

	/*
	 * Historique de modération
	 */

	public static void addKick(final String playerName, final String kickedBy, String reason)
	{
		reason = reason.length() > 0 ? " (" + reason + ")" : "";

		log("", playerName, Action.MOD_HISTORY, "Kicked by " + kickedBy + reason + ".");
	}

	public static void addBan(final String playerName, final String bannedBy, String reason)
	{
		reason = reason.length() > 0 ? " (" + reason + ")" : "";

		log("", playerName, Action.MOD_HISTORY, "Banned by " + bannedBy + reason + ".");
	}

	public static void addUnban(final String playerName, final String unbannedBy)
	{
		log("", playerName, Action.MOD_HISTORY, "Unbanned by " + unbannedBy + ".");
	}

	/*
	 * Méthodes d'insertion dans la base de données
	 */

	private static void log(ProxiedPlayer player, Action action, String data)
	{
		String server = player.getServer() == null ? "" : player.getServer().getInfo().getName();
		String playerName = player.getName();

		log(server, playerName, action, data);
	}

	private static void log(final String server, final String playerName, final Action action, final String data)
	{
		try
		{
			PreparedStatement ps = HeavenProxy.getConnection().prepareStatement(
					"INSERT INTO logs (timestamp, server, player, action, data) VALUES (?, ?, ?, ?, ?);");
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