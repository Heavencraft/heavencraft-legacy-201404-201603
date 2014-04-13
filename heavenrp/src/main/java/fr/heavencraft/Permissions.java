package fr.heavencraft;

public class Permissions
{
	private static final String ADMIN_PERM = "heavencraft.administrator";
	private static final String MOD_PERM = "heavencraft.moderator";
	
	// Economy
	public static final String LIVRET_SIGN			= MOD_PERM;
	public static final String LIVRETPRO_COMMAND	= ADMIN_PERM;
	public static final String LIVRETPRO_SIGN		= ADMIN_PERM;
	public static final String ENTERPRISE_COMMAND	= ADMIN_PERM;

	// Homes
	public static final String TPHOME_COMMAND		= MOD_PERM;
	
	// Horses
	public static final String HORSE_BYPASS			= ADMIN_PERM;
	
	/*
	 * Province
	 */

	public static final String PROVINCE_SIGN = ADMIN_PERM;
	
	/*
	 * Warps
	 */

	public static final String WARP_COMMAND = MOD_PERM;
	public static final String WARP_SIGN = MOD_PERM;
	
	public static final String WATCH = MOD_PERM;
	
	public static final String SPAWNMOB_COMMAND = MOD_PERM;
}