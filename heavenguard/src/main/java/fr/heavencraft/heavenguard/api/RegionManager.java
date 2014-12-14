package fr.heavencraft.heavenguard.api;

import java.util.Collection;
import java.util.UUID;

import fr.heavencraft.exceptions.HeavenException;

public class RegionManager
{
	private RegionProvider regionProvider;

	public void addMember(String regionName, UUID player)
			throws HeavenException
	{
		regionProvider.getRegionByName(regionName).addMember(player, false);
	}

	public void canBuildAt(UUID player, String world, int x, int y, int z)
			throws HeavenException
	{
		Collection<Region> regions = regionProvider.getRegionsAtLocation(world,
				x, y, z);

	}
}