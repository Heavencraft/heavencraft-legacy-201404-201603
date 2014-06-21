package fr.manu67100.heavenrp.laposte.handlers;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import fr.heavencraft.heavenrp.HeavenRP;

public class Colis {
	private Inventory contenu;
	private Player expediteur;
	private Player destinataire;	
	private int _ID = 0;
	private final static String FORMAT_POSTE = "�4[�6La Poste�4] �6%1$s";
	
	public Colis(int ID)
	{
		try
		{
			PreparedStatement ps = HeavenRP.getMainConnection().prepareStatement(
					"SELECT * FROM `poste_colis`  WHERE `IDcolis` = ? AND `isLOG` = 0");
			ps.setInt(1, ID);
			ResultSet rs = ps.executeQuery();

			if (!rs.next())
			{
				this.contenu = null;
			}
			UUID expID = UUID.fromString((rs.getString("expediteur")));
			UUID destID = UUID.fromString((rs.getString("destinataire")));
			
			this.expediteur = Bukkit.getServer().getPlayer(expID);
			this.destinataire =  Bukkit.getServer().getPlayer(destID);
			this.contenu = InventoryUtils.StringToInventory(rs.getString("contenu"));
			this._ID = ID;
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
		return this.contenu;
	}
	public Player getExpediteur()
	{
		return this.expediteur;
	}
	public String getNom()
	{
		return "Colis pour " + this.destinataire.getName();
	}

	public void envoyer()
	{
		//TODO: Pour economiser de la plawce, mettre le string inventaire en Base 64
		try
		{
			
			PreparedStatement ps = HeavenRP.getMainConnection().prepareStatement(
					"INSERT INTO poste_colis (expediteur, destinataire, dateEnvoi, contenu, isLOG) VALUES (?, ?, NOW(), ?, ?)");
			ps.setString(1, this.expediteur.getUniqueId().toString());
			ps.setString(2, this.destinataire.getUniqueId().toString());
			ps.setString(3, InventoryUtils.InventoryToString(this.contenu));
			ps.setBoolean(4, false);
			ps.executeUpdate();
			//inserer le log aussi
			ps.setBoolean(4, true);
			ps.executeUpdate();
			
			expediteur.sendMessage(String.format(FORMAT_POSTE, "Votre colis a �t� bien envoy�."));
		}

		catch (SQLException ex)
		{
			ex.printStackTrace();
		}
	}
	
	
	public int EmplacementsNecessaire()
	{
		return this.contenu.getContents().length;
	}

	public void openColis(Player p)
	{	
		for(ItemStack its : this.contenu.getContents())
		{
			if(its != null)
				p.getInventory().addItem(its);
		}
		
		try
		{
			PreparedStatement ps = HeavenRP.getMainConnection().prepareStatement("DELETE FROM poste_colis WHERE `IDcolis` = ? AND `isLOG` = 0");
			ps.setInt(1, this._ID);
			ps.executeUpdate();
		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
		}
		
	}


}
