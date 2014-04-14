package fr.heavencraft.NavalConflicts.Events;

import java.util.ArrayList;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import fr.heavencraft.NavalConflicts.Handlers.Lobby;
import fr.heavencraft.NavalConflicts.Handlers.Player.Team;


public class NCEndGame extends Event{
	private Team winingTeam;
	
	public NCEndGame(Team winingTeam)
	{
		this.winingTeam = winingTeam;
	}
	
	private static final HandlerList handlers = new HandlerList();

	public HandlerList getHandlers() {
		return handlers;
	}
	public static HandlerList getHandlerList() {
		return handlers;
	}
	
	/**
	 * @return the players
	 */
	public ArrayList<Player> getPlayers() {
		return Lobby.getPlayersInGame();
	}

	/**
	 * @return the red
	 */
	public ArrayList<String> getRed() {
		return Lobby.getRed();
	}

	/**
	 * @return the Humans
	 */
	public ArrayList<String> getBlue() {
		return Lobby.getBlue();
	}

	public Team didBlueWin() {
		return winingTeam;
	}

}
