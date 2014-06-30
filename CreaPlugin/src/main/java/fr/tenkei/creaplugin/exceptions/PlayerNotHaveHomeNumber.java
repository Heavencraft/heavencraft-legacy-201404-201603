package fr.tenkei.creaplugin.exceptions;

import fr.heavencraft.exceptions.HeavenException;

public class PlayerNotHaveHomeNumber extends HeavenException
{
	private static final long serialVersionUID = 1L;

	public PlayerNotHaveHomeNumber(int number)
	{
		super("Vous n'avez pas le home {%1$s}. Vous devez l'acheter.", number);
	}
}