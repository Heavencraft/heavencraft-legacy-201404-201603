package fr.heavencraft.railways;

import org.bukkit.plugin.java.JavaPlugin;

import fr.heavencraft.InitManager;

public class HeavenRailway extends JavaPlugin {
	private static HeavenRailway _instance;

	@Override
	public void onEnable()
	{
		super.onEnable();
		_instance = this;
	
		// Charger tout les listners 
		InitManager.init();
	}
	
	@Override
    public void onDisable() {
    }

	public static HeavenRailway getInstance()
	{
		return _instance;
	}
	
}
