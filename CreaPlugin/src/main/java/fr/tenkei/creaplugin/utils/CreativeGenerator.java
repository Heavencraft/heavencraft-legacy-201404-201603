package fr.tenkei.creaplugin.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.ChunkGenerator;


public class CreativeGenerator extends ChunkGenerator
{

	public int xyz(int x, int y, int z)
	{
		return (x * 16 + z) * 128 + y;
	}
	
	@Override
	public List<BlockPopulator> getDefaultPopulators(org.bukkit.World world)
	{
		List<BlockPopulator> populators = new ArrayList<BlockPopulator>();
		return populators;
	}

	@SuppressWarnings("deprecation")
	@Override
	public byte[] generate(World world, Random rand, int chunkX, int chunkZ)
	{
	    byte[] result = new byte[32768];
	    
		byte mainMaterial = (byte) Material.GRASS.getId();
		byte subMaterial = (byte) Material.DIRT.getId();

        int maxY = 45;
        
		for (int x = 0; x < 16; x++)
		{
			for (int z = 0; z < 16; z++)
			{
				for (int y = 0; y <= 4; y++)
				{
					result[xyz(x, y, z)] = (byte) Material.BEDROCK.getId();
				}
				for (int y = 5; y <= maxY; y++)
				{
					result[xyz(x, y, z)] = (byte) Material.STONE.getId();
				}

				for (int y = maxY + 1; y <= maxY + 4; y++)
				{
					result[xyz(x, y, z)] = subMaterial;
				}
				result[xyz(x, maxY + 5, z)] = mainMaterial;
			}
		}

		return result;
	}
}