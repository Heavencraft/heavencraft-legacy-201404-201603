package fr.heavencraft.heavenproxy.motd;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.event.ProxyPingEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import fr.heavencraft.heavenproxy.Utils;

public class ProxyPingListener implements Listener
{
	private static final String FIRST_LINE = "§l§fHeaven§bcraft§r [1.8.1]\n";

	public ProxyPingListener()
	{
		Utils.registerListener(this);
	}

	@EventHandler
	public void onProxyPing(ProxyPingEvent event)
	{
		String description = FIRST_LINE;

		description += getServerString("Semi-RP", "semirp") + " ";
		description += getServerString("Créatif", "creative", "musee", "build") + " ";
		// description += getServerString("Factions", "factions") + " ";
		description += getServerString("Survie", "origines", "ultrahard") + " ";
		description += getServerString("Jeux", "infected", "tntrun", "mariokart", "paintball", "hungergames");

		event.getResponse().setDescription(description);
	}

	private static String getServerString(String name, String... servers)
	{
		for (String server : servers)
		{
			ServerInfo serverInfo = ProxyServer.getInstance().getServerInfo(server);

			if (serverInfo != null && serverInfo.getPlayers().size() != 0)
			{
				return ChatColor.GREEN + name;
			}
		}

		return ChatColor.RED + name;
	}
}