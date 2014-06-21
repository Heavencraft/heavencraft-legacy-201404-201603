package fr.heavencraft.heavenrp.general;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import fr.heavencraft.heavenrp.RPPermissions;
import fr.heavencraft.utils.DevUtil;

public class WatchListener implements Listener
{
	public WatchListener()
	{
		DevUtil.registerListener(this);
	}

	@EventHandler(ignoreCancelled = true)
	public void onPlayerInteract(PlayerInteractEvent event)
	{
		Player player = event.getPlayer();

		if (!player.hasPermission(RPPermissions.WATCH))
			return;

		ItemStack item = event.getItem();

		if (item != null && item.getType() == Material.WATCH)
		{
			World world = player.getWorld();

			switch (event.getAction())
			{
				case LEFT_CLICK_AIR:
					world.strikeLightningEffect(player.getTargetBlock(null, 120).getLocation());
					event.setCancelled(true);
					break;

				case RIGHT_CLICK_AIR:
					world.strikeLightning(player.getTargetBlock(null, 120).getLocation());
					event.setCancelled(true);
					break;

				case LEFT_CLICK_BLOCK:
					world.strikeLightningEffect(event.getClickedBlock().getLocation());
					event.setCancelled(true);
					break;

				case RIGHT_CLICK_BLOCK:
					world.strikeLightning(event.getClickedBlock().getLocation());
					event.setCancelled(true);
					break;

				default:
					break;
			}
		}
	}
}