package fr.tenkei.creaplugin;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import fr.tenkei.creaplugin.commands.CommandsManager;
import fr.tenkei.creaplugin.listeners.ListenersManager;
import fr.tenkei.creaplugin.managers.ManagersManager;
import fr.tenkei.creaplugin.utils.ConnectionManager;

public class MyPlugin extends JavaPlugin
{

	public final static String pluginTag = "[Plugin-Creative] ";
	public final static String builder = "server.builder";
	public final static String archiModo = "server.archimodo";
	public final static String administrator = "server.administrator";
	public final static int LIMITE_WORLDS = 1500;

	private static Logger _log = Logger.getLogger("Minecraft");

	private ManagersManager _myManagers;

	@Override
	public void onEnable()
	{
		super.onEnable();

		// Connexion DB
		ConnectionManager.enableConnection();
		// UpdateHomes.updateAllPlayers();

		// Gestionnaire de Manager
		_myManagers = new ManagersManager(this);

		// Gestionnaire de commandes
		new CommandsManager(this);

		// Gestionnaire de listeners
		new ListenersManager(this);

		_log.info(pluginTag + " " + getDescription().getVersion() + " created by " + getDescription().getAuthors());
	}

	@Override
	public void onDisable()
	{

		try
		{
			_myManagers.getUserManager().saveAllUsers();
			Bukkit.getScheduler().cancelTasks(this);
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}

	public ManagersManager getManagers()
	{
		return _myManagers;

	}

	public static void log(String str)
	{
		_log.info(pluginTag + " " + str);
	}
}
