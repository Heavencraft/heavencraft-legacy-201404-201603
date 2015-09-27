package fr.heavencraft.async.actions;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.bukkit.scheduler.BukkitRunnable;

import fr.heavencraft.utils.DevUtil;

public class ActionsHandler extends BukkitRunnable
{
	private static Queue<Action> actions = new ConcurrentLinkedQueue<Action>();

	public ActionsHandler()
	{
		runTaskTimer(DevUtil.getPlugin(), 1L, 1L);
	}

	@Override
	public void run()
	{
		if (!actions.isEmpty())
		{
			Action action;

			while ((action = actions.poll()) != null)
			{
				try
				{
					action.executeAction();
					action.onSuccess();
				}
				catch (Exception ex)
				{
					action.onFailure();
				}
			}
		}
	}

	public static void addAction(Action action)
	{
		actions.add(action);
	}
}