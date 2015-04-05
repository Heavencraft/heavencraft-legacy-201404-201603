package fr.heavencraft.portiques;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;

import fr.heavencraft.Utils.ChatUtil;
import fr.heavencraft.railways.HeavenRailway;

public class PortiqueSignListener implements Listener
{
	private final String _permission;
	private final String _tag;

	public PortiqueSignListener()
	{
		_permission = "railways.portique.create";
		_tag = "[portique]";
		Bukkit.getPluginManager().registerEvents(this, HeavenRailway.getInstance());
	}

	@EventHandler(ignoreCancelled = true)
	private void onSignChange(SignChangeEvent event)
	{
		Player player = event.getPlayer();

		if (player == null || (!player.hasPermission(_permission) && player.isOp() == false)
				|| !event.getLine(0).equalsIgnoreCase(_tag))
			return;

		event.setLine(0, ChatColor.GREEN + _tag);
		ChatUtil.sendMessage(player, "Le panneau {%1$s} de portique a été placé correctement.", _tag);
		return;

	}
}
