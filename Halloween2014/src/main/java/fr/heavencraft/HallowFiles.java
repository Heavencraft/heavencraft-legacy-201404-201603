package fr.heavencraft;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class HallowFiles {

	// Set up all the needed things for files
		public static YamlConfiguration Players = null;
		public static File playersFile = null;

		public static void saveAll() {
			savePlayers();
		}

		public static void reloadAll() {
			reloadPlayers();
		}
		
		
		// Reload Abilities File
		public static void reloadPlayers() {
			if (playersFile == null)
				playersFile = new File(Bukkit.getPluginManager().getPlugin("HeavenHallow").getDataFolder(), "Players.yml");
			Players = YamlConfiguration.loadConfiguration(playersFile);		
			// Look for defaults in the jar
			InputStream defConfigStream = Bukkit.getPluginManager().getPlugin("HeavenHallow").getResource("Players.yml");
			if (defConfigStream != null)
			{
				YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
				if (!playersFile.exists() || playersFile.length() == 0)
					Players.setDefaults(defConfig);
			}
		}

		public static FileConfiguration getPlayers() {
			if (Players == null)
				reloadPlayers();
			return Players;
		}
		

		public static void savePlayers() {
			if (Players == null || playersFile == null)
				return;
			try
			{
				getPlayers().save(playersFile);
			} catch (IOException ex)
			{
				Bukkit.getLogger().log(Level.SEVERE, "Could not save config " + playersFile, ex);
			}
		}
		
}
