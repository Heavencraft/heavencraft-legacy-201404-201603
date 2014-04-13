package fr.heavencraft.NavalConflicts.Handlers.Player;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class NCPlayerManager {

	
	private static ArrayList<NCPlayer> players = new ArrayList<NCPlayer>();

	/**
	 * Create NCPlayer
	 * 
	 * @param IP
	 */
	public static void createNCPlayer(NCPlayer IP) {
		if (!players.contains(IP))
			players.add(IP);
	}

	/**
	 * Create NCPlayer
	 * 
	 * @param Player
	 * @return The new NCPlayer
	 */
	public static NCPlayer createNCPlayer(Player p) {
		NCPlayer IP = new NCPlayer(p);
		if (!players.contains(IP))
			players.add(IP);
		return IP;
	}

	/**
	 * Remove NCPlayer
	 * 
	 * @param Playername
	 */
	public static void removeNCPlayer(String playerName) {
		for (NCPlayer player : players)
		{
			if (player.getName().equalsIgnoreCase(playerName))
				players.remove(player);
		}
	}

	/**
	 * Remove NCPlayer
	 * 
	 * @param IP
	 */
	public static void removeNCPlayer(NCPlayer IP) {
		players.remove(IP);
	}

	/**
	 * Get NCPlayer
	 * 
	 * @param playername
	 */
	public static NCPlayer getNCPlayer(String playerName) {
		for (NCPlayer IP : players)
		{
			if (IP.getName().equalsIgnoreCase(playerName))
				return IP;
		}
		return createNCPlayer(Bukkit.getPlayer(playerName));
	}

	/**
	 * Create NCPlayer
	 * 
	 * @param Player
	 */
	public static NCPlayer getNCPlayer(Player p) {
		for (NCPlayer IP : players)
		{
			if (IP.getPlayer() == p)
				return IP;
		}
		return createNCPlayer(p);
	}
	
}
