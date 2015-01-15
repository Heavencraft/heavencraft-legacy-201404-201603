package fr.heavencraft.heavenguard.datamodel;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;

import fr.heavencraft.api.providers.connection.ConnectionProvider;
import fr.heavencraft.common.logs.HeavenLog;
import fr.heavencraft.exceptions.HeavenException;
import fr.heavencraft.exceptions.SQLErrorException;
import fr.heavencraft.heavenguard.api.GlobalRegion;
import fr.heavencraft.heavenguard.api.Region;
import fr.heavencraft.heavenguard.api.RegionProvider;
import fr.heavencraft.heavenguard.exceptions.RegionNotFoundException;

public class SQLRegionProvider implements RegionProvider
{
	// SQL Queries
	private static final String PRELOAD_REGIONS = "SELECT * FROM regions;";
	private static final String PRELOAD_GLOBAL_REGIONS = "SELECT * FROM worlds;";

	private static final String LOAD_REGION = "SELECT * FROM regions WHERE name = LOWER(?) LIMIT 1;";

	private static final String CREATE_REGION = "INSERT INTO regions (name, world, min_x, min_y, min_z, max_x, max_y, max_z) VALUES (LOWER(?), LOWER(?), ?, ?, ?, ?, ?, ?);";
	private static final String DELETE_REGION = "DELETE FROM regions WHERE name = LOWER(?) LIMIT 1;";

	private static final String CREATE_GLOBAL_REGION = "INSERT INTO global_regions (name) VALUES (LOWER(?));";

	// Logger
	private final HeavenLog log = HeavenLog.getLogger(getClass());

	// Cache
	private final Map<String, Region> regionsByName = new HashMap<String, Region>();
	private final Map<String, Collection<Region>> regionsByWorld = new HashMap<String, Collection<Region>>();
	private final Map<String, GlobalRegion> globalRegionsByWorld = new HashMap<String, GlobalRegion>();

	// Connection to the database
	private final ConnectionProvider connectionProvider;

	public SQLRegionProvider(ConnectionProvider connectionProvider)
	{
		this.connectionProvider = connectionProvider;

		loadFromDatabase();
		loadGlobalRegions();
	}

	/*
	 * Cache
	 */

	private void loadFromDatabase()
	{
		try (PreparedStatement ps = connectionProvider.getConnection().prepareStatement(PRELOAD_REGIONS))
		{
			try (ResultSet rs = ps.executeQuery())
			{
				int count = 0;

				while (rs.next())
				{
					++count;
					addToCache(new SQLRegion(connectionProvider, rs));
				}

				log.info("%1$s regions loaded from database.", count);
			}
		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
			Bukkit.shutdown(); // Close server if we can't load regions
		}
	}

	private Region loadRegion(String name) throws RegionNotFoundException, SQLErrorException
	{
		try (PreparedStatement ps = connectionProvider.getConnection().prepareStatement(LOAD_REGION))
		{
			ps.setString(1, name);

			try (ResultSet rs = ps.executeQuery())
			{
				if (!rs.next())
					throw new RegionNotFoundException(name);

				Region region = new SQLRegion(connectionProvider, rs);
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

	private void loadGlobalRegions()
	{
		try (PreparedStatement ps = connectionProvider.getConnection().prepareStatement(PRELOAD_GLOBAL_REGIONS))
		{
			try (ResultSet rs = ps.executeQuery())
			{
				int count = 0;

				while (rs.next())
				{
					SQLGlobalRegion region = new SQLGlobalRegion(rs);
					globalRegionsByWorld.put(region.getName(), region);
				}

				log.info("%1$s regions loaded from database.", count);
			}
		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
			Bukkit.shutdown(); // Close server if we can't load regions
		}
	}

	/*
	 * Cache management
	 */

	private void addToCache(Region region)
	{
		// Add to "Name -> Region" cache
		regionsByName.put(region.getName(), region);

		// Add to "World -> Regions" cache
		Collection<Region> regions = regionsByWorld.get(region.getWorld());

		if (regions == null)
		{
			regions = new ArrayList<Region>();
			regionsByWorld.put(region.getWorld(), regions);
		}

		regions.add(region);
	}

	private void removeFromCache(Region region)
	{
		// Remove from "Name -> Region" cache
		regionsByName.remove(region.getName());

		// Remove from "World -> Regions" cache
		regionsByWorld.get(region.getWorld()).remove(region);
	}

	@Override
	public void clearCache()
	{
		// Clear caches
		regionsByName.clear();
		regionsByWorld.clear();

		// Reload regions from database
		loadFromDatabase();
	}

	@Override
	public Region createRegion(String name, String world, int minX, int minY, int minZ, int maxX, int maxY, int maxZ)
			throws HeavenException
	{
		if (regionsByName.get(name) != null)
			throw new HeavenException("La protection {%1$s} existe déjà.", name);

		try (PreparedStatement ps = connectionProvider.getConnection().prepareStatement(CREATE_REGION))
		{
			ps.setString(1, name);
			ps.setString(2, world);
			ps.setInt(3, minX);
			ps.setInt(4, minY);
			ps.setInt(5, minZ);
			ps.setInt(6, maxX);
			ps.setInt(7, maxY);
			ps.setInt(8, maxZ);

			if (ps.executeUpdate() != 1)
			{
				throw new HeavenException("La region existe déjà");
			}

			return loadRegion(name);
		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
			throw new SQLErrorException();
		}
	}

	@Override
	public void deleteRegion(String name) throws HeavenException
	{
		try (PreparedStatement ps = connectionProvider.getConnection().prepareStatement(DELETE_REGION))
		{
			ps.setString(1, name);

			if (ps.executeUpdate() != 1)
				throw new HeavenException("La protection {%1$s} n'a pas pu être supprimée.");

			removeFromCache(getRegionByName(name));
		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
			new SQLErrorException();
		}
	}

	/*
	 * Retrieving region(s)
	 */

	@Override
	public Region getRegionByName(String name) throws HeavenException
	{
		Region region = regionsByName.get(name);

		if (region == null)
			throw new RegionNotFoundException(name);

		return region;
	}

	@Override
	public Collection<Region> getRegionsAtLocation(String world, int x, int y, int z)
	{
		Collection<Region> regionsAtLocation = new ArrayList<Region>();
		Collection<Region> regionsInWorld = regionsByWorld.get(world);

		if (regionsInWorld != null)
		{
			for (Region region : regionsInWorld)
			{
				// Optimization : don't check world twice
				if (region.containsSameWorld(x, y, z))
					regionsAtLocation.add(region);
			}
		}

		return regionsAtLocation;
	}

	@Override
	public GlobalRegion getGlobalRegion(String world)
	{
		GlobalRegion region = globalRegionsByWorld.get(world);

		if (region != null)
			return region;

		// TODO Auto-generated method stub
		return null;
	}
}