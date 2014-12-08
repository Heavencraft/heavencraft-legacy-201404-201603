package fr.heavencraft.heavenguard;

import java.sql.Connection;

import fr.heavencraft.HeavenPlugin;
import fr.heavencraft.heavenguard.datamodel.RegionProvider;
import fr.heavencraft.heavenguard.datamodel.SQLRegionProvider;

/*
 * Database looks like :
 * region (
 *   name, parent_name,
 *   world, x1, y1, z1, x2, y2, z2,
 *   ... +flags
 * )
 * region_member (region_id, user_id, owner)
 * 
 * UUID ?
 */
public class HeavenGuard extends HeavenPlugin
{
	private static RegionProvider regionProvider = new SQLRegionProvider();

	public static Connection getConnection()
	{
		return null;
	}

	public static RegionProvider getRegionProvider()
	{
		return regionProvider;
	}
}