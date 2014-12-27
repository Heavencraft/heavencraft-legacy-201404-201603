package fr.heavencraft.api.providers.uuid;

import java.util.UUID;

import org.bukkit.Bukkit;

import fr.heavencraft.HeavenPlugin;
import fr.heavencraft.exceptions.UserNotFoundException;

public class BukkitUniqueIdProvider extends DefaultUniqueIdProvider
{
	public BukkitUniqueIdProvider(HeavenPlugin plugin)
	{
		super(plugin);
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