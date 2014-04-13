package fr.heavencraft.heavenproxy.listeners;

import fr.heavencraft.heavenproxy.ban.BanManager;
import net.md_5.bungee.api.event.LoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.event.EventHandler;

public class NexusListener implements Listener {

	public NexusListener(Plugin plugin)
	{
		plugin.getProxy().getPluginManager().registerListener(plugin, this);
	}
	
	@EventHandler
	public void onLogin(LoginEvent event)
	{
		// TODO : remplacer par UUID
		//String uuid = event.getConnection().getUUID();
		String name = event.getConnection().getName();
		
		if (BanManager.isBanned(name))
		{
			event.setCancelled(true);
			event.setCancelReason("Vous êtes banni d'Heavencraft.\n\n" + BanManager.getReason(name));
		}
	}
}