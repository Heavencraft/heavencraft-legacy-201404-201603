package fr.tenkei.creaplugin.managers;

import java.util.HashSet;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;


public class WarpsManager {
	
	private HashSet<Warp> _warpsList;
	private final int BLOC_TELEPORTER = 159;
	private final int BLOC_BIOME = 112;
	

	private class Warp
	{
		private Location _startLocation;
		private Location _exitLocation;

		public Warp(World world, int x, int y, int z, World world2, int x2, int y2, int z2)
		{
			_startLocation = new Location(world, x, y, z);
			_exitLocation = new Location(world2, x2, y2, z2);
		}

		public boolean shouldTeleport(Location location)
		{
			if (location.getWorld() != _startLocation.getWorld()) return false;
			return location.distance(_startLocation) < 2.0;
		}
	}

	public WarpsManager() {
		_warpsList = new HashSet<Warp>();
		_warpsList.add(new Warp(WorldsManager.getTheCreative(), -13, 153, 52, WorldsManager.getTheCreative(), 0, 60, 0));
	}

	public void teleport(Player player)
	{
		Location playerLocation = player.getLocation();
		for (Warp warp : _warpsList)
		{
			if (warp.shouldTeleport(playerLocation)){
				player.teleport(warp._exitLocation);
				return;
			}
		}
		
		shallTeleport(player);
	}
	
	@SuppressWarnings("deprecation")
	/***
	 * @author Morgan
	 * <h1>Classe de gestion de téléportation.</h1>
	 * 
	 * 
	 * **/
	public void shallTeleport(Player player)
	{
		Location playerLocation = player.getLocation();
		
		Location teleportLocation = new Location(WorldsManager.getTheCreative(),
				playerLocation.getX(),
				playerLocation.getY(),
				playerLocation.getZ(),
				playerLocation.getYaw(),
				playerLocation.getPitch());
		
		if(playerLocation.getBlock().getRelative(0, -1, 0).getType().getId() == BLOC_TELEPORTER && playerLocation.getBlock().getRelative(0, -1, 0).getData() == 3) { // NORD  -196blocks
			teleportLocation.add(0, 0, -howFarIsNextTeleportNS(playerLocation.getBlockZ(), false));
			player.teleport(teleportLocation);
		}
		else if(playerLocation.getBlock().getRelative(0, -1, 0).getType().getId() == BLOC_TELEPORTER && playerLocation.getBlock().getRelative(0, -1, 0).getData() == 12) { // SUD  +196blocks
			teleportLocation.add(0, 0, howFarIsNextTeleportNS(playerLocation.getBlockZ(), true));
			player.teleport(teleportLocation);
		}
		else if(playerLocation.getBlock().getRelative(0, -1, 0).getType().getId() == BLOC_TELEPORTER && playerLocation.getBlock().getRelative(0, -1, 0).getData() == 5) { // EST +
			teleportLocation.add(howFarIsNextTeleportEO(playerLocation.getX()), 0, 0);
			player.teleport(teleportLocation);
		}
		else if(playerLocation.getBlock().getRelative(0, -1, 0).getType().getId() == BLOC_TELEPORTER && playerLocation.getBlock().getRelative(0, -1, 0).getData() == 4) { // OUEST -
			if(playerLocation.getX()<69)
				teleportLocation = WorldsManager.getTheCreative().getSpawnLocation();
			
			teleportLocation.add(-howFarIsNextTeleportEO(playerLocation.getX()), 0, 0);
			player.teleport(teleportLocation);
		}
		else if(playerLocation.getBlock().getRelative(0, -1, 0).getType().getId() == BLOC_BIOME) { // BIOME
			
			player.teleport(WorldsManager.getTheCreativeBiome().getSpawnLocation());
		}
        //		}else{
		// 
		//			teleportLocation.add(1, 0, 1);
		//		}

		//		<69 => /spawn
		//				74<...<165 => 91
		//				170<...<309 => 139
		//				314<...<501 => 187
		//				506<...<789 => 283
		//				794<...<1173 => 379


	}

	private double howFarIsNextTeleportEO(double d) {
		if(d<69)
			return 0;
		else if(d<165)
			return 91;
		else if(d<309)
			return 139;
		else if(d<501)
			return 187;
		else if(d<789)
			return 283;
		else if(d<1173)
			return 379;


		return 2;
	}
	
	
	private int howFarIsNextTeleportNS(int y, boolean SUD) {
		if(SUD && y<100 && y>-210)
			return 195;
		
		if(!SUD && y<210 && y>-100)
			return 195;
		
		return 187;
	}
	
}