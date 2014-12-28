package fr.manu67100.heavenrp.laposte.handlers;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import fr.heavencraft.exceptions.HeavenException;
import fr.heavencraft.exceptions.PlayerNotConnectedException;
import fr.heavencraft.heavenrp.HeavenRP;
import fr.heavencraft.heavenrp.general.users.UserProvider;
import fr.heavencraft.utils.PlayerUtil;

public class Colis {
	private Inventory contenu;
	private String expediteur;
	private String destinataire;	
	private int _ID = 0;
	private final static String FORMAT_POSTE = "§4[§6La Poste§4] §6%1$s";
	
	public Colis(int ID)
	{
		try
		{
			PreparedStatement ps = HeavenRP.getConnection().prepareStatement(
					"SELECT * FROM `poste_colis`  WHERE `IDcolis` = ? AND `isLOG` = 0");
			ps.setInt(1, ID);
			ResultSet rs = ps.executeQuery();

			if (!rs.next())
			{
				this.contenu = null;
			}
			String expID = rs.getString("expediteur");
			String destID = rs.getString("destinataire");
			
			//this.expediteur = Bukkit.getServer().getPlayer(expID);
			try {
				this.expediteur = UserProvider.getUserByUUID(expID.toString()).getName();
			} catch (HeavenException e) {
				this.expediteur = "";
			}
			//this.destinataire =  Bukkit.getServer().getPlayer(destID);
			try {
				this.destinataire =UserProvider.getUserByUUID(destID.toString()).getName();
			} catch (HeavenException e) {
				this.destinataire = "";
			}
			this.contenu = PosteUtils.StringToInventory(rs.getString("contenu"));
			this._ID = ID;
		}
		catch (SQLException e)
		{
			this.contenu = null;
		}
	}

	public Colis(String expedit, String dest, Inventory inv)
	{
		this.expediteur = expedit;
		this.destinataire = dest;
		this.contenu = inv;
	}

	public Inventory getContenu()
	{
		return this.contenu;
	}
	public String getExpediteur()
	{
		return this.expediteur;
	}
	public String getNom()
	{
		return "Colis pour " + this.destinataire;
	}

	public void envoyer()
	{
		//TODO: Pour economiser de la plawce, mettre le string inventaire en Base 64
		try
		{
			
			PreparedStatement ps = HeavenRP.getConnection().prepareStatement(
					"INSERT INTO poste_colis (expediteur, destinataire, dateEnvoi, contenu, isLOG) VALUES (?, ?, NOW(), ?, ?)");
			try {
				ps.setString(1, UserProvider.getUserByName(this.expediteur).getUUID());
			} catch (HeavenException e) {
				ps.setString(1, "?");
			}
			try {
				ps.setString(2, UserProvider.getUserByName(this.destinataire).getUUID());
			} catch (HeavenException e) {
				ps.setString(2, "?");
			}
			ps.setString(3, PosteUtils.InventoryToString(this.contenu));
			ps.setBoolean(4, false);
			ps.executeUpdate();
			//inserer le log aussi
			ps.setBoolean(4, true);
			ps.executeUpdate();
					
			try {
				PlayerUtil.getPlayer(this.expediteur).sendMessage(String.format(FORMAT_POSTE, "Votre colis a été bien envoyé."));
			} catch (PlayerNotConnectedException e) {
			}
			try {
				PlayerUtil.getPlayer(this.destinataire).sendMessage(String.format(FORMAT_POSTE, "Vous avez recu un colis, vous pouvez le récuperer a la poste!"));
			} catch (PlayerNotConnectedException e) {
			}
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
			PreparedStatement ps = HeavenRP.getConnection().prepareStatement("DELETE FROM poste_colis WHERE `IDcolis` = ? AND `isLOG` = 0");
			ps.setInt(1, this._ID);
			ps.executeUpdate();
		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
		}
		
	}
	
	


}
