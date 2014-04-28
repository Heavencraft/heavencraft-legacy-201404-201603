package fr.heavencraft.laposte.handlers;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class Colis {
	private Inventory contenu;
	private Player proprietaire;
	private Player destinataire;
	
	public Colis(Player owner, Player dest)
	{
		proprietaire = owner;
		destinataire = dest;
		
		owner.openInventory(contenu);
	}
	
	public Inventory getContenu()
	{
		return contenu;
	}
	public Player getProprio()
	{
		return proprietaire;
	}
	public String getNom()
	{
		return "Colis pour " + destinataire.getName();
	}
	
	public void envoyer()
	{
		//TODO: Envoyer le colis
	}
}
