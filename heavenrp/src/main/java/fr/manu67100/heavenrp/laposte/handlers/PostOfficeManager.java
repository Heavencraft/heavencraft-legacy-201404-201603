package fr.manu67100.heavenrp.laposte.handlers;

import java.util.ArrayList;

import org.bukkit.entity.Player;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;

import fr.heavencraft.heavenrp.HeavenRP;
import fr.manu67100.heavenrp.laposte.Files;

public class PostOfficeManager {
	
	static WorldGuardPlugin wg;
	
	public static void LoadOffices()
	{
		wg = HeavenRP.getWorldGuard();
		//################ Chargement des regions ################
		for (String a : PostOfficeManager.getPostOffices())
			PostOfficeManager.delOffice(a);	
		Files.reloadRegions();
		if(Files.getRegions().getConfigurationSection("Regions") != null)
			for (String a : Files.getRegions().getConfigurationSection("Regions").getKeys(false))
				PostOfficeManager.addOffice(a);
		//############## FIN Chargement des regions ###############		
	}
	
	// Les PostOffices
	private static ArrayList<String> PostOffices = new ArrayList<String>();
	
	/**
	 * Returns if the passed string correspond with a region registered as post office.
	 * @param p
	 * @return
	 */
	public static boolean isOffice(String p) {
		return PostOffices.contains(p.toLowerCase());
	}

	public static void addOffice(String p) {
		PostOffices.add(p.toLowerCase());
	}

	public static void delOffice(String p) {
		PostOffices.remove(p.toLowerCase());
	}
	
	public static ArrayList<String> getPostOffices() {
		return PostOffices;
	}
	
	/**
	 * Checks if user is inside a region corresponding to a registered Post region.
	 * @param p
	 * @return
	 */
	public static boolean isInOffice(Player p) {
		ApplicableRegionSet aps = wg.getRegionManager(p.getWorld()).getApplicableRegions(p.getLocation());
		for (ProtectedRegion region : aps) 
		    if(isOffice(region.getId()))
		    	return true;
		return false;
	}
	
}
