package fr.tenkei.creaplugin.exceptions;

import fr.heavencraft.exceptions.HeavenException;

public class RegionNotFoundException extends HeavenException
{
	private static final long serialVersionUID = 1L;

	public RegionNotFoundException(int id)
	{
		super("La protection {%1$s} n'existe pas.", id);
	}
}