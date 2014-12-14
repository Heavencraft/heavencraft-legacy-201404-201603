package fr.heavencraft.heavenguard;

import java.sql.Connection;

import fr.heavencraft.HeavenPlugin;
import fr.heavencraft.heavenguard.api.RegionProvider;
import fr.heavencraft.heavenguard.datamodel.SQLRegionProvider;

/*
 * Database looks like :
 * regions (
 *   id, name, parent_id,
 *   world, min_x, min_y, min_z, max_x, max_y, max_z,
 *   ... +flags
 * )
 * 
 * hg_regions_members (region_id, user_id, owner)
 * 
 * hg_uuid (id, uuid, last_name)
 * 
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