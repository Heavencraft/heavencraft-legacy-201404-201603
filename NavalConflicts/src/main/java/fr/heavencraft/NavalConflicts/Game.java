package fr.heavencraft.NavalConflicts;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import fr.heavencraft.NavalConflicts.Enums.Events;
import fr.heavencraft.NavalConflicts.Events.NCEndGame;
import fr.heavencraft.NavalConflicts.GameMechanics.Settings;
import fr.heavencraft.NavalConflicts.GameMechanics.Stats;
import fr.heavencraft.NavalConflicts.Handlers.Lobby;
import fr.heavencraft.NavalConflicts.Handlers.Lobby.GameState;
import fr.heavencraft.NavalConflicts.Handlers.Player.NCPlayer;
import fr.heavencraft.NavalConflicts.Handlers.Player.NCPlayerManager;
import fr.heavencraft.NavalConflicts.Handlers.Player.Team;
import fr.heavencraft.NavalConflicts.Messages.Msgs;

public class Game {

	/**
	 * End the game and depending on who won, do things...
	 * 
	 * @param DidHumansWin
	 *            - Did the humans win
	 */
	public static void endGame(Team winingTeam) {

		Lobby.setGameState(GameState.GameOver);

		NCEndGame e = new NCEndGame(winingTeam);
		Bukkit.getPluginManager().callEvent(e);

		for (Player u : Lobby.getPlayersInGame())
		{
			NCPlayer IP = NCPlayerManager.getNCPlayer(u);
			IP.getScoreBoard().showProperBoard();
			Stats.setPlayingTime(u.getName(), Stats.getPlayingTime(u.getName()) + NCPlayerManager.getNCPlayer(u).getPlayingTime());
			//TODO KillStreaks.handle(true, u);
		}
		if (winingTeam == Team.Blue)
		{

			ArrayList<String> winners = new ArrayList<String>();

			for (Player u : Lobby.getPlayersInGame())
			{
				NCPlayer IP = NCPlayerManager.getNCPlayer(u);

				Inventory IV = Bukkit.getServer().createInventory(null, InventoryType.PLAYER);
				for (ItemStack stack : IP.getInventory())
					if (stack != null)
						IV.addItem(stack);

				for (ItemStack is : Lobby.getActiveArena().getSettings().getRewordItems())
					IV.addItem(is);

				if (NCPlayerManager.getNCPlayer(u).isWinner())
				{
					winners.add(u.getName());
				}

				Stats.setScore(u.getName(), Stats.getScore(u.getName()) + Lobby.getActiveArena().getSettings().getScorePer(IP, Events.GameEnds));
				Stats.setPoints(u.getName(), Stats.getPoints(u.getName()));
				u.sendMessage("");
				u.sendMessage("");
				u.sendMessage("");
				u.sendMessage("");
				u.sendMessage("");
				u.sendMessage("");
				u.sendMessage("");
				u.sendMessage(Msgs.Format_Header.getString("<title>", "Game Over"));
				u.sendMessage("");
				u.sendMessage(Msgs.Game_Over_Humans_Win.getString());
				StringBuilder winnersS = new StringBuilder();
				int i = 0;
				for (String s : winners)
				{
					i++;
					winnersS.append(s);
					if (i == winners.size())
						winnersS.append(".");
					else
						winnersS.append(", ");
				}
				u.sendMessage(Msgs.Game_Over_Winners.getString("<winners>", winnersS.toString()));
				u.sendMessage("");
				u.sendMessage(Lobby.getActiveArena().getName() == null ? "Really? You couldn't even wait for a map to be voted for?" : Msgs.Game_Info_Arena.getString("<arena>", Lobby.getActiveArena().getName(), "<creator>", Lobby.getActiveArena().getCreator()));
				u.sendMessage("");
				u.sendMessage(Msgs.Format_Line.getString());
			}
		} else
		{
			for (Player u : Lobby.getPlayersInGame())
			{
				NCPlayer IP = NCPlayerManager.getNCPlayer(u);

				Stats.setScore(u.getName(), Stats.getScore(u.getName()) + Lobby.getActiveArena().getSettings().getScorePer(IP, Events.GameEnds));
				Stats.setPoints(u.getName(), Stats.getPoints(u.getName()));
				u.sendMessage("");
				u.sendMessage("");
				u.sendMessage("");
				u.sendMessage("");
				u.sendMessage("");
				u.sendMessage("");
				u.sendMessage("");
				u.sendMessage(Msgs.Format_Header.getString("<title>", "Game Over"));
				u.sendMessage("");
				u.sendMessage(Msgs.Game_Over_Zombies_Win.getString());
				u.sendMessage("");
				u.sendMessage(Msgs.Game_Info_Arena.getString("<arena>", Lobby.getActiveArena().getName(), "<creator>", Lobby.getActiveArena().getCreator()));
				u.sendMessage("");
				u.sendMessage(Msgs.Format_Line.getString());
			}
		}
		Lobby.reset();
		Bukkit.getScheduler().scheduleSyncDelayedTask(NavalConflicts.getInstance(), new Runnable()
		{

			@Override
			public void run() {

				Lobby.setGameState(GameState.InLobby);
				for (Player u : Lobby.getPlayersInGame())
					NCPlayerManager.getNCPlayer(u).tpToLobby();

				Bukkit.getScheduler().scheduleSyncDelayedTask(NavalConflicts.getInstance(), new Runnable()
				{

					@Override
					public void run() {

						if (Lobby.getPlayersInGame().size() >= Settings.getRequiredPlayers() && Lobby.getGameState() == GameState.InLobby)
							Lobby.timerStartVote();
					}
				}, 10 * 60);
			}
		}, 100L);

	}

	/**
	 * 
	 * @param p
	 *            - The Player
	 */
	public static void leaveGame(Player p) {
		NCPlayerManager.getNCPlayer(p).leaveNavalConflicts();
	}


	/**
	 * Selectionne les rouges au hasard.
	 */
	public static void chooseTeams() {
		
		int i = Lobby.getPlayersInGame().size();
		for (Player u : Lobby.getPlayersInGame())
		{
			NCPlayer up = NCPlayerManager.getNCPlayer(u);
			if(i % 2 == 0)
				up.setTeam(Team.Blue);
			else
				up.setTeam(Team.Red);			
			i--;
		}
		
	}
	
	/**
	 * Selectionne les rouges au hasard.
	 */
	public static void equilibrateTeams() {
		
		int i = Lobby.getPlayersInGame().size();
		for (Player u : Lobby.getPlayersInGame())
		{
			NCPlayer up = NCPlayerManager.getNCPlayer(u);
			if(i % 2 == 0)
				up.setTeam(Team.Blue);
			else
				up.setTeam(Team.Red);			
			i--;
		}
		
	}
	
	
}
