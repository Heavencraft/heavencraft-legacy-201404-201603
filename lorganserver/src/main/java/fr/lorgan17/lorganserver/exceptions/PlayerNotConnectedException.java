package fr.lorgan17.lorganserver.exceptions;

import fr.heavencraft.exceptions.HeavenException;

public class PlayerNotConnectedException extends HeavenException
{
	private static final long serialVersionUID = 1L;

	public PlayerNotConnectedException(String name)
	{
		super("Le joueur {" + name + "} n'est pas connecté.");
	}
}