package fr.heavencraft.heavenproxy.chat;

import java.io.IOException;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import net.md_5.bungee.event.EventPriority;
import fr.heavencraft.heavenproxy.Utils;

public class ChatListener implements Listener
{
	private static final String TAG = "[ChatListener] ";

	private final Logger log = Utils.getLogger();

	public ChatListener() throws IOException
	{
		Utils.registerListener(this);

		log.info(TAG + "Initialized");
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerChat(ChatEvent event)
	{
		if (event.isCancelled())
			return;

		// log.info(TAG + event);

		if (event.isCommand())
			return;

		if (!(event.getSender() instanceof ProxiedPlayer))
			return;

		ProxiedPlayer player = (ProxiedPlayer) event.getSender();
		String message = event.getMessage();

		// BUGFIX : pour que la banque du semi-rp fonctionne
		if (player.getServer().getInfo().getName().equalsIgnoreCase("semirp") && Utils.isInteger(message))
			return;

		if (player.hasPermission("heavencraft.chat.color"))
		{
			Matcher matcher = Pattern.compile("\\&([0-9A-Fa-f])").matcher(message);
			message = matcher.replaceAll("§$1");
		}

		ChatManager.sendChatMessage(player, message);
		event.setCancelled(true);
	}

	@EventHandler
	public void onPlayerDisconnect(PlayerDisconnectEvent event)
	{
		log.info(TAG + event);

		String playerName = event.getPlayer().getName();
		String reason = DisconnectReasonManager.getReason(playerName);

		if (reason == null)
			ChatManager.sendQuitMessage(playerName);
		else
		{
			String[] data = reason.split("\\|", 3);

			if (data[0].equals("R"))
				ChatManager.sendRagequitMessage(playerName);
			else if (data[0].equals("K"))
				ChatManager.sendKickMessage(playerName, data[1], data[2]);
			else if (data[0].equals("B"))
				ChatManager.sendBanMessage(playerName, data[1], data[2]);
			else
				ChatManager.sendQuitMessage(playerName);
		}
	}
}