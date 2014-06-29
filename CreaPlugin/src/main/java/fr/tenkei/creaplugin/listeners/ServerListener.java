package fr.tenkei.creaplugin.listeners;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.weather.ThunderChangeEvent;
import org.bukkit.event.weather.WeatherChangeEvent;

import fr.heavencraft.exceptions.HeavenException;
import fr.tenkei.creaplugin.MyPlugin;
import fr.tenkei.creaplugin.managers.UserManager;
import fr.tenkei.creaplugin.managers.entities.User;

public class ServerListener implements Listener
{

	private final MyPlugin _plugin;

	public ServerListener(MyPlugin plugin)
	{
		Bukkit.getPluginManager().registerEvents(this, plugin);
		_plugin = plugin;
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event)
	{
		_plugin.getManagers().getSaveManager().autoSave();

		User user;
		try
		{
			user = UserManager.getUser(event.getPlayer().getName());
			user.onLogin();

		}
		catch (HeavenException e)
		{

			MyPlugin.log("Nouveau joueur : " + event.getPlayer().getName());
		}

		// if(event.getPlayer().hasPermission(MyPlugin.administrator))
		// _plugin.getSelectionManager().enable(event.getPlayer().getName());

	}

	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) throws HeavenException
	{
		UserManager.saveUser(event.getPlayer().getName());

		_plugin.getManagers().getSaveManager().autoSave();
	}

	@EventHandler(ignoreCancelled = true)
	private void onEntityDamageByEntity(EntityDamageByEntityEvent event)
	{
		event.setCancelled(true);
	}

	// Weather
	@EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
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