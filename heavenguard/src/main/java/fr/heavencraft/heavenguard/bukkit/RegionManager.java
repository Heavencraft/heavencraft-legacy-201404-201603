package fr.heavencraft.heavenguard.bukkit;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.sk89q.worldedit.bukkit.selections.Selection;

import fr.heavencraft.exceptions.HeavenException;
import fr.heavencraft.heavenguard.api.RegionProvider;
import fr.heavencraft.utils.ChatUtil;
import fr.heavencraft.utils.DevUtil;

public class RegionManager
{
	private final RegionProvider regionProvider;

	public RegionManager(RegionProvider regionProvider)
	{
		this.regionProvider = regionProvider;
	}

	public void redefineRegion(Player player, String name) throws HeavenException
	{
		Selection selection = DevUtil.getWESelection(player);
		Location min = selection.getMinimumPoint();
		Location max = selection.getMaximumPoint();

		regionProvider.getRegionByName(name).redefine(selection.getWorld().getName(), min.getBlockX(), min.getBlockY(),
				min.getBlockZ(), max.getBlockX(), max.getBlockY(), max.getBlockZ());

		ChatUtil.sendMessage(player, "La région {%1$s} a bien été redéfinie.", name);
	}

}