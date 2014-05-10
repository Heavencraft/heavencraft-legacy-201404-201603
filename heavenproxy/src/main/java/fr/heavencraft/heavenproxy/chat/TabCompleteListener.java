package fr.heavencraft.heavenproxy.chat;

import java.util.logging.Logger;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.TabCompleteEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import fr.heavencraft.heavenproxy.Utils;

public class TabCompleteListener implements Listener
{
	private static final String TAG = "[TabCompleteListener] ";

	private static final Logger log = Utils.getLogger();

	public TabCompleteListener()
	{
		Utils.registerListener(this);

		log.info(TAG + "Initialized");
	}

	@EventHandler
	public void onTabComplete(TabCompleteEvent event)
	{
		if (event.isCancelled())
			return;

		String cursor = event.getCursor();
		String playerName = cursor.substring(cursor.lastIndexOf(" ") + 1);
		playerName = playerName.toLowerCase();

		for (ProxiedPlayer player : ProxyServer.getInstance().getPlayers())
			if (player.getName().toLowerCase().startsWith(playerName))
				event.getSuggestions().add(player.getName());
	}
}