package fr.heavencraft.heavenNoel;

import java.util.ArrayList;
import java.util.Calendar;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class RacerManager {
	private static ArrayList<Racer> players = new ArrayList<Racer>();
	private static Calendar now = Calendar.getInstance();
	
	private final static String Message_Done = "Vos actions d'aujourd'hui ont permis de débloquer un des 5 codes nécessaires pour sauver le serveur, il m'a bien été transmis, merci. Manu";
	private final static Location start_run = new Location(Bukkit.getWorld("sommet"), 0, 64, 0);
	
	
	public static void handleRacerStart(final Player p)
	{
		Racer hp = getRacer(p.getName());
		if(hp == null)
		{
			createRacer(new Racer(p));
			hp = getRacer(p.getName());
			return;
		}
		
		hp.setStarttime(now.getTimeInMillis());
		//TODO Logique de téléportation
	}

	public static void handleRacerEnd(Racer r)
	{
		//TODO Logique de fin de course
		int delta = (int)(now.getTimeInMillis()-r.getStarttime());
		if(!r.isAlreadyPerformed())
			r.setPerformanceRecord(delta);
		
		
		
	}
	
	public static void createRacer(Racer p)
	{
		if(!players.contains(p))
			players.add(p);
	}
	public static Racer createRacer(Player p)
	{
		Racer rp = new Racer(p);
		if(!players.contains(rp))
			players.add(rp);
		return rp;
	}

	public static void removeRacer(String playerName) {
		for (Racer player : players)
			if (player.getPlayer().getName().equalsIgnoreCase(playerName))
				players.remove(player);
	}
	public static void removeRacer(Racer IP) {
		players.remove(IP);
	}
	
	public static Racer getRacer(String playerName) {
		for (Racer IP : players)
			if(IP.getPlayer() != null)
				if (IP.getPlayer().getName().equalsIgnoreCase(playerName))
					return IP;
		return null;
	}
	
	public static Racer getRacer(Player p) {
		for (Racer IP : players)
			if (IP.getPlayer() == p)
				return IP;
		return null;
	}
}
