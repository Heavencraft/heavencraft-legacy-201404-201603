package fr.tenkei.creaplugin.managers;

import java.util.Date;

import org.bukkit.World;

import fr.heavencraft.exceptions.HeavenException;
import fr.tenkei.creaplugin.MyPlugin;

public class SaveManager
{

	private final MyPlugin _plugin;
	private final ManagersManager _manager;
	private Date _lastSave;
	private Date _lastHours;

	public SaveManager(MyPlugin plugin, ManagersManager manager)
	{
		_plugin = plugin;
		_manager = manager;
		_lastSave = new Date();
		_lastHours = new Date();
	}

	public void autoSave()
	{
		Date now = new Date();
		long diffInSeconds = (now.getTime() - _lastSave.getTime()) / 1000;
		long diffS = (now.getTime() - _lastHours.getTime()) / 1000;

		if (diffInSeconds >= 550)
		{
			_lastSave = now;
			saveNow();
		}

		if (diffS >= 800)
		{
			_lastHours = now;

			WorldsManager.getTheTravaux().setTime(6000L); // Remise à zero
			WorldsManager.getTheCreative().setTime(6000L); // du temps (jours)

			try
			{
				UserManager.jetonByConnected();
			}
			catch (HeavenException e)
			{
				e.printStackTrace();
			}
		}
	}

	public void saveNow()
	{
		_plugin.getServer().getScheduler().scheduleSyncDelayedTask(_plugin, new AutoSaveMethod(_plugin), 3);
	}

	public class AutoSaveMethod implements Runnable
	{
		private final MyPlugin _plugin;

		public AutoSaveMethod(MyPlugin plugin)
		{
			_plugin = plugin;
		}

		public void run()
		{
			for (World world : _plugin.getServer().getWorlds())
				world.save();

			_plugin.getServer().savePlayers();
		}
	}
}
