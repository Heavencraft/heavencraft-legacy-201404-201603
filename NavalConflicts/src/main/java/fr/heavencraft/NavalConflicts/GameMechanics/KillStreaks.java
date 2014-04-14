package fr.heavencraft.NavalConflicts.GameMechanics;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import fr.heavencraft.NavalConflicts.Utils;
import fr.heavencraft.NavalConflicts.Enums.Events;
import fr.heavencraft.NavalConflicts.Handlers.Lobby;
import fr.heavencraft.NavalConflicts.Handlers.Player.NCPlayer;
import fr.heavencraft.NavalConflicts.Handlers.Player.NCPlayerManager;
import fr.heavencraft.NavalConflicts.Messages.Msgs;

public class KillStreaks {
	/**
	 * The method that is in charge of handling a players killstreaks
	 * 
	 * This includes when a player gets a kill and when a player dies
	 * 
	 * @param killed
	 *            - Was this player killed
	 * @param p
	 *            - The player
	 */
	public static void handle(boolean killed, Player p) {
		NCPlayer IP = NCPlayerManager.getNCPlayer(p);

		// If the player was killed check if it is their new highest killstreak
		if (killed)
		{
			if (IP.getKillstreak() > Stats.getHighestKillStreak(p.getName()))
				Stats.setHighestKillStreak(p.getName(), IP.getKillstreak());

		}
		// If the player didn't just die it means they we're the one who got a
		// kill, and so now we update their killstreak and reward what needs to
		// be rewarded
		else
		{
			IP.setKillstreak(IP.getKillstreak() + 1);
			int KillStreak = IP.getKillstreak();

			// To prevent spam we only show when they get 3 or more as a
			// killstreak
			if (KillStreak >= 3)
				for (Player u: Lobby.getPlayersInGame())
					u.sendMessage(Msgs.Game_KillStreak_Value.getString("<player>", p.getName(), "<killstreak>", String.valueOf(KillStreak)));

			// Does their class have a reward for getting a killstreak of this
			// value
			if (IP.getNCClass(IP.getTeam()).getKillstreaks().containsKey(IP.getKillstreak()))
			{
				p.getInventory().addItem(IP.getNCClass(IP.getTeam()).getKillstreaks().get(IP.getKillstreak()));
				ItemStack is = IP.getNCClass(IP.getTeam()).getKillstreaks().get(IP.getKillstreak());
				p.sendMessage(Msgs.Game_KillStreak_Reward.getString("<item>", is.getItemMeta().getDisplayName() != null ? is.getItemMeta().getDisplayName() : Utils.getWord(is.getType().name())));
			}
			// Now we just update their points and score accordingly
			IP.setPoints(IP.getPoints());
			IP.setScore(IP.getScore() + Lobby.getActiveArena().getSettings().getScorePer(IP, Events.Kill));

			// But now all the survivors and other Infecteds need their share of
			// the kills points and score
			for (Player u : Lobby.getPlayersInGame())
			{
				if (Lobby.isBlue(u))
				{
					IP.setPoints(IP.getPoints());
					IP.setScore(IP.getScore() + Lobby.getActiveArena().getSettings().getScorePer(IP, Events.Survive));
				} else
				{
					IP.setPoints(IP.getPoints());
					IP.setScore(IP.getScore() + Lobby.getActiveArena().getSettings().getScorePer(IP, Events.Infected ));
				}

			}
		}
	}
}
