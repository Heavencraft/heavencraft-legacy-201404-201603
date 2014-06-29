package fr.tenkei.creaplugin.exceptions;

import fr.heavencraft.exceptions.HeavenException;

public class UserNotFoundException extends HeavenException
{
	private static final long serialVersionUID = 1L;

	public UserNotFoundException(String name)
	{
		super("Le joueur {%1$s} n'existe pas.", name);
	}
}