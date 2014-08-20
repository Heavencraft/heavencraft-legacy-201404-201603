package fr.manu67100.heavenrp.laposte.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;

import fr.heavencraft.Utils;
import fr.heavencraft.exceptions.HeavenException;
import fr.heavencraft.heavenrp.HeavenRP;
import fr.heavencraft.heavenrp.general.users.User;
import fr.heavencraft.heavenrp.general.users.UserProvider;
import fr.manu67100.heavenrp.laposte.handlers.Colis;
import fr.manu67100.heavenrp.laposte.handlers.JoueursEnEditionDeColis;

public class InventoryListener implements Listener{
	public InventoryListener()
	{
		Bukkit.getPluginManager().registerEvents(this, HeavenRP.getInstance());
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
				JoueursEnEditionDeColis.removePlayer(p);
				return;
			}

			// Creer le colis


			User user;
			try {
				user = UserProvider.getUserByName(p.getName());


				if (user.getBalance() -45 > 0)
				{
					user.updateBalance(-45);
					Colis colis = new Colis(p, JoueursEnEditionDeColis.getDestinataire(p),e.getInventory());
					colis.envoyer();
					JoueursEnEditionDeColis.removePlayer(p);
					return;
				}
				else
				{
					p.sendMessage(String.format(FORMAT_POSTE, "Vous n'avez pas assez d'argent."));
					JoueursEnEditionDeColis.removePlayer(p);
					return;
				}


			} catch (HeavenException e1) {
				e1.printStackTrace();
			}


			//enever le joueur de la liste des editeurs
			JoueursEnEditionDeColis.removePlayer(p);
		}
	}


}