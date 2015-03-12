package fr.heavencraft;

import static fr.heavencraft.utils.DevUtil.setPlugin;

import org.bukkit.plugin.java.JavaPlugin;

import fr.heavencraft.api.providers.connection.ConnectionHandlerFactory;
import fr.heavencraft.api.providers.connection.Database;
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

	private final UniqueIdProvider uniqueIdProvider = new BukkitUniqueIdProvider(
			ConnectionHandlerFactory.getConnectionHandler(Database.PROXY));

	public UniqueIdProvider getUniqueIdProvider()
	{
		return uniqueIdProvider;
	}
}