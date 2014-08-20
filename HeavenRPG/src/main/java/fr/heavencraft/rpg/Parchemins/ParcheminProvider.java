package fr.heavencraft.rpg.Parchemins;

import java.util.ArrayList;

import org.bukkit.inventory.ItemStack;

import fr.heavencraft.rpg.Parchemins.Parchemins.ParcheminAuraDeLaBienfaisansce;
import fr.heavencraft.rpg.Parchemins.Parchemins.ParcheminLePetitPetDuNord;
import fr.heavencraft.rpg.Parchemins.Parchemins.ParcheminLeSouffleDuNecromentien;
import fr.heavencraft.rpg.Parchemins.Parchemins.ParcheminPousseeQuantique;
import fr.heavencraft.rpg.Parchemins.Parchemins.ParcheminTonnereDivin;

public class ParcheminProvider {
	private static ArrayList<IParchemin> parchemins = new ArrayList<IParchemin>();
	
	public static void LoadParchemins()
	{
		addParchemin(new ParcheminLePetitPetDuNord());
		addParchemin(new ParcheminTonnereDivin());
		addParchemin(new ParcheminPousseeQuantique());
		addParchemin(new ParcheminAuraDeLaBienfaisansce());
		addParchemin(new ParcheminLeSouffleDuNecromentien());
	}
	
	
	public static void addParchemin(IParchemin p)
	{
		if(!parchemins.contains(p))
			parchemins.add(p);
	}
	
	public static void removeParchemin(IParchemin p)
	{
		if(parchemins.contains(p))
			parchemins.remove(p);
	}
	
	public static ArrayList<IParchemin> getParchemins()
	{
		return parchemins;
	}
	
	/**
	 * Retourne le parchemin correspondant a l'item, en vérifiant le nom de celui-ci
	 * @param item
	 * @return
	 */
	public static IParchemin getParcheminByItem(ItemStack item)
	{
		for(IParchemin p: parchemins)
		{
			if(p.getItem().getItemMeta().getDisplayName().equalsIgnoreCase(item.getItemMeta().getDisplayName()))
				return p;
		}
		return null;
	}
	
}