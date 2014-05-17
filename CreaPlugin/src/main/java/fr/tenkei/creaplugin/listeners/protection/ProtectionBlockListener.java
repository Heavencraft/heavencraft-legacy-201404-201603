package fr.tenkei.creaplugin.listeners.protection;

import net.minecraft.server.v1_7_R3.WorldServer;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.craftbukkit.v1_7_R3.CraftWorld;
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

import fr.tenkei.creaplugin.MyPlugin;
import fr.tenkei.creaplugin.utils.Message;
import fr.tenkei.creaplugin.utils.Stuff;

public class ProtectionBlockListener implements Listener
{

	public ProtectionBlockListener(MyPlugin plugin)
	{
		Bukkit.getPluginManager().registerEvents(this, plugin);
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
			Message.sendMessage(player, "Cet endroit est prot�g�.");
			event.setCancelled(true);
		}
	}

	@SuppressWarnings("deprecation")
	@EventHandler(priority = EventPriority.LOWEST)
	public void onBlockPlace(BlockPlaceEvent event)
	{
		Player player = event.getPlayer();
		Block block = event.getBlockPlaced();

		if (block.getTypeId() == 68)
			if (block.getRelative(BlockFace.NORTH).getTypeId() == 125
					|| block.getRelative(BlockFace.SOUTH).getTypeId() == 125
					|| block.getRelative(BlockFace.EAST).getTypeId() == 125
					|| block.getRelative(BlockFace.WEST).getTypeId() == 125)
				return; // On laisse poser

		if (!Stuff.canBeDestroyed(player, block))
		{
			Message.sendMessage(player, "Cet endroit est prot�g�.");
			event.setCancelled(true);
		}

		if (block.getTypeId() == 144 || block.getTypeId() == 397)
		{
			// player.getEquipment().setItemInHand(new ItemStack(Material.AIR));
			ItemStack itemInHand = player.getEquipment().getItemInHand();
			itemInHand.setAmount(itemInHand.getAmount() - 1);
			player.getEquipment().setItemInHand(itemInHand);
			return;
		}

		Block b = event.getBlock();

		if (b.getTypeId() != 123)
			return;

		WorldServer ws = ((CraftWorld) b.getWorld()).getHandle();

		boolean mem = ws.isStatic;
		if (!mem)
			ws.isStatic = true;

		b.setTypeIdAndData(Material.REDSTONE_LAMP_ON.getId(), (byte) 0, false);

		if (!mem)
			ws.isStatic = false;
	}

	@SuppressWarnings("deprecation")
	@EventHandler(priority = EventPriority.LOWEST)
	public void onSignChange(SignChangeEvent event)
	{
		Player player = event.getPlayer();
		Block block = event.getBlock();

		if (block.getType().getId() == 68)
			if (block.getRelative(BlockFace.NORTH).getTypeId() == 125
					|| block.getRelative(BlockFace.SOUTH).getTypeId() == 125
					|| block.getRelative(BlockFace.EAST).getTypeId() == 125
					|| block.getRelative(BlockFace.WEST).getTypeId() == 125)
				return;

		if (!Stuff.canBeDestroyed(player, block))
		{
			Message.sendMessage(player, "Cet endroit est prot�g�.");
			event.setCancelled(true);
		}
	}
}
