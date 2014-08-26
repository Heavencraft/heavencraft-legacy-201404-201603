package fr.heavencraft.rpg.donjon;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class EndRoomEvent extends Event{
	
	private String dungeon_name;
	private static final HandlerList handlers = new HandlerList();
	
	public EndRoomEvent(String dg_name)
	{
		this.dungeon_name = dg_name;
	}
	
	@Override
	public HandlerList getHandlers() {
		return handlers;
	}
	
	public String getDungeonName()
	{
		return this.dungeon_name;
	}
}
