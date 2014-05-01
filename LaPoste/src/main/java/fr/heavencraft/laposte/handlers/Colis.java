package fr.heavencraft.laposte.handlers;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import fr.heavencraft.laposte.InventoryUtils;
import fr.heavencraft.laposte.LaPoste;

public class Colis {
	private Inventory contenu;
	private Player expediteur;
	private Player destinataire;
	
	public Colis(Player owner, Player dest)
	{
		expediteur = owner;
		destinataire = dest;
		
		owner.openInventory(contenu);
	}
	
	public Inventory getContenu()
	{
		return contenu;
	}
	public Player getExpediteur()
	{
		return expediteur;
	}
	public String getNom()
	{
		return "Colis pour " + destinataire.getName();
	}
	
	public void envoyer()
	{
		//TODO: Pour economiser de la place, mettre le string inventaire en Base 64
		try
		{
			PreparedStatement ps = LaPoste.getMainConnection().prepareStatement(
					"INSERT INTO poste_colis (expediteur, destinataire, dateEnvoi, contenu, isLOG) VALUES (?, ?, NOW(), ?, ?)");
			ps.setString(1, expediteur.getUniqueId().toString());
			ps.setString(2, destinataire.getUniqueId().toString());
			ps.setString(3, InventoryUtils.InventoryToString(contenu));
			ps.setBoolean(4, false);
			ps.executeUpdate();
			//inserer le log aussi
			ps.setBoolean(4, true);
			ps.executeUpdate();
		}

		catch (SQLException ex)
		{
			ex.printStackTrace();
		}
	}
}
