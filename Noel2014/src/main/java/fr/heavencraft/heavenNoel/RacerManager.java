package fr.heavencraft.heavenNoel;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import fr.heavencraft.ChatUtil;
import fr.heavencraft.exceptions.PlayerNotConnectedException;

public class RacerManager {
	private static ArrayList<Racer> players = new ArrayList<Racer>();
	
	private final static String Run_done_in = "Performance: {%1$s} secondes!";
	private final static String Message_Start = "Il me faut ces codes ! Suis les poteaux et ne perds pas de temps! Manu";
	private final static String Message_Done = "Vos actions d'aujourd'hui ont permis de débloquer un des 5 codes nécessaires pour sauver le serveur, il m'a bien été transmis, merci. Manu";
	private final static String New_Personal_Record = "Vous avez fait un nouveau temps record personnel!";
	
	private final static Location lobby = new Location(Bukkit.getWorld("sommet"), 69, 71, -2);
	private final static Location start_run = new Location(Bukkit.getWorld("sommet"), 31, 26, -45);
	
	
	public static void handleRacerStart(final Player p)
	{
		Racer hp = getRacer(p.getName());
		if(hp == null)
		{
			createRacer(new Racer(p));
			hp = getRacer(p.getName());
			return;
		}
		try {
			ChatUtil.sendMessage(p.getName(), Message_Start);
		} catch (PlayerNotConnectedException e) {
		}
		p.teleport(start_run);
		Calendar now = Calendar.getInstance();
		hp.setStarttime(now.getTimeInMillis());
	}

	public static void handleRacerEnd(Racer r)
	{
		Calendar now = Calendar.getInstance();
		long delta = (now.getTimeInMillis()-r.getStarttime());
		//Logique de fin de course
			
		if(!r.isAlreadyPerformed())
			ChatUtil.sendMessage(r.getPlayer(), Message_Done);
	
		boolean isNewRecord = r.savePerformanceRecord(delta);
		if(isNewRecord)
			ChatUtil.sendMessage(r.getPlayer(), New_Personal_Record);
		
		DateFormat formatter = new SimpleDateFormat("ss,SSS");

		
		ChatUtil.sendMessage(r.getPlayer(), Run_done_in, formatter.format(new Date(delta)));
		r.getPlayer().teleport(lobby);
		RacerManager.removeRacer(r);
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
