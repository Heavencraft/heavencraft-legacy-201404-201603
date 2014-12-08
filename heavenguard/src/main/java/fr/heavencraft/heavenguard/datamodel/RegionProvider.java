package fr.heavencraft.heavenguard.datamodel;

import java.util.Collection;

import fr.heavencraft.exceptions.HeavenException;

public interface RegionProvider
{
	void createRegion(String name, String world, int x1, int y1, int z1, int x2, int y2, int z2);

	Region getRegionByName(String name) throws HeavenException;

	Collection<Region> getRegionsAtLocation(String world, int x, int y, int z) throws HeavenException;

	void clearCache();
}