package fr.heavencraft.laposte.handlers;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public class BoiteAuxLettres {

	private Player proprietaire;
	private Location position;
	
	
	public BoiteAuxLettres(Player p, Location loc)
	{
		proprietaire = p;
		position = loc;
	}
	
	public Player getProprietaire()
	{
		return proprietaire;
	}
	
	public Location getPosition()
	{
		return position;
	}
	
	
}
