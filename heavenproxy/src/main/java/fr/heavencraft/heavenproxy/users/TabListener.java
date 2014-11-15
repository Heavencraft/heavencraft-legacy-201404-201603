package fr.heavencraft.heavenproxy.users;

import java.util.logging.Logger;

import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import net.md_5.bungee.event.EventPriority;
import fr.heavencraft.heavenproxy.Utils;
import fr.heavencraft.heavenproxy.exceptions.HeavenException;

public class TabListener implements Listener
{
	private static final String TAG = "[TabListener] ";
	private static final Logger log = Utils.getLogger();

	public TabListener()
	{
		Utils.registerListener(this);

		log.info(TAG + "Initialized");
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void onPostLogin(PostLoginEvent event) throws HeavenException
	{
		ProxiedPlayer player = event.getPlayer();
		String playerName = player.getName();

		User user = UserProvider.getUserByName(playerName);

		if (!user.getColor().equals("§f"))
		{
			String displayName = user.getColor() + playerName;

			if (displayName.length() > 16)
				displayName = displayName.substring(0, 16);

			player.setDisplayName(displayName);
		}
	}
}