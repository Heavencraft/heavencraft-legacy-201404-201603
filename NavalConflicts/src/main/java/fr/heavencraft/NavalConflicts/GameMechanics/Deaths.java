package fr.heavencraft.NavalConflicts.GameMechanics;


import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import fr.heavencraft.NavalConflicts.Enums.DeathType;
import fr.heavencraft.NavalConflicts.Events.NCDeathEvent;
import fr.heavencraft.NavalConflicts.Extras.Pictures;
import fr.heavencraft.NavalConflicts.Handlers.Lobby;
import fr.heavencraft.NavalConflicts.Handlers.Player.NCPlayer;
import fr.heavencraft.NavalConflicts.Handlers.Player.NCPlayerManager;
import fr.heavencraft.NavalConflicts.Handlers.Player.Team;
import fr.heavencraft.NavalConflicts.Messages.KillMessages;
import fr.heavencraft.NavalConflicts.Messages.Msgs;

public class Deaths {

	
	/**
	 * used to process a players death
	 * 
	 * @param death
	 *            - The death type
	 * @param killer
	 *            - the killer as a player
	 * @param killed
	 *            - the killed as a player
	 */
	public static void playerDies(DeathType death, Player killer, Player killed) {
		NCDeathEvent e = new NCDeathEvent(killer, killed, death);
		Bukkit.getPluginManager().callEvent(e);

		//TODO image délimination
//		// --> Picture deaths
//		if (death == DeathType.Other && NCPlayerManager.getNCPlayer(killed).getTeam() == Team.Red)
//		{
//		} else
//		{
//			String killMessage = KillMessages.getKillMessage(killer, killed, death, true);
//
//			if (Settings.PictureEnabled() && Lobby.isBlue(killed) && Lobby.getTeam(Team.Blue).size() > 1)
//			{
//				killMessage = KillMessages.getKillMessage(killer, killed, death, false);
//				String[] face = Pictures.getRed();
//				face[2] = face[2] + "     " + Msgs.Picture_Infected_You.getString();
//				face[3] = face[3] + "     " + Msgs.Picture_Infected_To_Win.getString();
//				killed.sendMessage(face);
//
//				for (String name : Lobby.getInGame())
//				{
//					Player u = Bukkit.getPlayer(name);
//					if (u != killed)
//						u.sendMessage(Msgs.Format_Prefix.getString() + killMessage);
//				}
//			} else
//				for (String name : Lobby.getInGame())
//					Bukkit.getPlayer(name).sendMessage(killMessage);
//		}
//		// <--

		NCPlayer NCKiller = null;
		NCPlayer NCKilled = null;
		if (killer != null)
		{
			NCKiller = NCPlayerManager.getNCPlayer(killer);
			NCKiller.updateStats(1, 0);
			KillStreaks.handle(false, killer);

		}

		if (killed != null)
		{
			NCKilled = NCPlayerManager.getNCPlayer(killed);
			NCKilled.updateStats(0, 1);

			KillStreaks.handle(true, killed);

			killed.playSound(killed.getLocation(), Sound.ZOMBIE_PIG_DEATH, 1, 1);
			NCKilled.respawn();
			//TODO Equip.equip(killed);
		}

		for (Player u : Lobby.getPlayersInGame())
		{
			NCPlayer up = NCPlayerManager.getNCPlayer(u);
			up.getScoreBoard().showProperBoard();
		}

	}

	public static void playerDiesWithoutDeathStat(Player killed) {

		NCPlayer NCKilled = NCPlayerManager.getNCPlayer(killed);

		// --> Picture deaths
		if (NCKilled.getTeam() != Team.Red)
		{
			String killMessage = KillMessages.getKillMessage(null, killed, DeathType.Other, true);

			if (Settings.PictureEnabled())
			{
				killMessage = KillMessages.getKillMessage(null, killed, DeathType.Other, false);
				String[] face = Pictures.getRed();
				face[2] = face[2] + "     " + Msgs.Picture_Infected_You.getString();
				face[3] = face[3] + "     " + Msgs.Picture_Infected_To_Win.getString();
				killed.sendMessage(face);

				for (Player u : Lobby.getPlayersInGame())
				{;
					if (u != killed)
						u.sendMessage(Msgs.Format_Prefix.getString() + killMessage);
				}
			}
			else
				for (Player u : Lobby.getPlayersInGame())
					u.sendMessage(killMessage);
		}
		// <--

		if (NCKilled.getTeam() == Team.Blue)
		{
			//TODO NCKilled.Infect();
			NCKilled.respawn();

			//if (Lobby.getBlue().size() == 0 && Lobby.getGameState() == GameState.Started)
				//TODO Game.endGame(false);

		} else
		{
			killed.playSound(killed.getLocation(), Sound.ZOMBIE_PIG_DEATH, 1, 1);
			NCKilled.respawn();
			//TODO Equip.equip(killed);
		}

		for (Player u: Lobby.getPlayersInGame())
		{
			NCPlayer up = NCPlayerManager.getNCPlayer(u);
			up.getScoreBoard().showProperBoard();
		}
	}
}
