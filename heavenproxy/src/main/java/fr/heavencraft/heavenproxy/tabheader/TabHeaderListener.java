package fr.heavencraft.heavenproxy.tabheader;

import java.util.logging.Logger;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ServerSwitchEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import fr.heavencraft.heavenproxy.Utils;

public class TabHeaderListener implements Listener
{
	private static final String TAG = "[TabHeaderListener] ";
	private static final Logger log = Utils.getLogger();

	public TabHeaderListener()
	{
		Utils.registerListener(this);

		log.info(TAG + "Initialized");
	}

	@EventHandler
	public void onServerSwitch(ServerSwitchEvent event)
	{
		ProxiedPlayer player = event.getPlayer();
		player.setTabHeader(
				new ComponentBuilder(Utils.getServerDisplayName(player)).color(ChatColor.GOLD).underlined(true)
						.create(), new ComponentBuilder(
						"Utilisez /list pour voir la liste de tous les joueurs connectés.").create());
	}
}
