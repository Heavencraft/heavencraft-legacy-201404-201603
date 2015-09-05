package fr.heavencraft.async.queries;

import java.sql.SQLException;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.bukkit.scheduler.BukkitRunnable;

import fr.heavencraft.utils.DevUtil;

public class QueriesHandler extends BukkitRunnable
{
	private static Queue<Query> queries = new ConcurrentLinkedQueue<Query>();

	public QueriesHandler()
	{
		runTaskTimerAsynchronously(DevUtil.getPlugin(), 1L, 1L);
	}

	@Override
	public void run()
	{
		if (!queries.isEmpty())
		{
			Query query;

			while ((query = queries.poll()) != null)
			{
				try
				{
					query.executeQuery();
				}
				catch (final SQLException ex)
				{
					ex.printStackTrace();
					query.onSQLException(ex);
				}
			}
		}
	}

	public static void addQuery(Query query)
	{
		queries.add(query);
	}
}