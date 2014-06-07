package fr.tenkei.creaplugin.listeners.protection;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Event.Result;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerBucketFillEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import fr.tenkei.creaplugin.MyPlugin;
import fr.tenkei.creaplugin.exceptions.MyException;
import fr.tenkei.creaplugin.managers.entities.Region;
import fr.tenkei.creaplugin.utils.Message;
import fr.tenkei.creaplugin.utils.Stuff;


public class ProtectionPlayerListener implements Listener {
	
	public ProtectionPlayerListener(MyPlugin plugin) {
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}
	
	
	@EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
	public void onPlayerBedEnter(PlayerBedEnterEvent event) {
		Player player = event.getPlayer();
		Block block = event.getBed();
		
		if (!Stuff.canBeDestroyed(player, block)) {
			Message.sendMessage(player, "Tu vas pas dormir dans le lit d'un autre !? Naméoh !");
			event.setCancelled(true);
		}
	}

	@EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
	public void onPlayerBucketFill(PlayerBucketFillEvent event) {
		Player player = event.getPlayer();
		Block block = event.getBlockClicked().getRelative(event.getBlockFace());
		
		if (!Stuff.canBeDestroyed(player, block)) {
			Message.sendMessage(player, "Cet endroit est protégé.");
			event.setCancelled(true);
		}
	}
	
	@EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
	public void onPlayerBucketEmpty(PlayerBucketEmptyEvent event) {
		Player player = event.getPlayer();
		Block block = event.getBlockClicked().getRelative(event.getBlockFace());
		
		if (!Stuff.canBeDestroyed(player, block)) {
			Message.sendMessage(player, "Cet endroit est protégé.");
			event.setCancelled(true);
		}
	}

	@SuppressWarnings("incomplete-switch")
	@EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
	public void onPlayerInteract(PlayerInteractEvent event) throws MyException
	{
		Player player = event.getPlayer();
		Block block = event.getClickedBlock();
		ItemStack item = event.getItem();
		
		 
		switch (event.getAction())
		{
		case RIGHT_CLICK_BLOCK:
			
			if (item != null && item.getType() == Material.ARROW) {
				Region region = Region.getRegionByLocation(block.getX(), block.getY(), block.getZ(), block.getWorld());
				
				if (region == null)
					Message.sendMessage(player, "Aucune protection");
				else {
					Message.sendMessage(player, "Protection : " + region.getId());
					Message.sendMessage(player, "Proprio : " + region.getProprio());
				}

				event.setUseItemInHand(Result.DENY);
				event.setCancelled(true);
			}
			else if (item != null && (item.getType() == Material.MINECART || item.getType() == Material.BOAT)) {
				if (!Stuff.canBeDestroyed(player, block)) {
					Message.sendMessage(player, "Cet endroit est protégé.");
					event.setUseItemInHand(Result.DENY);
					event.setCancelled(true);
				}
			}
			
			else {
				switch (block.getType()) {
					case ANVIL:
					case BREWING_STAND:
					case BURNING_FURNACE:
					case CAKE_BLOCK:
					case CHEST:
					case DISPENSER:
					case DROPPER:
					case FURNACE:
					case HOPPER:
					case JUKEBOX:
					case NOTE_BLOCK:
					case TRAPPED_CHEST:
						if (!Stuff.canBeDestroyed(player, block)) {
							Message.sendMessage(player, "Cet endroit est protégé.");
							event.setUseInteractedBlock(Result.DENY);
							event.setCancelled(true);
						}
						break;
					default:
						break;
				}
			}
			
			break;
		}
	}
	
}
