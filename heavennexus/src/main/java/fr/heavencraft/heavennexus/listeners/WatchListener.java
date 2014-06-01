package fr.heavencraft.heavennexus.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import fr.heavencraft.heavennexus.HeavenNexus;

public class WatchListener implements Listener
{
	public WatchListener(HeavenNexus plugin)
	{
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}

	@EventHandler(ignoreCancelled = true)
	public void onPlayerInteract(PlayerInteractEvent event)
	{
		Player player = event.getPlayer();

		ItemStack item = event.getItem();

		if (item != null && item.getType() == Material.WATCH)
		{
			World world = player.getWorld();
			
			switch (event.getAction())
			{
				case LEFT_CLICK_AIR:
				case RIGHT_CLICK_AIR:
					//world.strikeLightningEffect(player.getTargetBlock(null, 100).getLocation());
					world.strikeLightningEffect(player.getEyeLocation());
					event.setCancelled(true);
					break;
				case LEFT_CLICK_BLOCK:
				case RIGHT_CLICK_BLOCK:
					world.strikeLightningEffect(event.getClickedBlock().getLocation());
					event.setCancelled(true);
					break;
				default:
					break;
			}
		}
	}
}