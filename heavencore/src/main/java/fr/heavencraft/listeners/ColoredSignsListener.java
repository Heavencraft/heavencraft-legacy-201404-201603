package fr.heavencraft.listeners;

import static fr.heavencraft.utils.DevUtil.registerListener;

import java.util.regex.Pattern;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;

import fr.heavencraft.Permissions;

/**
 * This listener allows to place colored signs
 * 
 * @author lorgan17
 */
public class ColoredSignsListener implements Listener
{
	public ColoredSignsListener()
	{
		registerListener(this);
	}

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	private void onSignChange(SignChangeEvent event)
	{
		if (event.getPlayer().hasPermission(Permissions.COLORED_SIGNS))
		{
			final Pattern pattern = Pattern.compile("\\&([0-9A-Za-z])");

			for (int i = 0; i != 4; i++)
				event.setLine(i, pattern.matcher(event.getLine(i)).replaceAll("§$1"));
		}
	}
}