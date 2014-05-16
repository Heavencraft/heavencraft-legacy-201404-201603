package fr.heavencraft.laposte.handlers;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import fr.heavencraft.laposte.InventoryUtils;
import fr.heavencraft.laposte.LaPoste;

public class Colis {
	private Inventory contenu;
	private Player expediteur;
	private Player destinataire;	

	public Colis(int ID)
	{
		try
		{
			PreparedStatement ps = LaPoste.getMainConnection().prepareStatement(
					"SELECT * FROM `poste_colis`  WHERE `IDcolis` = ? AND `isLOG` = 0");
			ps.setInt(1, ID);
			ResultSet rs = ps.executeQuery();

			if (!rs.next())
			{
				this.contenu = null;
			}
			
			this.expediteur = Bukkit.getPlayer((rs.getString("expediteur")));
			this.destinataire =  Bukkit.getPlayer(rs.getString("destinataire"));
			this.contenu = InventoryUtils.StringToInventory(rs.getString("contenu"));
			
		}
		catch (SQLException e)
		{
			this.contenu = null;
		}

	}

	public Colis(Player expedit, Player dest, Inventory inv)
	{
		this.expediteur = expedit;
		this.destinataire = dest;
		this.contenu = inv;
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
			
			if(contenu == null)
				Bukkit.broadcastMessage("Contenu null");
			
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
	
	
	public int EmplacementsNecessaire()
	{
		return (36 - contenu.getContents().length);
	}

	public void openColis(Player p)
	{	
		for(ItemStack its : contenu.getContents())
		{
			p.getInventory().addItem(its);
		}
		//TODO supprimer de la bdd.
	}


}
