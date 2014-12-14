package fr.heavencraft.heavenguard.api;

import java.util.Collection;
import java.util.UUID;

public interface Region
{
	int getId();
	
	String getName();

	boolean contains(String world, int x, int y, int z);
	
	boolean canBuilt(UUID player);

	/**
	 * @return the parent region, or null if this region don't have parent.
	 */
	Region getParent();

	/*
	 * Members
	 */

	void addMember(UUID id, boolean owner);

	boolean isMember(UUID id, boolean owner);

	void removeMember(UUID id, boolean owner);

	Collection<UUID> getMembers(boolean owner);
}