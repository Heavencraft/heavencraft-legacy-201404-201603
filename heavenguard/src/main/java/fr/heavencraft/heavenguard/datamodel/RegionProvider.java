package fr.heavencraft.heavenguard.datamodel;

import java.util.Collection;

import fr.heavencraft.exceptions.HeavenException;

public interface RegionProvider
{
	void createRegion(String name, String world, int minX, int minY, int minZ, int maxX, int maxY, int maxZ);

	Region getRegionByName(String name) throws HeavenException;

	Collection<Region> getRegionsAtLocation(String world, int x, int y, int z) throws HeavenException;

	void clearCache();
}