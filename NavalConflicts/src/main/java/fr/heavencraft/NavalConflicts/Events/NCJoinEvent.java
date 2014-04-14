package fr.heavencraft.NavalConflicts.Events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class NCJoinEvent extends Event {

	private Player p;

	public NCJoinEvent(Player p)
	{
		this.p = p;
	}

	private static final HandlerList handlers = new HandlerList();

	public HandlerList getHandlers() {
		return handlers;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}

	/**
	 * @return the p
	 */
	public Player getP() {
		return p;
	}
}
