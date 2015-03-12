package fr.heavencraft.api.providers.uuid;

import java.util.UUID;

import org.bukkit.Bukkit;

import fr.heavencraft.api.providers.connection.ConnectionHandler;
import fr.heavencraft.exceptions.UserNotFoundException;

public class BukkitUniqueIdProvider extends DefaultUniqueIdProvider
{
	public BukkitUniqueIdProvider(ConnectionHandler connectionProvider)
	{
		super(connectionProvider);
	}

	@Override
	public String getNameFromUniqueId(UUID id) throws UserNotFoundException
	{
		String name = Bukkit.getOfflinePlayer(id).getName();

		if (name != null)
		{
			log.info("Bukkit : %1$s => %2$s", id, name);
			// LogUtil.info(getClass(), "Bukkit : %1$s => %2$s", id, name);
			return name;
		}
		else
			return super.getNameFromUniqueId(id);
	}
}