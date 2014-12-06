package fr.lorgan17.lorganserver.listeners.protection;

import static fr.heavencraft.utils.DevUtil.registerListener;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.block.Block;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.EntityInteractEvent;

import fr.heavencraft.utils.ChatUtil;
import fr.lorgan17.lorganserver.LorganServer;

public class ProtectionEntityListener implements Listener
{
	public ProtectionEntityListener()
	{
		registerListener(this);
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void onEntityChangeBlock(EntityChangeBlockEvent event)
	{
		// BUGFIX : Pour faire tomber le sable, le gravier, etc...
		if (event.getEntityType() == EntityType.FALLING_BLOCK)
			return;

		// BUGFIX : Pour que les moutons puissent se nourrir
		if (event.getEntityType() == EntityType.SHEEP)
			return;

		Block block = event.getBlock();

		if (!LorganServer.canBeDestroyed(null, block))
			event.setCancelled(true);
	}

	@EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
	private void onEntityInteract(EntityInteractEvent event)
	{
		if (event.getEntityType() == EntityType.PLAYER)
			return;

		Block block = event.getBlock();

		if (!LorganServer.canBeDestroyed(null, block))
		{
			event.setCancelled(true);
		}
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void onEntityDamageByEntity(EntityDamageByEntityEvent event)
	{
		Entity damager = event.getDamager();
		Player player;

		if (damager instanceof Player)
			player = (Player) damager;
		else if (damager instanceof Arrow && ((Arrow) damager).getShooter() instanceof Player)
			player = (Player) ((Arrow) damager).getShooter();
		else
			return;

		Entity defender = event.getEntity();

		Block block = defender.getLocation().getBlock();

		switch (event.getEntityType())
		{
			case CHICKEN:
			case COW:
			case HORSE:
			case MUSHROOM_COW:
			case OCELOT:
			case PIG:
			case SHEEP:
			case SNOWMAN:
			case VILLAGER:
			case WOLF:
			case BOAT:
			case MINECART:
			case MINECART_CHEST:
			case MINECART_FURNACE:
			case MINECART_HOPPER:
			case MINECART_MOB_SPAWNER:
			case MINECART_TNT:
			case ITEM_FRAME:
			case ARMOR_STAND:
				if (!LorganServer.canBeDestroyed(player, block))
				{
					ChatUtil.sendMessage(player, "Cet endroit est protégé.");
					event.setCancelled(true);
				}
				break;
			default:
				return;
		}
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void onEntityExplode(EntityExplodeEvent event)
	{
		List<Block> toRemove = new ArrayList<Block>();

		for (Block block : event.blockList())
		{
			if (!LorganServer.canBeDestroyed(null, block))
			{
				toRemove.add(block);
			}
		}

		for (Block block : toRemove)
			event.blockList().remove(block);
	}
}
