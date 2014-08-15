package fr.tenkei.creaplugin.listeners.protection;

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
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.Sign;

import fr.heavencraft.utils.ChatUtil;
import fr.heavencraft.utils.DevUtil;
import fr.tenkei.creaplugin.MyPlugin;
import fr.tenkei.creaplugin.utils.Stuff;

public class ProtectionBlockListener implements Listener
{
	public ProtectionBlockListener(MyPlugin plugin)
	{
		DevUtil.registerListener(this);
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void onBlockBreak(BlockBreakEvent event)
	{
		Player player = event.getPlayer();
		Block block = event.getBlock();

		if (!Stuff.canBeDestroyed(player, block))
			event.setCancelled(true);
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void onBlockBurn(BlockBurnEvent event)
	{
		event.setCancelled(true);
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void onBlockDamage(BlockDamageEvent event)
	{
		Player player = event.getPlayer();
		Block block = event.getBlock();

		if (Stuff.canBeDestroyed(player, block))
			return;

		if (!Stuff.canBeDestroyed(player, block))
			event.setCancelled(true);

	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void onBlockIgnite(BlockIgniteEvent event)
	{
		Player player = event.getPlayer();
		Block block = event.getBlock();

		if (player == null)
		{
			event.setCancelled(true);
			return;
		}

		if (!Stuff.canBeDestroyed(player, block))
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

		if (block.getType() == Material.WALL_SIGN)
		{
			Sign sign = (Sign) block.getState();

			if (block.getRelative(sign.getAttachedFace()).getType() == Material.WOOD_DOUBLE_STEP)
				return; // On laisse poser
		}

		if (!Stuff.canBeDestroyed(player, block))
		{
			ChatUtil.sendMessage(player, "Cet endroit est protégé.");
			event.setCancelled(true);
		}

		if (block.getType() == Material.SKULL || block.getType() == Material.SKULL_ITEM)
		{
			// player.getEquipment().setItemInHand(new ItemStack(Material.AIR));
			ItemStack itemInHand = player.getEquipment().getItemInHand();
			itemInHand.setAmount(itemInHand.getAmount() - 1);
			player.getEquipment().setItemInHand(itemInHand);
			return;
		}
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void onSignChange(SignChangeEvent event)
	{
		Player player = event.getPlayer();
		Block block = event.getBlock();

		if (block.getType() == Material.WALL_SIGN)
		{
			Sign sign = (Sign) block.getState();

			if (block.getRelative(sign.getAttachedFace()).getType() == Material.WOOD_DOUBLE_STEP)
				return; // On laisse poser
		}

		if (!Stuff.canBeDestroyed(player, block))
		{
			ChatUtil.sendMessage(player, "Cet endroit est protégé.");
			event.setCancelled(true);
		}
	}
}
