package fr.heavencraft.async.queries;

import java.sql.SQLException;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import fr.heavencraft.exceptions.HeavenException;
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
			Query tmp;

			while ((tmp = queries.poll()) != null)
			{
				final Query query = tmp; // Gruge final

				try
				{
					query.executeQuery();

					Bukkit.getScheduler().runTask(DevUtil.getPlugin(), new Runnable()
					{
						@Override
						public void run()
						{
							query.onSuccess();
						}
					});
				}
				catch (final HeavenException ex)
				{
					Bukkit.getScheduler().runTask(DevUtil.getPlugin(), new Runnable()
					{
						@Override
						public void run()
						{
							query.onHeavenException(ex);
						}
					});
				}
				catch (final SQLException ex)
				{
					ex.printStackTrace();

					Bukkit.getScheduler().runTask(DevUtil.getPlugin(), new Runnable()
					{
						@Override
						public void run()
						{
							query.onSQLException(ex);
						}
					});
				}
			}
		}
	}

	public static void addQuery(Query query)
	{
		queries.add(query);
	}
}