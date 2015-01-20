package fr.heavencraft.heavenproxy.ban;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;

import net.md_5.bungee.api.ServerPing;
import net.md_5.bungee.api.event.LoginEvent;
import net.md_5.bungee.api.event.ProxyPingEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import net.md_5.bungee.event.EventPriority;
import fr.heavencraft.heavenproxy.Utils;

public class SilentBanListener implements Listener
{
	Collection<String> banned = Arrays.asList(//
			"ef032164-34a0-475c-81b9-389a4d4a2b8f", // Sigmund_Frog
			"b07d767d-01fa-40e2-89b0-c4696a3ccb1d", // forrto
			"93f49a17-80f2-47bd-a815-4e2e3336bf83"); // Firebest1993

	Collection<String> bannedAddresses = new HashSet<String>();

	public SilentBanListener()
	{
		Utils.registerListener(this);
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onLogin(LoginEvent event)
	{
		if (event.isCancelled())
			return;

		final String uuid = event.getConnection().getUniqueId().toString();
		final String address = event.getConnection().getAddress().getAddress().toString();

		if (banned.contains(uuid) || bannedAddresses.contains(address))
		{
			bannedAddresses.add(address);

			event.setCancelReason("java.net.ConnectException: Connection refused:");
			event.setCancelled(true);
		}
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onProxyPing(ProxyPingEvent event)
	{
		final String address = event.getConnection().getAddress().getAddress().toString();

		if (bannedAddresses.contains(address))
		{
			event.setResponse(new ServerPing());
		}
	}
}