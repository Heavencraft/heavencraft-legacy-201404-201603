package fr.heavencraft.laposte.handlers;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import fr.heavencraft.laposte.LaPoste;

public class MenuColisRecus {

	
	public void Ouvrir(Player p)
	{
		//Récuperer tout les colis au destinataire p de la BDD.
	
		ArrayList<Colis> liste = getColisRecus(p.getUniqueId().toString());
		IconMenu menu = new IconMenu("Mes colis", 9,  new IconMenu.OptionClickEventHandler() {
            public void onOptionClick(IconMenu.OptionClickEvent event) {
                event.getPlayer().sendMessage("You have chosen " + event.getName());
                event.setWillClose(true);
            }
        }, LaPoste.getInstance());
		
		for(int i = 0; i >= liste.size(); i++)
		{
			menu.setOption(i, new ItemStack(Material.CHEST, 1), "Colis", "Colis de: " + liste.get(i).getExpediteur().getName());
		}
		
		menu.open(p);
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
