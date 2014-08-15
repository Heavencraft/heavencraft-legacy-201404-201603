package fr.tenkei.creaplugin.managers;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import com.sk89q.worldedit.bukkit.WorldEditPlugin;

import fr.tenkei.creaplugin.MyPlugin;

public class ManagersManager
{

	private final MyPlugin _plugin;
	private WorldEditPlugin _worldEdit;

	private final AVManager _avManager;
	private final WarpsManager _warpsManager;

	public ManagersManager(MyPlugin plugin)
	{
		_plugin = plugin;

		Plugin worldEdit = Bukkit.getPluginManager().getPlugin("WorldEdit");
		if (worldEdit != null && worldEdit instanceof WorldEditPlugin)
			_worldEdit = (WorldEditPlugin) worldEdit;

		// Sans donnée membre
		new WorldsManager();

		// Avec donnée membre
		_avManager = new AVManager(this);
		_warpsManager = new WarpsManager();
	}

	// Getters

	public WorldEditPlugin getWorldEditPlugin()
	{
		return _worldEdit;
	}

	public MyPlugin getPlugin()
	{
		return _plugin;
	}

	public AVManager getAVManager()
	{
		return _avManager;
	}

	public WarpsManager getWarpsManager()
	{
		return _warpsManager;
	}
}