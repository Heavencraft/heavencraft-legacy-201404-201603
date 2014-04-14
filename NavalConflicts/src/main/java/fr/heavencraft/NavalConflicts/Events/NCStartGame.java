package fr.heavencraft.NavalConflicts.Events;

import java.util.ArrayList;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import fr.heavencraft.NavalConflicts.Handlers.Arena;
import fr.heavencraft.NavalConflicts.Handlers.Lobby;

public class NCStartGame extends Event {

	public NCStartGame()
	{
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
	 * @return the Red
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

	/**
	 * @return the infecting time
	 */
	public int getTimeLimit() {
		return Lobby.getActiveArena().getSettings().getGameTime();
	}

	/**
	 * @return active arena
	 */
	public Arena getArena() {
		return Lobby.getActiveArena();
	}

}
