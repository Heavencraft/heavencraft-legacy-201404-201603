package fr.heavencraft.heavenguard.exceptions;

import fr.heavencraft.exceptions.HeavenException;

public class RegionNotFoundException extends HeavenException
{
	private static final long serialVersionUID = 1L;

	public RegionNotFoundException(String name)
	{
		super("La protection {%1$s} n'existe pas.", name);
	}

	public RegionNotFoundException(String world, int x, int y, int z)
	{
		super("Aucune protection trouvée en {(%1$s, %2$s, %3$s, %4$s)}.", world, x, y, z);
	}
}