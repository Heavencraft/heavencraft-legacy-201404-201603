package fr.heavencraft;

import static fr.heavencraft.utils.DevUtil.setPlugin;

import org.bukkit.plugin.java.JavaPlugin;

import fr.heavencraft.api.providers.connection.ConnectionProvider.Database;
import fr.heavencraft.api.providers.connection.DefaultConnectionProvider;
import fr.heavencraft.api.providers.uuid.BukkitUniqueIdProvider;
import fr.heavencraft.api.providers.uuid.UniqueIdProvider;

public class HeavenPlugin extends JavaPlugin
{
	@Override
	public void onEnable()
	{
		super.onEnable();

		setPlugin(this);
	}

	private final UniqueIdProvider uniqueIdProvider = new BukkitUniqueIdProvider(new DefaultConnectionProvider(Database.PROXY));

	public UniqueIdProvider getUniqueIdProvider()
	{
		return uniqueIdProvider;
	}
}