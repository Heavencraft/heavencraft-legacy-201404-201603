package fr.heavencraft.heavenguard.datamodel;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.UUID;

import org.bukkit.World;

import fr.heavencraft.exceptions.HeavenException;
import fr.heavencraft.heavenguard.HeavenGuard;
import fr.heavencraft.heavenguard.api.Region;

public class SQLRegion implements Region
{
	private static final String ADD_MEMBER = "REPLACE INTO region_members (region_name, member_uuid, owner) VALUES (?, ?, ?);";

	private final int id;
	private final String name;
	private final int parentId;

	private final String world;
	private final int minX;
	private final int minY;
	private final int minZ;
	private final int maxX;
	private final int maxY;
	private final int maxZ;

	SQLRegion(ResultSet rs) throws SQLException
	{
		id = rs.getInt("id");
		name = rs.getString("name");
		parentId = rs.getInt("parent_id");

		world = rs.getString("world");
		minX = rs.getInt("min_x");
		minY = rs.getInt("min_y");
		minZ = rs.getInt("min_z");
		maxX = rs.getInt("max_x");
		maxY = rs.getInt("max_y");
		maxZ = rs.getInt("max_z");
	}

	@Override
	public int getId()
	{
		return id;
	}

	@Override
	public String getName()
	{
		return name;
	}

	@Override
	public boolean canBuilt(UUID player)
	{
		Collection<UUID> members = getMembers(false);

		if (members.contains(player))
			return true;

		Region parent = getParent();

		if (parent != null)
			return parent.canBuilt(player);

		return false;
	}

	@Override
	public Region getParent()
	{
		try
		{
			return HeavenGuard.getRegionProvider().getRegionById(parentId);
		}
		catch (HeavenException ex)
		{
			return null;
		}
	}

	@Override
	public void addMember(UUID player, boolean owner)
	{
		// try (PreparedStatement ps = HeavenGuard.getConnection()
		// .prepareStatement(ADD_MEMBER))
		// {
		// ps.setInt(1, id);
		// ps.setString(2, x);
		//
		// } catch (SQLException ex)
		// {
		// ex.printStackTrace();
		// }
	}

	@Override
	public boolean isMember(UUID player, boolean owner)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void removeMember(UUID player, boolean owner)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public Collection<UUID> getMembers(boolean owner)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean contains(String world, int x, int y, int z)
	{
		return this.world.equalsIgnoreCase(world) //
				&& minX <= x && x <= maxX //
				&& minY <= y && y <= maxY //
				&& minZ <= z && z <= maxZ;
	}

}
