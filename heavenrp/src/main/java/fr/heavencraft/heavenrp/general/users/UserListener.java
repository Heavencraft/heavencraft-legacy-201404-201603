package fr.heavencraft.heavenrp.general.users;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

import fr.heavencraft.exceptions.HeavenException;
import fr.heavencraft.exceptions.UserNotFoundException;
import fr.heavencraft.utils.DevUtil;
import fr.heavencraft.utils.PlayerUtil;

public class UserListener implements Listener
{
	public UserListener()
	{
		DevUtil.registerListener(this);
	}

	@EventHandler(priority = EventPriority.LOWEST)
	private void onPlayerLogin(PlayerLoginEvent event) throws HeavenException
	{
		final Player player = event.getPlayer();

		final String name = player.getName();
		final String uuid = PlayerUtil.getUUID(player);

		DevUtil.logInfo("UsersListener.onPlayerLogin : %1$s = %2$s", uuid, name);

		try
		{
			User user = UserProvider.getUserByUUID(uuid);

			if (!name.equals(user.getName()))
			{
				UserProvider.updateName(uuid, name);
			}
		}
		catch (UserNotFoundException ex)
		{
			UserProvider.createUser(uuid, name);
		}
	}
}