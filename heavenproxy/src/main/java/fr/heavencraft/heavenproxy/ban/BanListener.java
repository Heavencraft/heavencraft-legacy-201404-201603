package fr.heavencraft.heavenproxy.ban;

import java.util.logging.Logger;

import net.md_5.bungee.api.event.LoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import fr.heavencraft.heavenproxy.Utils;

public class BanListener implements Listener
{
	private static final String TAG = "[BanListener] ";

	private final Logger log = Utils.getLogger();

	public BanListener()
	{
		Utils.registerListener(this);

		log.info(TAG + "Initialized");
	}

	@EventHandler
	public void onLogin(LoginEvent event)
	{
		if (event.isCancelled())
			return;

		log.info(TAG + event);

		String uuid = Utils.getUUID(event.getConnection());
		String reason = BanManager.getReason(uuid);

		if (reason != null)
		{
			event.setCancelled(true);
			event.setCancelReason("Vous êtes banni d'Heavencraft.\n\n" + reason);

			log.info("[BanListener] Account " + uuid + " is banned : " + reason);
		}
	}
}