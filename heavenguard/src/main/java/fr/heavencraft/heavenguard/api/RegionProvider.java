package fr.heavencraft.heavenguard.api;

import java.util.Collection;

import fr.heavencraft.api.providers.Provider;
import fr.heavencraft.exceptions.HeavenException;

public interface RegionProvider extends Provider
{
	void createRegion(String name, String world, int minX, int minY, int minZ, int maxX, int maxY, int maxZ)
			throws HeavenException;

	Region getRegionByName(String name) throws HeavenException;

	Collection<Region> getRegionsAtLocation(String world, int x, int y, int z) throws HeavenException;
}