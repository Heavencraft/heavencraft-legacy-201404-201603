package fr.tenkei.creaplugin.managers;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import com.sk89q.worldedit.bukkit.WorldEditPlugin;

import fr.tenkei.creaplugin.MyPlugin;

public class ManagersManager {

	private MyPlugin _plugin;
	private WorldEditPlugin _worldEdit;
	
	private UserManager _userManager;
	private AVManager _avManager;
	private SaveManager _saveManager;
	private WarpsManager _warpsManager;
	
	public ManagersManager(MyPlugin plugin){
		_plugin = plugin;
		
		Plugin worldEdit = Bukkit.getPluginManager().getPlugin("WorldEdit");
        if (worldEdit != null && worldEdit instanceof WorldEditPlugin)
                _worldEdit = (WorldEditPlugin) worldEdit;
        
        // Sans donnée membre
        new WorldsManager();
        
        // Avec donnée membre
        _userManager = new UserManager(plugin);
        _avManager = new AVManager(this);
        _saveManager = new SaveManager(plugin, this);
        _warpsManager = new WarpsManager();
	}
	
	// Getters
	
	public WorldEditPlugin getWorldEditPlugin(){
		return _worldEdit;
	}
	
	public MyPlugin getPlugin(){
		return _plugin;
	}
	
	public UserManager getUserManager(){
		return _userManager;
	}
	
	public AVManager getAVManager(){
		return _avManager;
	}
	
	public SaveManager getSaveManager(){	
		return _saveManager;
	}
	
	public WarpsManager getWarpsManager(){	
		return _warpsManager;
	}
}
