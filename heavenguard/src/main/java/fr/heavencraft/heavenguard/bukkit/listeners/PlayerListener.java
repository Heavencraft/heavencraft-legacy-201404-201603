package fr.heavencraft.heavenguard.bukkit.listeners;

import java.util.Collection;
import java.util.Iterator;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import fr.heavencraft.exceptions.HeavenException;
import fr.heavencraft.heavenguard.api.Region;
import fr.heavencraft.heavenguard.bukkit.HeavenGuard;
import fr.heavencraft.utils.ChatUtil;
import fr.heavencraft.utils.DevUtil;

public class PlayerListener implements Listener
{
	public PlayerListener()
	{
		DevUtil.registerListener(this);
	}

	@EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
	private void onPlayerInteract(PlayerInteractEvent event)
	{
		if (event.getAction() != Action.RIGHT_CLICK_BLOCK)
			return;

		if (event.getMaterial() != Material.ARROW)
			return;

		displayRegionAt(event.getPlayer(), event.getClickedBlock().getLocation());
	}

	public void displayRegionAt(Player player, Location location)
	{
		try
		{
			Collection<Region> regions = HeavenGuard.getRegionProvider().getRegionsAtLocation(location.getWorld().getName(),
					location.getBlockX(), location.getBlockY(), location.getBlockZ());

			// player.sendMessage(ChatColor.YELLOW + "Pouvez-vous construire ? "
			// + (set.canBuild(localPlayer) ? "Oui" : "Non"));

			if (regions.isEmpty())
			{
				ChatUtil.sendMessage(player, "Il n'y a aucune protection ici.");
				return;
			}

			StringBuilder str = new StringBuilder();
			for (Iterator<Region> it = regions.iterator(); it.hasNext();)
			{
				str.append(it.next().getName());

				if (it.hasNext())
					str.append(", ");
			}

			ChatUtil.sendMessage(player, "Liste des protections actives ici : " + str.toString());
		}
		catch (HeavenException e)
		{
			e.printStackTrace();
		}
	}
}