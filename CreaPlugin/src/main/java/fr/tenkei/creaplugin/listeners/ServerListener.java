package fr.tenkei.creaplugin.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.weather.ThunderChangeEvent;
import org.bukkit.event.weather.WeatherChangeEvent;

import fr.heavencraft.utils.DevUtil;

public class ServerListener implements Listener
{
	public ServerListener()
	{
		DevUtil.registerListener(this);
	}

	@EventHandler(ignoreCancelled = true)
	private void onEntityDamageByEntity(EntityDamageByEntityEvent event)
	{
		event.setCancelled(true);
	}

	@EventHandler(ignoreCancelled = true)
	public void onWeatherChange(WeatherChangeEvent event)
	{
		if (event.toWeatherState())
			event.setCancelled(true);
	}

	@EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
	public void onThunderChange(ThunderChangeEvent event)
	{
		if (event.toThunderState())
			event.setCancelled(true);
	}
}