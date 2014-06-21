package fr.lorgan17.lorganserver.exceptions;

import fr.heavencraft.exceptions.HeavenException;

public class NotEnoughNuggetsException extends HeavenException
{
	private static final long serialVersionUID = 1L;

	public NotEnoughNuggetsException(int amount)
	{
		super("Vous n'avez pas assez de pépites d'or sur vous. Il vous en faut {" + amount + "}.");
	}
}
