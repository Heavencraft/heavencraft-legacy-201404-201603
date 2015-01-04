package fr.heavencraft.api.providers.uuid;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import fr.heavencraft.HeavenPlugin;
import fr.heavencraft.api.providers.connection.ConnectionProvider.Database;
import fr.heavencraft.exceptions.UserNotFoundException;
import fr.heavencraft.utils.HeavenLog;

public class DefaultUniqueIdProvider implements UniqueIdProvider
{
	private static final String GET_NAME = "SELECT name FROM users WHERE uuid = ? LIMIT 1;";

	protected final HeavenLog log = HeavenLog.getLogger(getClass());

	private final HeavenPlugin plugin;
	private final Map<UUID, String> nameByUniqueId = new ConcurrentHashMap<UUID, String>();

	public DefaultUniqueIdProvider(HeavenPlugin plugin)
	{
		this.plugin = plugin;
	}

	@Override
	public String getNameFromUniqueId(UUID id) throws UserNotFoundException
	{
		String name = nameByUniqueId.get(id);

		if (name != null)
		{
			log.info("Cache : %1$s => %2$s", id, name);
			return name;
		}

		try (PreparedStatement ps = plugin.getConnectionProvider().getConnection(Database.PROXY).prepareStatement(GET_NAME))
		{
			ps.setString(1, id.toString().replace("-", ""));

			try (ResultSet rs = ps.executeQuery())
			{
				if (!rs.next())
					throw new UserNotFoundException(id);

				name = rs.getString("name");
				nameByUniqueId.put(id, name);
				log.info("Database : %1$s => %2$s", id, name);
				return name;
			}
		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
			throw new UserNotFoundException(id);
		}
	}

	@Override
	public void clearCache()
	{
		nameByUniqueId.clear();
	}

}