package fr.heavencraft.heavenguard.datamodel;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import fr.heavencraft.exceptions.HeavenException;
import fr.heavencraft.exceptions.SQLErrorException;
import fr.heavencraft.heavenguard.HeavenGuard;
import fr.heavencraft.heavenguard.exceptions.RegionNotFoundException;

public class SQLRegionProvider implements RegionProvider
{
	private static final String CREATE_REGION = "INSERT INTO regions (name, world, x1, y1, z1, x2, y2, z2) VALUES (?, ?, ?, ?, ?, ?, ?, ?);";
	private static final String GET_REGION_BY_NAME = "SELECT * FROM regions WHERE name = ? LIMIT 1";
	private static final String GET_REGIONS_AT_LOCATION = "SELECT * FROM regions WHERE world = ? AND ? BETWEEN min_x AND max_x AND ? BETWEEN y_min AND y_max AND ? BETWEEN z_min AND z_max";

	private final Map<String, Region> regionsByName = new ConcurrentHashMap<String, Region>();

	@Override
	public void clearCache()
	{
		regionsByName.clear();
	}

	@Override
	public void createRegion(String name, String world, int minX, int minY, int minZ, int maxX, int maxY, int maxZ)
	{

	}

	@Override
	public Region getRegionByName(String name) throws HeavenException
	{
		name = name.toLowerCase();

		// Search from cache
		Region region = regionsByName.get(name);

		if (region != null)
			return region;

		// Search from database
		try (PreparedStatement ps = HeavenGuard.getConnection().prepareStatement(GET_REGION_BY_NAME))
		{
			ps.setString(1, name);

			try (ResultSet rs = ps.executeQuery())
			{
				if (!rs.next())
					throw new RegionNotFoundException(name);

				region = new SQLRegion(rs);
				regionsByName.put(name, region);
				return region;
			}
		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
			throw new SQLErrorException();
		}
	}

	@Override
	public Collection<Region> getRegionsAtLocation(String world, int x, int y, int z) throws HeavenException
	{
		// Search from database
		try (PreparedStatement ps = HeavenGuard.getConnection().prepareStatement(GET_REGIONS_AT_LOCATION))
		{
			ps.setString(1, world);
			ps.setInt(2, x);
			ps.setInt(3, y);
			ps.setInt(4, z);

			try (ResultSet rs = ps.executeQuery())
			{
				Collection<Region> regions = new HashSet<Region>();

				while (rs.next())
				{
					Region region = new SQLRegion(rs);
					regions.add(region);

					// Add region to cache
					regionsByName.put(region.getName(), region);
				}

				return regions;
			}
		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
			throw new SQLErrorException();
		}
	}
}