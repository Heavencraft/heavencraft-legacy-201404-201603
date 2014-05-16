package fr.heavencraft.laposte.listeners;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.material.MaterialData;

import fr.heavencraft.laposte.InventoryUtils;
import fr.heavencraft.laposte.LaPoste;
import fr.heavencraft.laposte.Utils;
import fr.heavencraft.laposte.handlers.Colis;
import fr.heavencraft.laposte.handlers.MenuColisRecus;
import fr.heavencraft.laposte.handlers.popupMenu.MenuItem;
import fr.heavencraft.laposte.handlers.popupMenu.PopupMenu;
import fr.heavencraft.laposte.handlers.popupMenu.PopupMenuAPI;


public class SignListener implements Listener{
	private String _permission;
	private String _tag;

	public SignListener() 
	{
		_permission = "LaPoste.admin";
		_tag = "[" + "Poste" + "]";
		Bukkit.getPluginManager().registerEvents(this, LaPoste.getInstance());
	}

	@EventHandler(ignoreCancelled = true)
	private void onSignChange(SignChangeEvent event)
	{
		Player player = event.getPlayer();

		if (player == null ||(!player.hasPermission(_permission) && player.isOp() == false) || !event.getLine(0).equalsIgnoreCase(_tag))
			return;

		event.setLine(0, ChatColor.GREEN + _tag);
		Utils.sendMessage(player, "Le panneau {%1$s} de poste a été placé correctement.", _tag);
		return;

	}

	@EventHandler(ignoreCancelled = false)
	public void PostSignClick(PlayerInteractEvent e){

		if(e.getAction() != Action.RIGHT_CLICK_BLOCK)
			return;

		if (e.getClickedBlock().getType() != Material.SIGN_POST && e.getClickedBlock().getType() != Material.WALL_SIGN && e.getClickedBlock().getType() != Material.SIGN)
			return;

		Sign sign = (Sign) e.getClickedBlock().getState();

		if (!sign.getLine(0).equals(ChatColor.GREEN + _tag))
			return;

		//TODO Ouvrir l'inventaire
		e.getPlayer().sendMessage("Overture de l'inventaire recu");
//		MenuColisRecus menu = new MenuColisRecus();
//		menu.Ouvrir(e.getPlayer());
		
		ArrayList<Colis> mesColis = new ArrayList<Colis>();
		
		for(String id : getColisRecus(e.getPlayer().getUniqueId().toString()))
		{
			Bukkit.broadcastMessage(id);
			mesColis.add(new Colis(Integer.parseInt(id)));
		}
		
		
		PopupMenu menuMesColis = PopupMenuAPI.createMenu("Mes Colis Recus", 18);
		int index = 0;
		for(final Colis colis : mesColis)
		{
			
			
			
			MenuItem bouton = new MenuItem("Colis de" + colis.getExpediteur().getName(), new MaterialData(Material.CHEST))
			{
				@Override
				public void onClick(Player player) {
					//Que ce passe t'il lorsque l'on clique sur le bouton?
					Bukkit.broadcastMessage("ON A CLIQUé SUR: " + colis.getNom());
					
				}
			};
			
			bouton.setDescriptions(Utils.wrapWords("Colis de:" + colis.getExpediteur().getName(), 40));
			menuMesColis.addMenuItem(bouton, index);
			index ++;
		}
		
		menuMesColis.openMenu(e.getPlayer());
	}

	
	private ArrayList<String> getColisRecus(String UUID)
	{
		ArrayList<String> liste = new ArrayList<String>();
		
		try
		{
			PreparedStatement ps = LaPoste.getMainConnection().prepareStatement(
					"SELECT `IDcolis` FROM `poste_colis`  WHERE `destinataire` = ? AND `isLOG` = '0'");
			ps.setString(1, UUID);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next())
			{	
//				liste.add(new Colis(rs.getInt("IDcolis")));
				liste.add(rs.getString("IDcolis"));
			}		
			
			return liste;
		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
			return null;
		}
	}
	
}


