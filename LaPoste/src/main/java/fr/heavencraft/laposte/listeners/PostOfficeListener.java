package fr.heavencraft.laposte.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import fr.heavencraft.laposte.Files;
import fr.heavencraft.laposte.LaPoste;
import fr.heavencraft.laposte.WorldGuardRegions.RegionEnterEvent;
import fr.heavencraft.laposte.WorldGuardRegions.RegionLeaveEvent;
import fr.heavencraft.laposte.handlers.PostOfficeManager;
import fr.heavencraft.laposte.handlers.EnAttente.ColisEnAttente;

public class PostOfficeListener implements Listener{
	public PostOfficeListener()
	{
		Bukkit.getPluginManager().registerEvents(this, LaPoste.getInstance());
	}

	private final static String FORMAT_POSTE = "§4[§6La Poste§4] §6%1$s";

	@EventHandler
	public void onRegionEnter(RegionEnterEvent e)
	{

		e.getPlayer().sendMessage("DEBUG: region est office? " + PostOfficeManager.isOffice(e.getRegion().getId()));

		if(PostOfficeManager.isOffice(e.getRegion().getId()) == true)
		{
			//TODO: jouer le son d'entrée a la poste. e.getPlayer().playSound(e.getPlayer().getLocation(), getSound(e.getRegion().getId()), 1, 1);
			e.getPlayer().sendMessage(String.format(FORMAT_POSTE, "Bienvenue dans notre bureau de poste."));
			PostOfficeManager.addPlayerInOffice(e.getPlayer());
		}

	}


	//	public boolean getRegionEnabled(String regionName)
	//	{
	//		if(Files.getRegions().contains("Regions." + regionName.toLowerCase() + ".Enable"))
	//		{
	//			if( Files.getRegions().getBoolean("Regions." + regionName.toLowerCase() + ".Enable") == true)
	//				return true;
	//			else
	//				return false;
	//		}
	//		else
	//		{
	//			//creer la region automatiquement.
	//			Files.getRegions().set("Regions." + regionName.toLowerCase() + ".Enable", false);
	//			Files.getRegions().set("Regions." + regionName.toLowerCase() + ".Sound", "");
	//
	//			String greeting = "&b~ " +  regionName + " ~";
	//			greeting = greeting.replace('_', ' ');
	//			Files.getRegions().set("Regions." + regionName.toLowerCase() + ".Greeting", greeting);
	//			Files.saveRegions();
	//			return false;
	//		}
	//	}

	public String getSound(String regionName)
	{
		return Files.getRegions().getString("Regions." + regionName.toLowerCase() + ".Sound");
	}

	@EventHandler
	public void onRegionLeave(RegionLeaveEvent e)
	{
		//Enlever le joueur lorsqu'il sort d'un post office.
		if(PostOfficeManager.isOffice(e.getRegion().getId()) == true)
		{
			e.getPlayer().sendMessage("DEBUG: Vous quittez la poste");
			PostOfficeManager.delPlayerInOffice(e.getPlayer());
			//envoyer les colis de la personne
			ColisEnAttente.sendColis(e.getPlayer());
		}

	}
}
