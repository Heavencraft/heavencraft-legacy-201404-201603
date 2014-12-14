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
import fr.heavencraft.heavenguard.api.Region;
import fr.heavencraft.heavenguard.api.RegionProvider;
import fr.heavencraft.heavenguard.exceptions.RegionNotFoundException;

public class SQLRegionProvider implements RegionProvider
{
	/*
	 * SQL Queries
	 */

	private static final String CREATE_REGION = "INSERT INTO regions (name, world, min_x, min_y, min_z, max_x, max_y, max_z) VALUES (LOWER(?), LOWER(?), ?, ?, ?, ?, ?, ?);";
	private static final String GET_REGION_BY_ID = "SELECT * FROM regions WHERE id = ? LIMIT 1;";
	private static final String GET_REGION_BY_NAME = "SELECT * FROM regions WHERE name = LOWER(?) LIMIT 1;";
	private static final String GET_REGIONS_AT_LOCATION = "SELECT * FROM regions WHERE world = LOWER(?) AND ? BETWEEN min_x AND max_x AND ? BETWEEN y_min AND y_max AND ? BETWEEN z_min AND z_max;";

	/*
	 * Cache
	 */

	private final Map<Integer, Region> regionsById = new ConcurrentHashMap<Integer, Region>();
	private final Map<String, Region> regionsByName = new ConcurrentHashMap<String, Region>();

	private void addToCache(Region region)
	{
		regionsById.put(region.getId(), region);
		regionsByName.put(region.getName(), region);
	}

	@Override
	public void clearCache()
	{
		regionsByName.clear();
	}

	@Override
	public void createRegion(String name, String world, int minX, int minY, int minZ, int maxX, int maxY, int maxZ)
			throws HeavenException
	{
		try (PreparedStatement ps = HeavenGuard.getConnection().prepareStatement(CREATE_REGION))
		{
			ps.setString(1, name);
			ps.setString(2, world);
			ps.setInt(3, minX);
			ps.setInt(4, minY);
			ps.setInt(5, minZ);
			ps.setInt(6, minX);
			ps.setInt(7, minY);
			ps.setInt(8, minZ);
			ps.executeUpdate();
		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
			throw new SQLErrorException();
		}
	}

	@Override
	public Region getRegionById(int id) throws HeavenException
	{
		// Search from cache
		Region region = regionsById.get(id);

		if (region != null)
			return region;

		// Search from database
		try (PreparedStatement ps = HeavenGuard.getConnection().prepareStatement(GET_REGION_BY_ID))
		{
			ps.setInt(1, id);

			try (ResultSet rs = ps.executeQuery())
			{
				if (!rs.next())
					throw new RegionNotFoundException(id);

				region = new SQLRegion(rs);
				addToCache(region);
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
	public Region getRegionByName(String name) throws HeavenException
	{
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
				addToCache(region);
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
					addToCache(region);
					regions.add(region);
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