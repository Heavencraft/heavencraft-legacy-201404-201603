package fr.heavencraft.heavenguard.datamodel;

import java.util.Collection;

public interface Region
{
	String getName();

	boolean contains(String world, int x, int y, int z);

	/**
	 * @return the parent region, or null if this region don't have parent.
	 */
	Region getParent();

	void addMember(String name, boolean owner);

	boolean isMember(String name, boolean owner);

	void removeMember(String name, boolean owner);

	Collection<String> getMembers(boolean owner);

}