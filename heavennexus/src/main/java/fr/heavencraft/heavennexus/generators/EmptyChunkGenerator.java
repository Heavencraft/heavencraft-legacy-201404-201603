package fr.heavencraft.heavennexus.generators;

import java.util.Random;

import org.bukkit.World;
import org.bukkit.generator.ChunkGenerator;

public class EmptyChunkGenerator extends ChunkGenerator {
	
    @Override
    public byte[] generate(World world, Random random, int x, int z)
    {
            byte[] result =  new byte[32768];
            for (int i = 0; i < 32768; i++)
                    result[i] = 0;
            return result;
    }
}
