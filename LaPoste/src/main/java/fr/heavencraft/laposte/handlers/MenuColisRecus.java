package fr.heavencraft.laposte.handlers;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.material.MaterialData;

import fr.heavencraft.laposte.LaPoste;
import fr.heavencraft.laposte.Utils;
import fr.heavencraft.laposte.handlers.popupMenu.MenuItem;
import fr.heavencraft.laposte.handlers.popupMenu.PopupMenu;
import fr.heavencraft.laposte.handlers.popupMenu.PopupMenuAPI;

public class MenuColisRecus {

	
	public void Ouvrir(Player p)
	{
		//Récuperer tout les colis au destinataire p de la BDD.
	
		ArrayList<Colis> liste = getColisRecus(p.getUniqueId().toString());
		
		
		PopupMenu menuColis = PopupMenuAPI.createMenu("Mes colis", 9);
		int i = 0;
		
		for(final Colis colis : liste)
		{
			
			MenuItem menuItem = new MenuItem("Colis de" + colis.getExpediteur().getName(), new MaterialData(Material.CHEST))
			{
				@Override
				 public void onClick(Player player) {
                    
					// Le joueur a t'il suffisament de place dans l'inventaire?
					if(Utils.getEmptySlots(player.getInventory()) < colis.EmplacementsNecessaire())
					{
						colis.openColis(player);
						getMenu().closeMenu(player);
					}
					else
					{
						player.sendMessage("PAS ASSEZ DEPLACE");
					}
					//TODO changer le message
                   
                }
			};
			
			menuItem.setDescriptions(Utils.wrapWords("Colis de:" + colis.getExpediteur().getName(), 40));
			menuColis.addMenuItem(menuItem, i);
			
			i++;
		}
		
		
		
		
		
		
		
//		IconMenu menu = new IconMenu("Mes colis", 9,  new IconMenu.OptionClickEventHandler() {
//            public void onOptionClick(IconMenu.OptionClickEvent event) {
//                event.getPlayer().sendMessage("You have chosen " + event.getName());
//                event.setWillClose(true);
//            }
//        }, LaPoste.getInstance());
		
//		IconMenu menu = new IconMenu("Mes colis", 9, OnColisClick(IconMenu.OptionClickEventHandler),LaPoste.getInstance());
		
//		for(int i = 0; i >= liste.size(); i++)
//		{
//			menu.setOption(i, new ItemStack(Material.CHEST, 1), "Colis", "Colis de: " + liste.get(i).getExpediteur().getName());
//		}
//		
//		menu.open(p);
	}
	
	
	private ArrayList<Colis> getColisRecus(String UUID)
	{
		ArrayList<Colis> liste = new ArrayList<Colis>();
		
		try
		{
			PreparedStatement ps = LaPoste.getMainConnection().prepareStatement(
					"SELECT `IDcolis` FROM `poste_colis`  WHERE `destinataire` = ?");
			ps.setString(1, UUID);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next())
			{
				liste.add(new Colis(rs.getInt("IDcolis")));
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
