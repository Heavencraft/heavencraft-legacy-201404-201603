package fr.heavencraft.tasks;

import static fr.heavencraft.utils.DevUtil.getPlugin;
import static fr.heavencraft.utils.DevUtil.registerListener;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.scheduler.BukkitRunnable;

import fr.heavencraft.utils.DevUtil;

public class SaveTaskNoWorldGuard extends BukkitRunnable implements Listener
{
	public SaveTaskNoWorldGuard()
	{
		registerListener(this);
		runTaskTimer(getPlugin(), 6000L, 6000L);
	}

	@Override
	public void run()
	{
		DevUtil.logInfo("Sauvegarde en cours...");

		for (World world : Bukkit.getWorlds())
		{
			world.save();
		}

		Bukkit.savePlayers();

		DevUtil.logInfo("Sauvegarde terminée !");
	}

	@EventHandler
	private void onPluginDisable(PluginDisableEvent event)
	{
		if (event.getPlugin().equals(getPlugin()))
			run();
	}
}