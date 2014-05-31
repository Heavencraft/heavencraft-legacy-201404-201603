package fr.heavencraft.heavenrp.general.users;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import fr.heavencraft.Utils;
import fr.heavencraft.exceptions.HeavenException;
import fr.heavencraft.exceptions.UserNotFoundException;

public class UserListener implements Listener
{
	public UserListener()
	{
		Utils.registerListener(this);
	}

	@EventHandler(priority = EventPriority.LOWEST)
	private void onPlayerLogin(PlayerLoginEvent event) throws HeavenException
	{
		Player player = event.getPlayer();

		String name = player.getName();
		String uuid = Utils.getUUID(name);

		Utils.logInfo("UsersListener.onPlayerLogin : %1$s = %2$s", uuid, name);

		try
		{
			UserProvider.updateName(uuid, name);
		}
		catch (UserNotFoundException ex)
		{
			UserProvider.createUser(uuid, name);
		}
		catch (HeavenException ex)
		{
			ex.printStackTrace();
		}
	}

	@EventHandler(priority = EventPriority.MONITOR)
	private void onPlayerQuit(PlayerQuitEvent event)
	{
		UserProvider.removeFromCache(event.getPlayer().getName());
	}

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	private void onPlayerKick(PlayerKickEvent event)
	{
		UserProvider.removeFromCache(event.getPlayer().getName());
	}
}