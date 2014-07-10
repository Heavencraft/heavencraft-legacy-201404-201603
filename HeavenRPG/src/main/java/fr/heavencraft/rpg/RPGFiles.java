package fr.heavencraft.rpg;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class RPGFiles {

	// Set up all the needed things for files
		public static YamlConfiguration Zones = null;
		public static File zonesFile = null;
		
//		public static FileConfiguration getConfig() {
//			return HeavenRPG.getInstance().getConfig();
//		}
//
//		public static void saveConfig() {
//			HeavenRPG.getInstance().saveConfig();
//		}
//
//		public static void reloadConfig() {
//			HeavenRPG.getInstance().reloadConfig();
//		}

		public static void saveAll() {
//			saveConfig();
			saveZones();
		}

		public static void reloadAll() {
//			reloadConfig();
			reloadClasses();
		}
		
		
		// Reload Abilities File
		public static void reloadClasses() {
			if (zonesFile == null)
				zonesFile = new File(Bukkit.getPluginManager().getPlugin("HeavenRPG").getDataFolder(), "Zones.yml");
			Zones = YamlConfiguration.loadConfiguration(zonesFile);
			
			// Look for defaults in the jar
			InputStream defConfigStream = Bukkit.getPluginManager().getPlugin("HeavenRPG").getResource("Zones.yml");
			if (defConfigStream != null)
			{
				YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
				if (!zonesFile.exists() || zonesFile.length() == 0)
					Zones.setDefaults(defConfig);
			}
		}

		public static FileConfiguration getZones() {
			if (Zones == null)
				reloadClasses();
			return Zones;
		}

		public static void saveZones() {
			if (Zones == null || zonesFile == null)
				return;
			try
			{
				getZones().save(zonesFile);
			} catch (IOException ex)
			{
				Bukkit.getLogger().log(Level.SEVERE, "Could not save config " + zonesFile, ex);
			}
		}
}
