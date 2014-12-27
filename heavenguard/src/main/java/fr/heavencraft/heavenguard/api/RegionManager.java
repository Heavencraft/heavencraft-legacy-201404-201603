package fr.heavencraft.heavenguard.api;

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
		Collection<Region> regions = regionProvider.getRegionsAtLocation(world, x, y, z);

		// TODO : remove parents from regions

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
}