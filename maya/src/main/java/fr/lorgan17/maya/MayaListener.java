package fr.lorgan17.maya;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;

public abstract class MayaListener implements Listener
{
	private final Logger log = Bukkit.getLogger();

	protected MayaListener()
	{
		Bukkit.getPluginManager().registerEvents(this, MayaPlugin.getInstance());
		log("Initialized");
	}

	protected void log(String format, Object... args)
	{
		log.info("[" + getClass().getSimpleName() + "] " + String.format(format, args));
	}
}