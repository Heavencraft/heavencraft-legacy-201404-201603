package fr.heavencraft.heavenproxy.listeners;

import fr.heavencraft.heavenproxy.HeavenProxy;
import fr.heavencraft.heavenproxy.managers.UsersManager;
import fr.heavencraft.heavenproxy.managers.UsersManager.User;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class TabListener implements Listener
{
	public TabListener()
	{
		HeavenProxy plugin = HeavenProxy.getInstance();
		plugin.getProxy().getPluginManager().registerListener(plugin, this);
	}

	@EventHandler
	public void onPostLogin(PostLoginEvent event)
	{
		try
		{
			ProxiedPlayer player = event.getPlayer();
			User user = UsersManager.getUserByUuid(player.getUUID());

			if (!user.getColor().equals("§f"))
			{
				String displayName = user.getColor() + user.getName();

				if (displayName.length() > 16)
					displayName = displayName.substring(0, 16);

				player.setDisplayName(displayName);
			}
		}
		catch (Throwable t)
		{
			t.printStackTrace();
		}
	}
}