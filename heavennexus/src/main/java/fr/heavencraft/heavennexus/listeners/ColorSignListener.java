package fr.heavencraft.heavennexus.listeners;

import java.util.regex.Pattern;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class ColorSignListener implements Listener {
	
	public ColorSignListener(JavaPlugin plugin)
	{
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}
	
    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    private void onSignChange(SignChangeEvent event)
    {
		if (event.getPlayer().isOp())
		{
            Pattern pattern = Pattern.compile("\\&([0-9A-Fa-f])");

            for (int i = 0; i != 4; i++)
            	event.setLine(i, pattern.matcher(event.getLine(i)).replaceAll("§$1"));
		}
    }
}
