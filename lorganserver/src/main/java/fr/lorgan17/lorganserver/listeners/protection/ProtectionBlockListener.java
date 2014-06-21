package fr.lorgan17.lorganserver.listeners.protection;

import static fr.heavencraft.utils.DevUtil.registerListener;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockBurnEvent;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.SignChangeEvent;

import fr.heavencraft.utils.ChatUtil;
import fr.lorgan17.lorganserver.LorganServer;

public class ProtectionBlockListener implements Listener
{
	public ProtectionBlockListener()
	{
		registerListener(this);
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void onBlockBreak(BlockBreakEvent event)
	{
		Player player = event.getPlayer();
		Block block = event.getBlock();

		if (block.getType() == Material.MOB_SPAWNER)
		{
			ChatUtil.sendMessage(player, "Les {mob spawner} sont incassable.");
			event.setCancelled(true);
		}

		else if (!LorganServer.canBeDestroyed(player, block))
		{
			ChatUtil.sendMessage(player, "Cet endroit est protégé.");
			event.setCancelled(true);
		}
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void onBlockBurn(BlockBurnEvent event)
	{
		Block block = event.getBlock();

		if (!LorganServer.canBeDestroyed(null, block))
			event.setCancelled(true);
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void onBlockDamage(BlockDamageEvent event)
	{
		Player player = event.getPlayer();
		Block block = event.getBlock();

		if (!LorganServer.canBeDestroyed(player, block))
		{
			ChatUtil.sendMessage(player, "Cet endroit est protégé.");
			event.setCancelled(true);
		}
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void onBlockIgnite(BlockIgniteEvent event)
	{
		Player player = event.getPlayer();
		Block block = event.getBlock();

		if (!LorganServer.canBeDestroyed(player, block))
		{
			ChatUtil.sendMessage(player, "Cet endroit est protégé.");
			event.setCancelled(true);
		}
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void onBlockPlace(BlockPlaceEvent event)
	{
		Player player = event.getPlayer();
		Block block = event.getBlockPlaced();

		if (!LorganServer.canBeDestroyed(player, block))
		{
			ChatUtil.sendMessage(player, "Cet endroit est protégé.");
			event.setCancelled(true);
		}
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void onSignChange(SignChangeEvent event)
	{
		Player player = event.getPlayer();
		Block block = event.getBlock();

		if (!LorganServer.canBeDestroyed(player, block))
		{
			ChatUtil.sendMessage(player, "Cet endroit est protégé.");
			event.setCancelled(true);
		}
	}
}
