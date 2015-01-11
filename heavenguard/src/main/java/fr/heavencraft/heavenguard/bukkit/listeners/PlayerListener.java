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
		String world = location.getWorld().getName();
		int x = location.getBlockX();
		int y = location.getBlockY();
		int z = location.getBlockZ();

		Collection<Region> regions = HeavenGuard.getRegionProvider().getRegionsAtLocation(world, x, y, z);

		if (regions.isEmpty())
		{
			ChatUtil.sendMessage(player, "Il n'y a aucune protection ici.");
		}

		else
		{
			StringBuilder str = new StringBuilder("Liste des protections actives ici : ");

			for (Iterator<Region> it = regions.iterator(); it.hasNext();)
			{
				str.append(it.next().getName());

				if (it.hasNext())
					str.append(", ");
			}

			ChatUtil.sendMessage(player, str.toString());
		}

		StringBuilder canYouBuild = new StringBuilder("Pouvez-vous construire ? ");
		canYouBuild.append(HeavenGuard.getRegionManager().canBuildAt(player.getUniqueId(), world, x, y, z) ? "Oui." : "Non.");
		ChatUtil.sendMessage(player, canYouBuild.toString());

		StringBuilder pvpEnabled = new StringBuilder("PVP activé ? ");
		pvpEnabled.append(HeavenGuard.getRegionManager().isPvp(world, x, y, z) ? "Oui." : "Non.");
		ChatUtil.sendMessage(player, pvpEnabled.toString());
	}
}