package fr.heavencraft.laposte.handlers;

import java.util.ArrayList;

import org.bukkit.entity.Player;

import fr.heavencraft.laposte.Files;

public class PostOfficeManager {
	
	
	public static void LoadOffices()
	{
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
	
	
	
	// Les joueurs dans des PostOffices
	private static ArrayList<Player> inPostOffice = new ArrayList<Player>();
	
	public static boolean isInOffice(Player p) {
		return inPostOffice.contains(p);
	}

	public static void addPlayerInOffice(Player p) {
		inPostOffice.add(p);
	}

	public static void delPlayerInOffice(Player p) {
		inPostOffice.remove(p);
	}
	
	public static ArrayList<Player> getPlayersInOffice() {
		return inPostOffice;
	}
	
}
