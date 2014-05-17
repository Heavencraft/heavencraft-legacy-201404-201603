package fr.tenkei.creaplugin.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import fr.tenkei.creaplugin.MyPlugin;
import fr.tenkei.creaplugin.exceptions.MyException;
import fr.tenkei.creaplugin.exceptions.PlayerNotConnectedException;
import fr.tenkei.creaplugin.managers.entities.Region;

public class Stuff {

	public static Player getPlayer(String name) throws PlayerNotConnectedException
	{
		Player player = Bukkit.getPlayer(name);
		
		if (player == null)
			throw new PlayerNotConnectedException(name);
		
		return player;
	}

	public static boolean canBeDestroyed(Player player, Block block)
	{
		if(player == null)
			return false;

		if(player.hasPermission(MyPlugin.administrator))
			return true;	
		
		if(player.getWorld().getName().equalsIgnoreCase("world_biome") || player.getWorld().getName().equalsIgnoreCase("Build"))
			return true;	

		Region region = Region.getRegionByLocation(block.getX(), block.getY(), block.getZ(), block.getWorld());
		
		if (region == null)
			return false;
		else
			return region.isMember(player.getName(), false);
	}
	
	public static boolean blocksEquals(Block blockA, Block blockB)
	{
		if (blockA.getX() != blockB.getX())
			return false;
		if (blockA.getY() != blockB.getY())
			return false;
		if (blockA.getZ() != blockB.getZ())
			return false;

		if (blockA.getWorld() != blockB.getWorld())
			return false;
		
		return true;
	}
	
	public static String locationToString(Location location)
    {
            return location.getWorld().getName() + ":" + Double.valueOf(arrondi(location.getX())).toString() + ":" + Double.valueOf(arrondi(location.getY())).toString() + ":"
             + Double.valueOf(arrondi(location.getZ())).toString() + ":" + Float.valueOf(location.getYaw()).toString() + ":" + Float.valueOf(location.getPitch()).toString();
    }

	public static Location stringToLocation(String str)
	{
	            String[] locData = str.split(":");
	            if (locData.length != 6) return null;
	            
	            World locWorld = Bukkit.getWorld(locData[0]);
	            if (locWorld == null) return null;
	            
	            return new Location(locWorld, Double.valueOf(locData[1]), Double.valueOf(locData[2]), Double.valueOf(locData[3]),
	                            Float.valueOf(locData[4]), Float.valueOf(locData[5]));
	}
	
	public static double arrondi(double A) {
	     return (double) ( (int) (A * 100 + .5)) / 100;
	}
	
	public static String getDateTime()
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		Date date = new Date();
		return dateFormat.format(date);
	}

	public static int toInt(String s) throws MyException
	{
		try
		{
			return Integer.parseInt(s);
		}
		catch (NumberFormatException ex)
		{
			throw new MyException("Le nombre {" + s + "} est incorrect.");
		}
	}
	
	public static int toUint(String s) throws MyException
	{
		int i = toInt(s);

		if (i < 0)
			throw new MyException("Le nombre {" + s + "} est incorrect.");

		return i;
	}
}
