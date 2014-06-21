package fr.lorgan17.lorganserver.exceptions;

import fr.heavencraft.exceptions.HeavenException;

public class RegionNotFoundException extends HeavenException
{
	private static final long serialVersionUID = 1L;

	public RegionNotFoundException(int id)
	{
		super("La protection {" + id + "} n'existe pas.");
	}
}