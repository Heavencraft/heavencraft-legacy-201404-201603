package fr.heavencraft.heavenguard.api;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

import fr.heavencraft.utils.DevUtil;

public class RegionManager
{
	private final RegionProvider regionProvider;

	public RegionManager(RegionProvider regionProvider)
	{
		this.regionProvider = regionProvider;
	}

	private Collection<Region> getRegionsAtLocationWithoutParents(String world, int x, int y, int z)
	{
		Collection<Region> regions = regionProvider.getRegionsAtLocation(world, x, y, z);

		if (regions.size() > 1)
		{
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
		}

		return regions;
	}

	private Collection<Region> getAllParents(Region region)
	{
		Region parent = region.getParent();

		if (parent == null)
			return new ArrayList<Region>();

		Collection<Region> parents = getAllParents(parent);
		parents.add(parent);
		return parents;
	}

	// TODO For debug purpose : remove me
	public boolean canBuildAt(UUID player, String world, int x, int y, int z)
	{
		long start = System.nanoTime();

		try
		{
			return canBuildAt2(player, world, x, y, z);
		}
		finally
		{
			DevUtil.logInfo("canBuildAt : %1$s us", (System.nanoTime() - start) / 1000.0);
		}
	}

	private boolean canBuildAt2(UUID player, String world, int x, int y, int z)
	{
		Collection<Region> regions = getRegionsAtLocationWithoutParents(world, x, y, z);

		// If there are regions here
		if (regions.size() != 0)
		{
			// If a player can't build in one region, it can't build here
			for (Region region : regions)
				if (!region.canBuilt(player))
					return false;

			return true;
		}

		// No regions here : the player can build if the world is public
		return regionProvider.getGlobalRegion(world).getBooleanFlag(Flag.PUBLIC);
	}

	public boolean isProtectedAgainstEnvironment(String world, int x, int y, int z)
	{
		Collection<Region> regions = getRegionsAtLocationWithoutParents(world, x, y, z);

		// If there are regions here
		if (regions.size() != 0)
			return true;

		// No regions here : the block is protected if the world is not public
		return !regionProvider.getGlobalRegion(world).getBooleanFlag(Flag.PUBLIC);
	}

	// TODO For debug purpose : remove me
	public boolean isPvp(String world, int x, int y, int z)
	{
		long start = System.nanoTime();

		try
		{
			return isPvp2(world, x, y, z);
		}
		finally
		{
			DevUtil.logInfo("isPvp : %1$s us", (System.nanoTime() - start) / 1000.0);
		}
	}

	private boolean isPvp2(String world, int x, int y, int z)
	{
		Collection<Region> regions = getRegionsAtLocationWithoutParents(world, x, y, z);

		// If there are regions here
		if (regions.size() != 0)
		{
			boolean pvpEnabled = false;
			boolean pvpDisabled = false;

			for (Region region : regions)
			{
				Boolean pvp = region.getBooleanFlag(Flag.PVP);

				if (pvp == null)
					continue;

				if (pvp)
					pvpEnabled = true;
				else
					pvpDisabled = true;
			}

			return !pvpDisabled && pvpEnabled;
		}

		// No regions here : this block is pvp if the world is pvp
		return regionProvider.getGlobalRegion(world).getBooleanFlag(Flag.PVP);
	}
}