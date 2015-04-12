package fr.heavencraft.heavenproxy.servers;

import java.util.logging.Logger;

import net.md_5.bungee.api.event.ServerConnectedEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import fr.heavencraft.heavenproxy.Utils;

public class TitleListener implements Listener
{
	private static final String TAG = "[TitleListener] ";

	private final Logger log = Utils.getLogger();

	public TitleListener()
	{
		Utils.registerListener(this);

		log.info(TAG + "Initialized");
	}

	@EventHandler
	public void onServerConnected(ServerConnectedEvent event)
	{
		final String serverName = event.getServer().getInfo().getName();
		Server.getUniqueInstanceByName(serverName).getTitle().send(event.getPlayer());
	}
}
