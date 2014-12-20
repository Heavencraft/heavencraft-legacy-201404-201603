package fr.lorgan17.maya;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;

public abstract class MayaListener implements Listener
{
	protected final Logger log = Logger.getLogger(getClass().getSimpleName());

	protected MayaListener()
	{
		Bukkit.getPluginManager().registerEvents(this, MayaPlugin.getInstance());
		log.info("Initialized");
	}
}