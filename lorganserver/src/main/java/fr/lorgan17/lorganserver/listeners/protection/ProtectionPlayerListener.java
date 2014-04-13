package fr.lorgan17.lorganserver.listeners.protection;

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
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import fr.lorgan17.lorganserver.LorganServer;
import fr.lorgan17.lorganserver.entities.Region;
import fr.lorgan17.lorganserver.exceptions.LorganException;

public class ProtectionPlayerListener implements Listener {
	
	public ProtectionPlayerListener(LorganServer plugin)
	{
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}
	
	
	@EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
	public void onPlayerBedEnter(PlayerBedEnterEvent event)
	{
		Player player = event.getPlayer();
		Block block = event.getBed();
		
		if (!LorganServer.canBeDestroyed(player, block))
		{
			LorganServer.sendMessage(player, "Tu vas pas dormir dans le lit d'un autre !? Naméoh !");
			event.setCancelled(true);
		}
	}

	@EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
	public void onPlayerBucketFill(PlayerBucketFillEvent event)
	{
		Player player = event.getPlayer();
		Block block = event.getBlockClicked().getRelative(event.getBlockFace());
		
		if (!LorganServer.canBeDestroyed(player, block))
		{
			LorganServer.sendMessage(player, "Cet endroit est protégé.");
			event.setCancelled(true);
		}
	}
	
	@EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
	public void onPlayerBucketEmpty(PlayerBucketEmptyEvent event)
	{
		Player player = event.getPlayer();
		Block block = event.getBlockClicked().getRelative(event.getBlockFace());
		
		if (!LorganServer.canBeDestroyed(player, block))
		{
			LorganServer.sendMessage(player, "Cet endroit est protégé.");
			event.setCancelled(true);
		}
	}

	@EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
	public void onPlayerInteractEntity(PlayerInteractEntityEvent event)
	{
		Player player = event.getPlayer();
		Block block = event.getRightClicked().getLocation().getBlock();

		if (!LorganServer.canBeDestroyed(player, block))
		{
			LorganServer.sendMessage(player, "Cet endroit est protégé.");
			event.setCancelled(true);
		}
	}

	
	@EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
	private void onPlayerInteract(PlayerInteractEvent event) throws LorganException
	{
		switch (event.getAction())
		{
			case RIGHT_CLICK_BLOCK:
				onPlayerRightClickBlock(event);		
				break;
			case PHYSICAL:
				onPlayerPhysical(event);
				break;
			default:
				break;
		}
	}
	
	private void onPlayerRightClickBlock(PlayerInteractEvent event)
	{
		Block block = event.getClickedBlock();
		ItemStack item = event.getItem();
		Player player = event.getPlayer();
		
		if (item != null && item.getType() == Material.ARROW)
		{
			Region region = Region.getRegionByLocation(block.getX(), block.getZ());
			
			if (region == null)
				LorganServer.sendMessage(player, "Aucune protection.");
			else
				LorganServer.sendMessage(player, "Protection : " + region.getId());

			event.setUseItemInHand(Result.DENY);
			event.setCancelled(true);
		}
		
		else if (item != null && (item.getType() == Material.MINECART || item.getType() == Material.BOAT))
		{
			if (!LorganServer.canBeDestroyed(player, block))
			{
				LorganServer.sendMessage(player, "Cet endroit est protégé.");
				event.setUseItemInHand(Result.DENY);
				event.setCancelled(true);
			}
		}
		
		else
		{
			switch (block.getType())
			{
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
					if (!LorganServer.canBeDestroyed(player, block))
					{
						LorganServer.sendMessage(player, "Cet endroit est protégé.");
						event.setUseInteractedBlock(Result.DENY);
						event.setCancelled(true);
					}
					break;
				default:
					break;
			}
		}

	}
	
	private void onPlayerPhysical(PlayerInteractEvent event)
	{
        Block block = event.getClickedBlock();
        Player player = event.getPlayer();
        
        switch (block.getType())
        {
	        case SOIL:
	        case PUMPKIN_STEM:
	        case MELON_STEM:
				if (!LorganServer.canBeDestroyed(player, block))
				{
					LorganServer.sendMessage(player, "Cette plantation est protégée.");
					event.setUseInteractedBlock(Result.DENY);
	                event.setCancelled(true);
	            }
				break;
			default:
				break;
        }
	}
}
