package fr.heavencraft.heavenguard.datamodel;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

import fr.heavencraft.exceptions.HeavenException;
import fr.heavencraft.heavenguard.HeavenGuard;

public class SQLRegion implements Region
{
	private final String name;
	private final String parentName;

	private final String world;
	private final int x1;
	private final int y1;
	private final int z1;
	private final int x2;
	private final int y2;
	private final int z2;

	SQLRegion(ResultSet rs) throws SQLException
	{
		name = rs.getString("name");
		parentName = rs.getString("parent_name");

		world = rs.getString("world");
		x1 = rs.getInt("x1");
		y1 = rs.getInt("y1");
		z1 = rs.getInt("z1");
		x2 = rs.getInt("x2");
		y2 = rs.getInt("y2");
		z2 = rs.getInt("z2");
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
				&& x1 <= x && x <= x2 //
				&& y1 <= y && y <= y2 //
				&& z1 <= z && z <= z2;
	}
}
