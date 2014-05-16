package fr.heavencraft.laposte.listeners;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import fr.heavencraft.laposte.Files;
import fr.heavencraft.laposte.LaPoste;
import fr.heavencraft.laposte.WorldGuardRegions.RegionEnterEvent;
import fr.heavencraft.laposte.WorldGuardRegions.RegionLeaveEvent;
import fr.heavencraft.laposte.handlers.PostOfficeManager;

public class PostOfficeListener implements Listener{
	public PostOfficeListener()
	{
		Bukkit.getPluginManager().registerEvents(this, LaPoste.getInstance());
	}

	private final static String FORMAT_POSTE = "§4[§6La Poste§4] §6%1$s";

	@EventHandler
	public void onRegionEnter(RegionEnterEvent e)
	{
		if(PostOfficeManager.isOffice(e.getRegion().getId()) == true)
		{
			//TODO: jouer le son d'entrée a la poste. e.getPlayer().playSound(e.getPlayer().getLocation(), getSound(e.getRegion().getId()), 1, 1);
			e.getPlayer().sendMessage(String.format(FORMAT_POSTE, "Bienvenue dans notre bureau de poste."));
			PostOfficeManager.addPlayerInOffice(e.getPlayer());
		}

	}

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
			PostOfficeManager.delPlayerInOffice(e.getPlayer());
		}

	}
}
