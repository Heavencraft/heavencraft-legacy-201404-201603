package fr.tenkei.creaplugin.exceptions;

import fr.heavencraft.exceptions.HeavenException;

public class PlayerNotConnectedException extends HeavenException
{
	private static final long serialVersionUID = 1L;

	public PlayerNotConnectedException(String name)
	{
		super("Le joueur {%1$s} n'est pas connecté.", name);
	}
}