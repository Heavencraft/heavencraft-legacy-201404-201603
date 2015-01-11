package fr.heavencraft.heavenguard.bukkit.listeners;

import java.util.Collection;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.hanging.HangingBreakByEntityEvent;
import org.bukkit.event.hanging.HangingPlaceEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerBucketFillEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.vehicle.VehicleDamageEvent;
import org.bukkit.inventory.InventoryHolder;

import com.google.common.collect.Sets;

import fr.heavencraft.common.logs.HeavenLog;
import fr.heavencraft.heavenguard.bukkit.HeavenGuard;
import fr.heavencraft.utils.ChatUtil;
import fr.heavencraft.utils.DevUtil;

public class ProtectionPlayerListener implements Listener
{
	private static final HeavenLog log = HeavenLog.getLogger(ProtectionPlayerListener.class);

	private static final Collection<Material> VEHICULES = Sets.newHashSet(Material.BOAT, Material.MINECART,
			Material.STORAGE_MINECART, Material.POWERED_MINECART, Material.EXPLOSIVE_MINECART, Material.HOPPER_MINECART,
			Material.COMMAND_MINECART);

	InventoryHolder holder;

	private static final Collection<Material> INTERACT_BLOCKS = Sets.newHashSet(Material.ANVIL, Material.BEACON,
			Material.BREWING_STAND, Material.BURNING_FURNACE, Material.CAKE_BLOCK, Material.CHEST, Material.DISPENSER,
			Material.DROPPER, Material.FURNACE, Material.HOPPER, Material.JUKEBOX, Material.NOTE_BLOCK, Material.TRAPPED_CHEST);

	public ProtectionPlayerListener()
	{
		DevUtil.registerListener(this);
	}

	/*
	 * BlockEvent
	 */

	@EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
	private void onBlockBreak(BlockBreakEvent event)
	{
		log.info(event.getClass().getSimpleName());

		if (!canBuildAt(event.getPlayer(), event.getBlock()))
			event.setCancelled(true);
	}

	@EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
	private void onBlockDamage(BlockDamageEvent event)
	{
		log.info(event.getClass().getSimpleName());

		if (!canBuildAt(event.getPlayer(), event.getBlock()))
			event.setCancelled(true);
	}

	@EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
	private void onBlockIgnite(BlockIgniteEvent event)
	{
		log.info(event.getClass().getSimpleName());

		if (event.getPlayer() == null)
			return;

		if (!canBuildAt(event.getPlayer(), event.getBlock()))
			event.setCancelled(true);
	}

	@EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
	private void onBlockPlace(BlockPlaceEvent event)
	{
		log.info(event.getClass().getSimpleName());

		if (!canBuildAt(event.getPlayer(), event.getBlock()))
			event.setCancelled(true);
	}

	@EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
	private void onSignChange(SignChangeEvent event)
	{
		log.info(event.getClass().getSimpleName());

		if (!canBuildAt(event.getPlayer(), event.getBlock()))
			event.setCancelled(true);
	}

	/*
	 * EntityEvent
	 */

	@EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
	private void onEntityDamageByEntity(EntityDamageByEntityEvent event)
	{
		log.info(event.getClass().getSimpleName());

		Entity damager = event.getDamager();
		Player player;

		if (damager instanceof Player)
			player = (Player) damager;
		else if (damager instanceof Projectile && ((Projectile) damager).getShooter() instanceof Player)
			player = (Player) ((Projectile) damager).getShooter();
		else
			return;

		Entity defender = event.getEntity();
		Block block = defender.getLocation().getBlock();

		if (!canBuildAt(player, block))
			event.setCancelled(true);
	}

	@EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
	private void onHangingPlace(HangingPlaceEvent event)
	{
		log.info(event.getClass().getSimpleName());

		if (!canBuildAt(event.getPlayer(), event.getBlock()))
			event.setCancelled(true);
	}

	@EventHandler(priority = EventPriority.LOWEST)
	private void onHangingBreakByEntity(HangingBreakByEntityEvent event)
	{
		log.info(event.getClass().getSimpleName());

		Player player = event.getRemover() instanceof Player ? (Player) event.getRemover() : null;
		Block block = event.getEntity().getLocation().getBlock();

		if (!canBuildAt(player, block))
			event.setCancelled(true);
	}

	/*
	 * PlayerEvent
	 */

	@EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
	private void onPlayerBucketFill(PlayerBucketFillEvent event)
	{
		log.info(event.getClass().getSimpleName());

		Block block = event.getBlockClicked().getRelative(event.getBlockFace());

		if (!canBuildAt(event.getPlayer(), block))
			event.setCancelled(true);
	}

	@EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
	private void onPlayerBucketEmpty(PlayerBucketEmptyEvent event)
	{
		log.info(event.getClass().getSimpleName());

		Block block = event.getBlockClicked().getRelative(event.getBlockFace());

		if (!canBuildAt(event.getPlayer(), block))
			event.setCancelled(true);
	}

	@EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
	private void on(PlayerBedEnterEvent event)
	{
		log.info(event.getClass().getSimpleName());

		if (!canBuildAt(event.getPlayer(), event.getBed()))
			event.setCancelled(true);
	}

	@EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
	private void onPlayerInteractEntity(PlayerInteractEntityEvent event)
	{
		log.info(event.getClass().getSimpleName() + " " + event.getEventName());

		if (!canBuildAt(event.getPlayer(), event.getRightClicked().getLocation().getBlock()))
			event.setCancelled(true);
	}

	@EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
	private void on(PlayerInteractEvent event)
	{
		log.info(event.getClass().getSimpleName());

		switch (event.getAction())
		{
			case RIGHT_CLICK_BLOCK:
				onPlayerRightClickBlock(event);
				break;

			default:
				break;
		}
	}

	private void onPlayerRightClickBlock(PlayerInteractEvent event)
	{
		Block clickedBlock = event.getClickedBlock();

		// Player can't place a Vehicule
		if (event.hasItem() && VEHICULES.contains(event.getItem().getType()))
		{
			if (!canBuildAt(event.getPlayer(), clickedBlock))
				event.setCancelled(true);
		}

		// Player can't right-click
		else if (INTERACT_BLOCKS.contains(clickedBlock.getType()))
		{
			if (!canBuildAt(event.getPlayer(), clickedBlock))
				event.setCancelled(true);
		}
	}

	// @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
	// private void onPlayerInteract(PlayerInteractEvent event)
	// {
	// try
	// {
	// switch (event.getAction())
	// {
	// case RIGHT_CLICK_BLOCK:
	// onPlayerRightClickBlock(event);
	// break;
	// case PHYSICAL:
	// onPlayerPhysical(event);
	// break;
	// default:
	// break;
	// }
	// }
	// catch (HeavenException ex)
	// {
	// ex.printStackTrace();
	// ChatUtil.sendMessage(event.getPlayer(), ex.getMessage());
	// }
	// }

	// @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
	// private void onPlayerRightClickBlock(PlayerInteractEvent event) throws
	// SQLErrorException
	// {
	// Block block = event.getClickedBlock();
	//
	// switch (block.getType())
	// {
	// case ANVIL:
	// case BREWING_STAND:
	// case BURNING_FURNACE:
	// case CAKE_BLOCK:
	// case CHEST:
	// case DISPENSER:
	// case DROPPER:
	// case FURNACE:
	// case HOPPER:
	// case JUKEBOX:
	// case NOTE_BLOCK:
	// case TRAPPED_CHEST:
	// if (!canBuildAt(event.getPlayer(), block))
	// event.setCancelled(true);
	// break;
	// default:
	// break;
	// }
	//
	// }

	// @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
	// private void onPlayerPhysical(PlayerInteractEvent event)
	// {
	// Block block = event.getClickedBlock();
	//
	// switch (block.getType())
	// {
	// case SOIL:
	// case PUMPKIN_STEM:
	// case MELON_STEM:
	// if (!canBuildAt(event.getPlayer(), block))
	// event.setCancelled(true);
	// break;
	// default:
	// break;
	// }
	// }

	/*
	 * InventoryEvent
	 */

	@EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
	private void onInventoryOpen(InventoryOpenEvent event)
	{
		InventoryType type = event.getInventory().getType();

		switch (type)
		{
			case ANVIL:
			case BEACON:
			case BREWING:
			case CHEST:
			case CRAFTING:
			case CREATIVE:
			case DISPENSER:
			case DROPPER:
			case ENCHANTING:
			case ENDER_CHEST:
			case FURNACE:
			case HOPPER:
			case MERCHANT:
			case PLAYER:
			case WORKBENCH:
				break;

			default:
				break;
		}
	}

	/*
	 * VehiculeEvent
	 */

	@EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
	private void onVehicleDamage(VehicleDamageEvent event)
	{
		log.info(event.getClass().getSimpleName());

		if (event.getAttacker().getType() != EntityType.PLAYER)
			return;

		if (!canBuildAt((Player) event.getAttacker(), event.getVehicle().getLocation().getBlock()))
			event.setCancelled(true);
	}

	private static boolean canBuildAt(Player player, Block block)
	{
		boolean result = HeavenGuard.getRegionManager().canBuildAt(player.getUniqueId(), //
				block.getWorld().getName(), block.getX(), block.getY(), block.getZ());

		if (!result)
			ChatUtil.sendMessage(player, "Cet endroit est protégé.");

		return result;
	}
}