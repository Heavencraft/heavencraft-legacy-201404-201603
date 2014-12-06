package fr.heavencraft.heavencrea.users;

import static fr.heavencraft.utils.DevUtil.registerListener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import fr.heavencraft.exceptions.HeavenException;
import fr.heavencraft.exceptions.UserNotFoundException;
import fr.heavencraft.utils.ChatUtil;
import fr.heavencraft.utils.DevUtil;
import fr.heavencraft.utils.PlayerUtil;

public class UserListener implements Listener
{
	public UserListener()
	{
		registerListener(this);
	}

	@EventHandler(priority = EventPriority.LOWEST)
	private void onPlayerLogin(PlayerLoginEvent event) throws HeavenException
	{
		Player player = event.getPlayer();

		String name = player.getName();
		String uuid = PlayerUtil.getUUID(name);

		try
		{
			UserProvider.updateName(uuid, name);
		}
		catch (UserNotFoundException ex)
		{
			UserProvider.createUser(uuid, name);
			DevUtil.logInfo("Nouveau joueur : %1$s", event.getPlayer().getName());
		}
		catch (HeavenException ex)
		{
			ex.printStackTrace();
		}
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event)
	{
		try
		{
			User user = UserProvider.getUserByName(event.getPlayer().getName());
			String currentDate = Stuff.getDateTime();

			if (user.shouldBeNewDay())
			{
				user.updateBalance(100);
				ChatUtil.sendMessage(event.getPlayer(), "Vous venez d'obtenir {100} jeton en vous connectant !");
				user.setSavedData(currentDate);
			}

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