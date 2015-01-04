package fr.heavencraft.heavenguard.api;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

import fr.heavencraft.exceptions.HeavenException;
import fr.heavencraft.utils.DevUtil;

public class RegionManager
{
	private final RegionProvider regionProvider;

	public RegionManager(RegionProvider regionProvider)
	{
		this.regionProvider = regionProvider;
	}

	public boolean canBuildAt(UUID player, String world, int x, int y, int z) throws HeavenException
	{
		long start = System.nanoTime();
		Collection<Region> regions = regionProvider.getRegionsAtLocation(world, x, y, z);
		DevUtil.logInfo("getRegionsAtLocation %1$s us", (System.nanoTime() - start) / 1000.0);

		start = System.nanoTime();
		Collection<Region> toRemove = new ArrayList<Region>();

		for (Region region : regions)
		{
			for (Region parent : getAllParents(region))
			{
				if (regions.contains(parent))
				{
					toRemove.add(parent);
				}
			}
		}

		regions.removeAll(toRemove);
		DevUtil.logInfo("Removing parents %1$s us", (System.nanoTime() - start) / 1000.0);

		for (Region region : regions)
		{
			if (!region.canBuilt(player))
			{
				DevUtil.logInfo("%1$s can't build in region %2$s.", player, region.getName());
				return false;
			}
		}

		DevUtil.logInfo("%1$s can build at %2$s [%3$s %4$s %5$s].", player, world, x, y, z);
		return true;
	}

	private Collection<Region> getAllParents(Region region)
	{
		Region parent = region.getParent();

		if (parent != null)
		{
			Collection<Region> parents = getAllParents(parent);
			parents.add(parent);
			return parents;
		}

		return new ArrayList<Region>();
	}
}