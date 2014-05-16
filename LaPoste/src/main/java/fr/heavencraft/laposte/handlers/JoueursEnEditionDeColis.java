package fr.heavencraft.laposte.handlers;

import java.util.HashMap;

import org.bukkit.entity.Player;

public class JoueursEnEditionDeColis {
	private static HashMap<Player, Player> JoueursEnEdit = new HashMap<Player,Player>();
	
	public static void addPlayer(Player p, Player dest)
	{
		JoueursEnEdit.put(p, dest);
	}
	
	public static boolean isEditing(Player p)
	{
		return JoueursEnEdit.containsKey(p);
	}
	
	public static Player getDestinataire(Player p)
	{
		return JoueursEnEdit.get(p);
	}
	
	public static void removePlayer(Player p)
	{
		JoueursEnEdit.remove(p);
	}
	
}
