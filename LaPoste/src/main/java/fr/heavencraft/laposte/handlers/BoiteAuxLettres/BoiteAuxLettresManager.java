package fr.heavencraft.laposte.handlers.BoiteAuxLettres;

import java.util.ArrayList;

import org.bukkit.Location;

public class BoiteAuxLettresManager {

	
	private static ArrayList<BoiteAuxLettres> boites = new ArrayList<BoiteAuxLettres>();
	
	
	public static void createBoiteAuxLettres(BoiteAuxLettres bal)
	{
		if(!boites.contains(bal))
			boites.add(bal);
	}
	public static void removeBoiteAuxLettres(BoiteAuxLettres bal)
	{
		if(boites.contains(bal))
			boites.remove(bal);
	}
	
	public static ArrayList<BoiteAuxLettres> getBoites()
	{
		return boites;
	}
	
	public static BoiteAuxLettres getBoiteAuxLettres(Location loc)
	{
		for(BoiteAuxLettres bal : getBoites())
		{
			if(bal.getPosition() == loc)
				return bal;
		}
		return null;
	}
	
}
