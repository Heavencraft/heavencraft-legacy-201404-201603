package fr.heavencraft.heavenrp.worlds;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPortalEnterEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import fr.heavencraft.Permissions;
import fr.heavencraft.utils.ChatUtil;
import fr.heavencraft.utils.DevUtil;
import fr.heavencraft.utils.PlayerUtil;

public class WorldsListener implements Listener
{
	public WorldsListener()
	{
		DevUtil.registerListener(this);
	}

	// Limites des mondes
	@EventHandler(ignoreCancelled = true)
	public void onPlayerMove(PlayerMoveEvent event)
	{
		final Location l = event.getTo();

		// Monde semi-RP
		if (l.getWorld().equals(WorldsManager.getWorld()))
		{
			if (Math.pow(l.getX(), 2) + Math.pow(l.getZ(), 2) > 25000000)
			{
				event.setTo(WorldsManager.getSpawn());
				ChatUtil.sendMessage(event.getPlayer(), "Vous avez atteint la limite du monde semi-RP.");
				// event.setCancelled(true);
			}
		}

		// Monde ressources
		else if (l.getWorld().equals(WorldsManager.getResources()))
		{
			final int limit = WorldsManager.RESOURCES_SIZE / 2;

			if (Math.abs(l.getX()) > limit || Math.abs(l.getZ()) > limit)
			{
				event.setTo(WorldsManager.getSpawn());
				ChatUtil.sendMessage(event.getPlayer(), "Vous avez atteint la limite du monde ressources.");
				// event.setCancelled(true);
			}
		}

		else if (l.getWorld().getName().equals("world_old") || l.getWorld().getName().equals("world_origine")
				|| l.getWorld().getName().equals("world_dungeon") || l.getWorld().getName().equals("world_event"))
		{
			if (!event.getPlayer().hasPermission(Permissions.TPWORLD))
				event.getPlayer().teleport(WorldsManager.getSpawn());
		}
	}

	// Passage dans un portail
	@EventHandler
	public void onEntityPortalEnter(EntityPortalEnterEvent event)
	{
		if (event.getEntityType() != EntityType.PLAYER)
			return;

		final Block block = event.getLocation().getBlock();

		if (block.getType() != Material.PORTAL)
			return;

		if (block.getWorld() != WorldsManager.getWorld())
			return;

		final Player player = (Player) event.getEntity();

		switch (block.getRelative(BlockFace.DOWN).getType())
		{
			case NETHERRACK:
				PlayerUtil.teleportPlayer(player, WorldsManager.getSpawnNether());
				break;

			case ENDER_STONE:
				PlayerUtil.teleportPlayer(player, WorldsManager.getSpawnTheEnd());
				break;

			case SAND:
				PlayerUtil.teleportPlayer(player, WorldsManager.getResourcesSpawn());
				break;

			default:
				break;
		}
	}
}