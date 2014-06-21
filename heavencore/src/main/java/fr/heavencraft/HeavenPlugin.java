package fr.heavencraft;

import static fr.heavencraft.utils.DevUtil.setPlugin;

import org.bukkit.plugin.java.JavaPlugin;

public class HeavenPlugin extends JavaPlugin
{
	@Override
	public void onEnable()
	{
		super.onEnable();

		setPlugin(this);
	}
}