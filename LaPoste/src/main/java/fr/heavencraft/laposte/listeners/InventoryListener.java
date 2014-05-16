package fr.heavencraft.laposte.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;

import fr.heavencraft.laposte.LaPoste;
import fr.heavencraft.laposte.Utils;
import fr.heavencraft.laposte.handlers.Colis;
import fr.heavencraft.laposte.handlers.JoueursEnEditionDeColis;

public class InventoryListener implements Listener{
	public InventoryListener()
	{
		Bukkit.getPluginManager().registerEvents(this, LaPoste.getInstance());
	}
	
	private final static String FORMAT_POSTE = "§4[§6La Poste§4] §6%1$s";
	
	@EventHandler
	public void onInventoryClose(InventoryCloseEvent e)
	{		
		if(e.getPlayer().getType() != EntityType.PLAYER)
			return;
		Player p = (Player)e.getPlayer();
		
		if(JoueursEnEditionDeColis.isEditing(p))
		{
		
			// Verifier que le colis n'est pas vide.
			if(e.getInventory().getSize() == Utils.getEmptySlots(e.getInventory()))
			{
				p.sendMessage(String.format(FORMAT_POSTE, "Votre colis est vide, envoi annulé."));
				return;
			}
			
			// Creer le colis
			
			Colis colis = new Colis(p, JoueursEnEditionDeColis.getDestinataire(p),e.getInventory());
			colis.envoyer();
			
			//enever le joueur de la liste des editeurs
			JoueursEnEditionDeColis.removePlayer(p);
		}
	}
	
	
}
