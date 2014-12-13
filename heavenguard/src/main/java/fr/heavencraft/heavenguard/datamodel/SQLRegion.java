package fr.heavencraft.heavenguard.datamodel;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

import org.bukkit.World;

import fr.heavencraft.exceptions.HeavenException;
import fr.heavencraft.heavenguard.HeavenGuard;

public class SQLRegion implements Region
{
	private final String name;
	private final String parentName;

	private final String world;
	private final int minX;
	private final int minY;
	private final int minZ;
	private final int maxX;
	private final int maxY;
	private final int maxZ;

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
	}

	@Override
	public String getName()
	{
		return name;
	}

	@Override
	public Region getParent()
	{
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
	public void addMember(String name, boolean owner)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isMember(String name, boolean owner)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void removeMember(String name, boolean owner)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public Collection<String> getMembers(boolean owner)
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
