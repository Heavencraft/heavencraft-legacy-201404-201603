package fr.heavencraft.NavalConflicts.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.event.Event.Result;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.enchantment.PrepareItemEnchantEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import fr.heavencraft.NavalConflicts.Game;
import fr.heavencraft.NavalConflicts.NavalConflicts;
import fr.heavencraft.NavalConflicts.GameMechanics.Settings;
import fr.heavencraft.NavalConflicts.Handlers.Lobby;
import fr.heavencraft.NavalConflicts.Handlers.Lobby.GameState;
import fr.heavencraft.NavalConflicts.Handlers.LocationHandler;
import fr.heavencraft.NavalConflicts.Handlers.Player.NCPlayerManager;
import fr.heavencraft.NavalConflicts.Handlers.Player.Team;
import fr.heavencraft.NavalConflicts.Tools.Files;

public class PlayerListener implements Listener{
	public PlayerListener()
	{
		Bukkit.getPluginManager().registerEvents(this, NavalConflicts.getInstance());
	}
	
	
	
	// If a player attempts to drop an item in game
		@EventHandler(priority = EventPriority.NORMAL)
		public void onPlayerDropItem(PlayerDropItemEvent e) {
			if (Lobby.isInGame(e.getPlayer()))
				if (Lobby.getActiveArena().getSettings().droppingItemsDisabled())
					e.setCancelled(true);
		}
	
		
		@SuppressWarnings("deprecation")
		@EventHandler(priority = EventPriority.NORMAL)
		public void onPlayerBreakBlock(BlockBreakEvent e) {
			if (!e.isCancelled())
			{
				// Remove any signs if it is one
				if (Files.getSigns().getStringList("Info Signs").contains(LocationHandler.getLocationToString(e.getBlock().getLocation())))
				{
					Files.getSigns().getStringList("Info Signs").remove(LocationHandler.getLocationToString(e.getBlock().getLocation()));
					Files.saveSigns();
				}

				if (Files.getSigns().getStringList("Shop Signs").contains(LocationHandler.getLocationToString(e.getBlock().getLocation())))
				{
					Files.getSigns().getStringList("Shop Signs").remove(LocationHandler.getLocationToString(e.getBlock().getLocation()));
					Files.saveSigns();
				}
				// When players break blocks, before we do anything, are they in the
				// started game?
				else if (Lobby.isInGame(e.getPlayer()))
				{
					if (Lobby.getGameState() == GameState.Started)
					{
						// Does the config say they can break it?
						if (Lobby.getActiveArena().getBlocks().containsKey(e.getBlock().getLocation()) || Lobby.getActiveArena().getSettings().canBreakBlock(NCPlayerManager.getNCPlayer(e.getPlayer()).getTeam(), e.getBlock().getTypeId()))
						{
							Location loc = e.getBlock().getLocation();
							// Lets make sure this block wasn't a placed one, if so
							// we'll just remove it
							if (!Lobby.getActiveArena().getBlocks().containsKey(loc))
								Lobby.getActiveArena().setBlock(loc, e.getBlock().getType());
							else
								Lobby.getActiveArena().removeBlock(loc);
							e.getBlock().setType(Material.AIR);
						} else
							e.setCancelled(true);
					} else
						e.setCancelled(true);
				}
			}

		}
		
		// When a player interacts with an object well the game is started
		@EventHandler(priority = EventPriority.NORMAL)
		public void onPlayerInteract(PlayerInteractEvent e) {
			if (!e.isCancelled())
				// If they are playing and the game has started
				if (Lobby.isInGame(e.getPlayer()))
				{
					if (e.getClickedBlock().getRelative(e.getBlockFace()).getType() == Material.FIRE && !Lobby.getActiveArena().getSettings().canBreakBlock(NCPlayerManager.getNCPlayer(e.getPlayer()).getTeam(), 51))
						e.setCancelled(true);

					if (Lobby.getGameState() == GameState.Started)
					{

						if (!Lobby.getActiveArena().getSettings().interactDisabled())
						{

							if (e.getAction() == Action.RIGHT_CLICK_BLOCK)
							{
								Block b = e.getClickedBlock();
								// Make sure the chest isn't already saved, if
								// it isn't
								// save it
								if (e.getClickedBlock().getType() == Material.CHEST)
								{
									Chest chest = (Chest) b.getState();
									if (Lobby.getActiveArena().getChests().containsKey(chest.getLocation()))
										Lobby.getActiveArena().setChest(chest.getLocation(), chest.getBlockInventory());
								}
							}
						} else
						{
							e.setUseInteractedBlock(Result.DENY);
							e.setUseItemInHand(Result.DENY);
							e.setCancelled(true);

						}
					}
				}
		}
		
		

		// If a player is attempting to place a block
		@EventHandler(priority = EventPriority.NORMAL)
		public void onPlayerBlockPlace(BlockPlaceEvent e) {

			if (Lobby.isInGame(e.getPlayer()))
			{
				if (Lobby.getGameState() != GameState.Started)
					e.setCancelled(true);
				else
					Lobby.getActiveArena().setBlock(e.getBlock().getLocation(), Material.AIR);
			}
		}

		// If an arrow show in game hits the ground
		@EventHandler(priority = EventPriority.NORMAL)
		public void onArrowShoot(final ProjectileHitEvent e) {

			if (e.getEntity().getShooter() instanceof Player)
			{
				Player player = (Player) e.getEntity().getShooter();
				if (Lobby.isInGame(player) && Lobby.getGameState() == GameState.Started)
				{
					Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(NavalConflicts.getInstance(), new Runnable()
					{

						@Override
						public void run() {
							if (e.getEntity() != null)
								e.getEntity().remove();
						}
					}, 1);
				}
			}
		}

		// If the player gets kicked out of the server, do we need to remove them?
		@EventHandler(priority = EventPriority.MONITOR)
		public void onPlayerKick(final PlayerKickEvent e) {
			if (Lobby.isInGame(e.getPlayer()))
				Game.leaveGame(e.getPlayer());
		}

		// If the player quits on the server, do we need to remove them?
		@EventHandler(priority = EventPriority.MONITOR)
		public void onPlayerQuit(final PlayerQuitEvent e) {
			if (Lobby.isInGame(e.getPlayer()))
				Game.leaveGame(e.getPlayer());
		}

		
		// Stop "Living Entities" from targetting the players in Infected if its
		// enabled and the player isn't human
		@EventHandler(priority = EventPriority.NORMAL)
		public void onEntityAtkPlayerLivingEntity(EntityTargetLivingEntityEvent e) {
			if (e.getTarget() instanceof Player)
			{
				Player p = (Player) e.getTarget();
				if (Lobby.isInGame(p))
				{
					e.setCancelled(true);
					if (Lobby.getActiveArena().getSettings().hostileMobsTargetHumans() && Lobby.getPlayersTeam(p) == Team.Blue && Lobby.getGameState() == GameState.Started)
						e.setCancelled(false);
				}
			}
		}
		
		// When a Entity targets another, block it if it's in a game
		@EventHandler(priority = EventPriority.NORMAL)
		public void onEntityAtk(EntityTargetEvent e) {
			if (e.getTarget() instanceof Player)
			{
				Player p = (Player) e.getTarget();
				if (Lobby.isInGame(p))
				{
					e.setCancelled(true);
					if (Lobby.getActiveArena().getSettings().hostileMobsTargetHumans() && Lobby.getPlayersTeam(p) == Team.Blue && Lobby.getGameState() == GameState.Started)
						e.setCancelled(false);
				}
			}
		}

		// Should we allow for food levels to drop
		@EventHandler(priority = EventPriority.LOW)
		public void onPlayerHunger(FoodLevelChangeEvent e) {
			if (!e.isCancelled())
			{
				Player p = (Player) e.getEntity();
				if (Lobby.isInGame(p) && Lobby.getActiveArena().getSettings().hungerDisabled())
					e.setCancelled(true);
			}
		}

		// Block enchantment tables as levels are used as a timer
		@EventHandler(priority = EventPriority.NORMAL)
		public void PlayerTryEnchant(PrepareItemEnchantEvent e) {
			Player p = e.getEnchanter();
			if (Lobby.isInGame(p) && Lobby.getActiveArena().getSettings().enchantDisabled())
				e.setCancelled(true);
		}

		// Block enchantment tables as levels are used as a timer
		@EventHandler(priority = EventPriority.NORMAL)
		public void InventoryClick(InventoryClickEvent e) {
			Player p = (Player) e.getWhoClicked();
			if (Lobby.isInGame(p) && Settings.isEditingInventoryPrevented())
				e.setCancelled(true);
		}

		// If a player shoots a bow, before the game has started, lets return the
		// arrow and cancel the shot
		@SuppressWarnings("deprecation")
		@EventHandler(priority = EventPriority.NORMAL)
		public void onPlayerShootBow(EntityShootBowEvent e) {
			if (!e.isCancelled() && Lobby.getGameState() != GameState.Started)
				if (e.getEntity() instanceof Player)
				{
					Player player = (Player) e.getEntity();
					if (Lobby.isInGame(player))
					{
						e.getProjectile().remove();
						e.setCancelled(true);
						player.updateInventory();
					}
				}
		}

		// Prevent throwing potions well in the lobby of Infected, as it just gets
		// annoying
		@EventHandler(priority = EventPriority.NORMAL)
		public void onPlayerThrowPotion(PlayerInteractEvent e) {
			if (!e.isCancelled())
				if (Lobby.isInGame(e.getPlayer()) && Lobby.getGameState() != GameState.Started)
				{
					if (e.getPlayer().getItemInHand().getType() == Material.POTION)
					{
						e.setUseItemInHand(Result.DENY);
						e.setCancelled(true);
					}
				}
		}
		
		
		
	
}
