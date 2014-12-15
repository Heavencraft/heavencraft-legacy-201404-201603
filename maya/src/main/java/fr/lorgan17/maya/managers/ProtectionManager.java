package fr.lorgan17.maya.managers;

import org.bukkit.block.Block;

import fr.lorgan17.maya.MayaPlugin;

public class ProtectionManager
{
	public static boolean isProtected(Block block)
	{
		int x = Math.abs(block.getChunk().getX() - MayaPlugin.spawn.getChunk().getX());
		int z = Math.abs(block.getChunk().getZ() - MayaPlugin.spawn.getChunk().getZ());
		
		
		if (x <= 1 && z <= 1)
		{
			return true;
		}
		
		else
			return false;
	}
}