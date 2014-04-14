package fr.heavencraft.NavalConflicts;

import java.util.ArrayList;

import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.plugin.java.JavaPlugin;

public class WorldOperator {

	private JavaPlugin plugin = null;

	public WorldOperator(NavalConflicts NCplugin) {
		plugin = NCplugin;
	}

	public void LoadWorld(String worldName) {
		// Vérifier que le monde n'est pas déjà chargé
		if (!getWorldsNames().contains(worldName)) {
			plugin.getServer().createWorld(new WorldCreator(worldName));
			plugin.getServer().getWorld(worldName).setAutoSave(false);
		}

	}

	public void setAutosave(String worldName, boolean state) {
		// Vérifier que le monde n'est pas déjà chargé
		if (getWorldsNames().contains(worldName)) {
			plugin.getServer().getWorld(worldName).setAutoSave(state);
		}
	}
	
	public void setAutosave(World wrld, boolean state) {
		// Vérifier que le monde n'est pas déjà chargé
		if (plugin.getServer().getWorlds().contains(wrld)) {
			wrld.setAutoSave(state);
		}
	}

	public void UnloadWorld(World world) {
		if (world != null) {
			if (plugin.getServer().unloadWorld(world, false)) {
				plugin.getLogger().info("Dechargement de " + world.getName());
			} else {
				plugin.getLogger().severe(
						"Erreur de déchargmenent " + world.getName());
			}
		} else {
			plugin.getLogger().info("Erreur, monde nul.");
		}
	}

	/**
	 * Retourne une liste des mondes disponibles
	 * 
	 * @return
	 */
	public ArrayList<World> getWorlds() {
		ArrayList<World> worldObjects = new ArrayList<World>();
		for (World world : plugin.getServer().getWorlds()) {
			worldObjects.add(world);
		}
		return worldObjects;
	}

	public ArrayList<String> getWorldsNames() {
		ArrayList<String> worldObjects = new ArrayList<String>();
		for (World world : plugin.getServer().getWorlds()) {
			worldObjects.add(world.getName());
		}
		return worldObjects;
	}

	public World getWorldByName(String worldname) {
		return plugin.getServer().getWorld(worldname);

	}

}
