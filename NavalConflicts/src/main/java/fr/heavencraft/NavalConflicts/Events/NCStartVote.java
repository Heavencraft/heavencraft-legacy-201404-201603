package fr.heavencraft.NavalConflicts.Events;

import java.util.ArrayList;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import fr.heavencraft.NavalConflicts.GameMechanics.Settings;
import fr.heavencraft.NavalConflicts.Handlers.Arena;
import fr.heavencraft.NavalConflicts.Handlers.Lobby;

public class NCStartVote extends Event{

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
	 * @return the voting time
	 */
	public int getTimeLimit() {
		return Settings.getVotingTime();
	}

	/**
	 * @return all arenas
	 */
	public ArrayList<Arena> getArenas() {
		return Lobby.getArenas();
	}
}
