package fr.manu67100.heavenrp.laposte;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class Files {
	// Set up all the needed things for files
			public static YamlConfiguration regions = null;
			public static File regionsFile = null;
			
			// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

			// Reload Abilities File
			
			public static void reloadRegions() {
				if (regionsFile == null)
					regionsFile = new File(Bukkit.getPluginManager().getPlugin("HeavenRP").getDataFolder(),"regions.yml");
				
				regions = YamlConfiguration.loadConfiguration(regionsFile);
				// Look for defaults in the jar
				InputStream defConfigStream = Bukkit.getPluginManager().getPlugin("HeavenRP").getResource("regions.yml");
				if (defConfigStream != null)
				{
					YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
					if (!regionsFile.exists() || regionsFile.length() == 0)
						regions.setDefaults(defConfig);
				}
			}
			
			// Get Abilities file
			public static FileConfiguration getRegions() {
				if (regions == null)
					reloadRegions();
				return regions;
			}

			// Safe Abilities File
			public static void saveRegions() {
				if (regions == null || regionsFile == null)
					return;
				try
				{
					getRegions().save(regionsFile);
				} catch (IOException ex)
				{
					Bukkit.getLogger().log(Level.SEVERE, "Could not save config " + regionsFile, ex);
				}
			}
}
