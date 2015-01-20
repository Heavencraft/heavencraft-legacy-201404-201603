package fr.heavencraft.listeners;

import java.util.Arrays;
import java.util.Collection;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

import fr.heavencraft.Permissions;
import fr.heavencraft.utils.DevUtil;

public class ForbiddenBlocksListener implements Listener
{
	private final Collection<Material> types;

	public ForbiddenBlocksListener(Material... types)
	{
		this.types = Arrays.asList(types);
		DevUtil.registerListener(this);
	}

	@EventHandler(ignoreCancelled = true)
	private void onBlockPlace(BlockPlaceEvent event)
	{
		Player player = event.getPlayer();

		if (player.hasPermission(Permissions.FORBIDDEN_BLOCKS))
			return;

		if (types.contains(event.getBlockPlaced().getType()))
		{
			event.setCancelled(true);
		}
	}
}