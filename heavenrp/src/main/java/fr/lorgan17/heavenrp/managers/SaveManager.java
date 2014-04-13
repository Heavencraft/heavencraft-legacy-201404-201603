package fr.lorgan17.heavenrp.managers;

import fr.heavencraft.Utils;
import fr.heavencraft.heavenrp.HeavenRP;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.scheduler.BukkitRunnable;

import com.sk89q.worldguard.protection.databases.ProtectionDatabaseException;

public class SaveManager
{
	public SaveManager()
	{
		new BukkitRunnable() {
			public void run()
			{
				save();
			}
		}.runTaskTimer(HeavenRP.getInstance(), 6000L, 6000L);
	}
	
	public static void save()
	{
		Utils.logInfo("Sauvegarde en cours...");

		for (World world : Bukkit.getWorlds())
		{
			world.save();
			
			try
			{
				Utils.getWorldGuard().getRegionManager(world).save();
			}
			catch (ProtectionDatabaseException e)
			{
				e.printStackTrace();
			}
		}
		
		Bukkit.savePlayers();
		
		Utils.logInfo("Sauvegarde terminée !");
	}
}