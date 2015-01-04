package fr.heavencraft.heavenguard.datamodel;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashSet;
import java.util.UUID;

import fr.heavencraft.exceptions.HeavenException;
import fr.heavencraft.exceptions.SQLErrorException;
import fr.heavencraft.heavenguard.api.Region;
import fr.heavencraft.heavenguard.bukkit.HeavenGuard;

public class SQLRegion implements Region
{
	private static final String ADD_MEMBER = "REPLACE INTO regions_members (region_name, uuid, owner) VALUES (LOWER(?), ?, ?);";
	private static final String IS_MEMBER = "SELECT owner FROM regions_members WHERE region_name = LOWER(?) AND uuid = ? LIMIT 1;";
	private static final String GET_MEMBERS = "SELECT uuid FROM regions_members WHERE region_name = LOWER(?) AND owner = ?;";
	private static final String REMOVE_MEMBER = "DELETE FROM regions_members WHERE region_name = LOWER(?) AND uuid = ? AND owner = ? LIMIT 1;";

	private static final String REDEFINE = "UPDATE regions SET world = LOWER(?), min_x = ?, min_y = ?, min_z = ?, max_x = ?, max_y = ?, max_z = ? WHERE name = LOWER(?) LIMIT 1";
	private static final String SETPARENT = "UPDATE regions SET parent_name = LOWER(?) WHERE name = LOWER(?) LIMIT 1";

	private static final String LOAD_MEMBERS = "SELECT uuid, owner FROM regions_members WHERE region_name = LOWER(?);";

	private final String name;
	private String parentName;

	private String world;
	private int minX;
	private int minY;
	private int minZ;
	private int maxX;
	private int maxY;
	private int maxZ;

	private final Collection<UUID> members = new HashSet<UUID>();
	private final Collection<UUID> owners = new HashSet<UUID>();

	SQLRegion(ResultSet rs) throws SQLException
	{
		name = rs.getString("name");
		parentName = rs.getString("parent_name");

		world = rs.getString("world");
		minX = rs.getInt("min_x");
		minY = rs.getInt("min_y");
		minZ = rs.getInt("min_z");
		maxX = rs.getInt("max_x");
		maxY = rs.getInt("max_y");
		maxZ = rs.getInt("max_z");

		loadMembers();
	}

	private void loadMembers() throws SQLException
	{
		try (PreparedStatement ps = HeavenGuard.getConnection().prepareStatement(LOAD_MEMBERS))
		{
			ps.setString(1, name);

			try (ResultSet rs = ps.executeQuery())
			{
				while (rs.next())
				{
					UUID player = UUID.fromString(rs.getString("uuid"));

					if (rs.getBoolean("owner"))
						owners.add(player);
					else
						members.add(player);
				}
			}
		}
	}

	@Override
	public String getName()
	{
		return name;
	}

	@Override
	public boolean canBuilt(UUID player)
	{
		// Members/Owners of this region can build there
		if (isMember(player, false))
			return true;

		// Players that can build in the parent region can also build there
		Region parent = getParent();
		if (parent != null)
			return parent.canBuilt(player);

		return false;
	}

	/*
	 * Parent
	 */

	@Override
	public Region getParent()
	{
		if (parentName == null)
			return null;

		try
		{
			return HeavenGuard.getRegionProvider().getRegionByName(parentName);
		}
		catch (HeavenException ex)
		{
			return null;
		}
	}

	@Override
	public void setParent(String parentName) throws HeavenException
	{
		// Update database
		try (PreparedStatement ps = HeavenGuard.getConnection().prepareStatement(SETPARENT))
		{
			ps.setString(1, parentName);
			ps.setString(2, name);

			if (ps.executeUpdate() != 1)
				throw new HeavenException("Impossible de mettre à jour la région.");
		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
			throw new SQLErrorException();
		}

		// Update cache
		this.parentName = parentName;
	}

	/*
	 * Coordonnées
	 */

	@Override
	public boolean contains(String world, int x, int y, int z)
	{
		return this.world.equalsIgnoreCase(world) //
				&& minX <= x && x <= maxX //
				&& minY <= y && y <= maxY //
				&& minZ <= z && z <= maxZ;
	}

	@Override
	public void redefine(String world, int minX, int minY, int minZ, int maxX, int maxY, int maxZ) throws HeavenException
	{
		// Update database
		try (PreparedStatement ps = HeavenGuard.getConnection().prepareStatement(REDEFINE))
		{
			ps.setString(1, world);
			ps.setInt(2, minX);
			ps.setInt(3, minY);
			ps.setInt(4, minZ);
			ps.setInt(5, maxX);
			ps.setInt(6, maxY);
			ps.setInt(7, maxZ);
			ps.setString(8, name);

			if (ps.executeUpdate() != 1)
				throw new HeavenException("Impossible de mettre à jour la région.");
		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
			throw new SQLErrorException();
		}

		// Update cache
		this.world = world;
		this.minX = minX;
		this.minY = minY;
		this.minZ = minZ;
		this.maxX = maxX;
		this.maxY = maxY;
		this.maxZ = maxZ;
	}

	@Override
	public String getWorld()
	{
		return world;
	}

	@Override
	public int getMinX()
	{
		return minX;
	}

	@Override
	public int getMinY()
	{
		return minY;
	}

	@Override
	public int getMinZ()
	{
		return minZ;
	}

	@Override
	public int getMaxX()
	{
		return maxX;
	}

	@Override
	public int getMaxY()
	{
		return maxY;
	}

	@Override
	public int getMaxZ()
	{
		return maxZ;
	}

	/*
	 * Members
	 */

	@Override
	public void addMember(UUID player, boolean owner) throws HeavenException
	{
		try (PreparedStatement ps = HeavenGuard.getConnection().prepareStatement(ADD_MEMBER))
		{
			ps.setString(1, name);
			ps.setString(2, player.toString());
			ps.setBoolean(3, owner);

			if (ps.executeUpdate() != 1)
				throw new HeavenException("Impossible de mettre à jour la région.");
		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
			new SQLErrorException();
		}
	}

	@Override
	public boolean isMember(UUID player, boolean owner)
	{
		if (owner)
			return owners.contains(player);
		else
			return members.contains(player) || owners.contains(player);
	}

	// @Override
	// public boolean isMember(UUID player, boolean owner)
	// {
	// try (PreparedStatement ps =
	// HeavenGuard.getConnection().prepareStatement(IS_MEMBER))
	// {
	// ps.setString(1, name);
	// ps.setString(2, player.toString());
	//
	// try (ResultSet rs = ps.executeQuery())
	// {
	// if (!rs.next())
	// return false;
	//
	// return owner ? rs.getBoolean("owner") : true;
	// }
	// }
	// catch (SQLException ex)
	// {
	// ex.printStackTrace();
	// new SQLErrorException();
	// }
	// return false;
	// }

	@Override
	public void removeMember(UUID player, boolean owner) throws HeavenException
	{
		try (PreparedStatement ps = HeavenGuard.getConnection().prepareStatement(REMOVE_MEMBER))
		{
			ps.setString(1, name);
			ps.setString(2, player.toString());
			ps.setBoolean(3, owner);

			if (ps.executeUpdate() != 1)
				throw new HeavenException("Impossible de mettre à jour la région.");
		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
			new SQLErrorException();
		}
	}

	@Override
	public Collection<UUID> getMembers(boolean owner)
	{
		// Never return the original collection, because plugin could modify it.
		return new HashSet<UUID>(owner ? owners : members);
	}

	// @Override
	// public Collection<UUID> getMembers(boolean owner)
	// {
	// try (PreparedStatement ps =
	// HeavenGuard.getConnection().prepareStatement(GET_MEMBERS))
	// {
	// ps.setString(1, name);
	// ps.setBoolean(2, owner);
	//
	// try (ResultSet rs = ps.executeQuery())
	// {
	// Collection<UUID> members = new HashSet<UUID>();
	//
	// while (rs.next())
	// members.add(UUID.fromString(rs.getString("uuid")));
	//
	// return members;
	// }
	// }
	// catch (SQLException ex)
	// {
	// ex.printStackTrace();
	// new SQLErrorException();
	// }
	//
	// return null;
	// }
}