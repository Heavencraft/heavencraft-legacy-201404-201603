package fr.heavencraft.heavenproxy.listeners;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.model.CountryResponse;

import fr.heavencraft.heavenproxy.HeavenProxy;
import fr.heavencraft.heavenproxy.Utils;
import fr.heavencraft.heavenproxy.exceptions.UserNotFoundException;
import fr.heavencraft.heavenproxy.managers.KickManager;
import fr.heavencraft.heavenproxy.managers.UsersManager;
import fr.heavencraft.heavenproxy.managers.UsersManager.User;
import fr.heavencraft.heavenproxy.mute.MuteManager;

public class ChatListener implements Listener
{
	private final static String WELCOME_MESSAGE		= " * %1$s (%2$s) vient de rejoindre Heavencraft. {Bienvenue !}";
	private final static String JOIN_MESSAGE		= " * %1$s (%2$s) vient de se connecter.";
	private final static String QUIT_MESSAGE		= " * %1$s s'est déconnecté.";
	private final static String RAGEQUIT_MESSAGE	= " * %1$s a ragequit.";

	private final static String KICK_MESSAGE_WITHOUT_REASON	= " * %1$s a été exclu du serveur par {%2$s}.";
	private final static String KICK_MESSAGE_WITH_REASON	= " * %1$s a été exclu du serveur par {%2$s} : %3$s.";
	private final static String BAN_MESSAGE_WITHOUT_REASON	= " * %1$s a été banni du serveur par {%2$s}.";
	private final static String BAN_MESSAGE_WITH_REASON		= " * %1$s a été banni du serveur par {%2$s} : %3$s.";

	private final static String CHAT_MESSAGE = ChatColor.GRAY + "%1$s %2$s[%3$s]" + ChatColor.RESET + " %4$s";

	private final DatabaseReader _databaseReader;
	
	public ChatListener(HeavenProxy plugin) throws IOException
	{
		plugin.getProxy().getPluginManager().registerListener(plugin, this);
		_databaseReader = new DatabaseReader.Builder(new File("GeoLite2-Country.mmdb")).build();
	}

	@EventHandler
	public void onPlayerChat(ChatEvent event)
	{
		try
		{
			if (event.isCancelled() || event.isCommand())
				return;

			if (!(event.getSender() instanceof ProxiedPlayer))
				return;

			ProxiedPlayer player = (ProxiedPlayer) event.getSender();
			String message = event.getMessage();

			// BUGFIX : pour que la banque du semi-rp fonctionne
			if (player.getServer().getInfo().getName().equalsIgnoreCase("semirp") && Utils.isInteger(message))
				return;
			
			// Si le joueur est mute
			if (MuteManager.isMuted(player.getName()))
			{
				event.setCancelled(true);
				return;
			}
			
			// TODO : à supprimer si le serveur aventure dégage
			// BUGFIX : aventure
			if (player.getServer().getInfo().getName().equalsIgnoreCase("aventure"))
				return;

			if (player.hasPermission("heavencraft.chat.color"))
			{
				Matcher matcher = Pattern.compile("\\&([0-9A-Fa-f])").matcher(message);
				message = matcher.replaceAll("§$1");
			}

			chat(player, message);
			event.setCancelled(true);
		}
		catch (Throwable t)
		{
			t.printStackTrace();
		}
	}

	private void chat(ProxiedPlayer player, String message) throws UserNotFoundException
	{
		User user = UsersManager.getUserByUuid(player.getUUID());

		String color = user.getColor();
		String prefix = Utils.getPrefix(player);

		String chatMessage = String.format(CHAT_MESSAGE, prefix, color, player.getName(), message);

		ProxyServer.getInstance().broadcast(TextComponent.fromLegacyText(chatMessage));
	}

	@EventHandler
	public void onPostLogin(PostLoginEvent event)
	{
		ProxiedPlayer player = event.getPlayer();
		String name = player.getName();

		try
		{
			UsersManager.getUserByName(name);
			UsersManager.updateUser(player);

			onPlayerJoin(name, player.getAddress().getAddress(), false);
			
			if (player.hasPermission("heavencraft.commands.modo"))
			{
				Utils.sendMessage(player, ChatColor.GREEN + "Vous êtes membre du staff, VOTEZ !");
				Utils.sendMessage(player, ChatColor.GREEN +"http://mc-topserv.net/top/serveur.php?serv=46");
			}
		}
		catch (UserNotFoundException ex)
		{
			UsersManager.createUser(player);

			onPlayerJoin(name, player.getAddress().getAddress(), true);
		}
		
		HeavenProxy.getInstance().getLogger().info(player.getName() + " = " + player.getUniqueId());
	}

	@EventHandler
	public void onPlayerDisconnect(PlayerDisconnectEvent event)
	{
		String playerName = event.getPlayer().getName();
		String reason = KickManager.getReason(playerName);

		if (reason == null)
			onPlayerQuit(playerName);
		else
		{
			String[] data = reason.split("\\|", 3);

			if (data[0].equals("R"))
				onPlayerRagequit(playerName);
			else if (data[0].equals("K"))
				onPlayerKick(playerName, data[1], data[2]);
			else if (data[0].equals("B"))
				onPlayerBan(playerName, data[1], data[2]);
			else
				onPlayerQuit(playerName);
		}
	}
	
	/*
	 * Messages affichés dans le chat
	 */
	
	private void onPlayerJoin(final String playerName, final InetAddress address, final boolean welcome)
	{
		ProxyServer.getInstance().getScheduler().runAsync(HeavenProxy.getInstance(), new Runnable()
		{
			@Override
			public void run()
			{
				String location;
				
				try
				{
					CountryResponse response = _databaseReader.country(address);
					location = response.getCountry().getNames().get("fr");
				}
				catch (Throwable t)
				{
					t.printStackTrace();
					location = "France";
				}
				
				if (welcome)
					Utils.broadcastMessage(WELCOME_MESSAGE, playerName, location);
				else
					Utils.broadcastMessage(JOIN_MESSAGE, playerName, location);
			}
		});
	}

	private void onPlayerQuit(String playerName)
	{
		Utils.broadcastMessage(QUIT_MESSAGE, playerName);
	}

	private void onPlayerRagequit(String playerName)
	{
		Utils.broadcastMessage(RAGEQUIT_MESSAGE, playerName);
	}

	private void onPlayerKick(String playerName, String modName, String reason)
	{
		if (reason.isEmpty())
			Utils.broadcastMessage(KICK_MESSAGE_WITHOUT_REASON, playerName, modName);
		else
			Utils.broadcastMessage(KICK_MESSAGE_WITH_REASON, playerName, modName, reason);
	}

	private void onPlayerBan(String playerName, String modName, String reason)
	{
		if (reason.isEmpty())
			Utils.broadcastMessage(BAN_MESSAGE_WITHOUT_REASON, playerName, modName);
		else
			Utils.broadcastMessage(BAN_MESSAGE_WITH_REASON, playerName, modName, reason);
	}
}