package fr.heavencraft.NavalConflicts.Messages;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import fr.heavencraft.NavalConflicts.Enums.DeathType;
import fr.heavencraft.NavalConflicts.GameMechanics.Stats;
import fr.heavencraft.NavalConflicts.Handlers.Player.NCPlayer;
import fr.heavencraft.NavalConflicts.Handlers.Player.NCPlayerManager;
import fr.heavencraft.NavalConflicts.Handlers.Player.Team;
import fr.heavencraft.NavalConflicts.Tools.Files;

import java.util.List;
import java.util.Random;

public class KillMessages {

	/**
	 * 
	 * @param killer
	 *            - The Killer
	 * @param killed
	 *            - The Killed
	 * @param death
	 *            - The DeathType
	 * @param prefix
	 *            - Do we want a prefix for this death
	 * @return The Kill Message
	 */
	public static String getKillMessage(Player killer, Player killed, DeathType death, Boolean prefix) {

		NCPlayer IP = null;
		Team team = Team.Red;
		// Figure out what team the killer was on
		if (killer != null)
		{
			IP = NCPlayerManager.getNCPlayer(killer);
			team = IP.getTeam();
		} else
		{
			if (NCPlayerManager.getNCPlayer(killed).getTeam() == Team.Blue)
				team = Team.Red;
			else
				team = Team.Blue;
		}
		// Get the message
		String msg = getKill(team, death);

		// Replace color codes, and killer and killed names
		msg = ChatColor.translateAlternateColorCodes('&', msg.replaceAll("&x", "&" + String.valueOf(RandomChatColor.getColor().getChar())).replaceAll("&y", "&" + String.valueOf(RandomChatColor.getFormat().getChar())));
		if (killer != null)
			msg = msg.replaceAll("<killer>", killer.getName() + "(" + Stats.getScore(killer.getName()) + ")");
		if (killed != null)
			msg = msg.replaceAll("<killed>", killed.getName() + "(" + Stats.getScore(killed.getName()) + ")");
		return (prefix ? Msgs.Format_Prefix.getString() : "") + msg;
	}

	/**
	 * 
	 * @param team
	 *            - The killers team
	 * @param death
	 *            - The death type
	 * @return the death message
	 */
	private static String getKill(Team team, DeathType death) {
		String message;
		List<String> list;
		// If the death type is other it means they suicided or just joined late
		// in the game
		if (death == DeathType.Other)
			list = Files.getMessages().getStringList("Suicides." + team.toString());
		else
			list = Files.getMessages().getStringList("Kills." + team.toString() + "." + death.toString());
		Random r = new Random();
		int i = r.nextInt(list.size());
		message = list.get(i);
		return message;
	}
	
}
