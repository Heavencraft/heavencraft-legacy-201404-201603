package fr.lorgan17.lorganserver.managers;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.scheduler.BukkitRunnable;

import fr.lorgan17.lorganserver.LorganServer;

public class SaveManager {

	public SaveManager()
	{
		new BukkitRunnable() {
			
			@Override
			public void run()
			{
				LorganServer.sendConsoleMessage("Sauvegarde en cours...");
				
				for (World world : Bukkit.getWorlds())
					world.save();
				
				Bukkit.savePlayers();
				
				LorganServer.sendConsoleMessage("Sauvegarde termin�e !");
			}
		}.runTaskTimer(LorganServer.getInstance(), 6000, 6000);
	}
}
