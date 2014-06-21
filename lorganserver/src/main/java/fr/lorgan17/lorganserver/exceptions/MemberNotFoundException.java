package fr.lorgan17.lorganserver.exceptions;

import fr.heavencraft.exceptions.HeavenException;

public class MemberNotFoundException extends HeavenException
{
	private static final long serialVersionUID = 1L;

	public MemberNotFoundException(int id, String name)
	{
		super("Le joueur {" + name + "} n'est pas membre de la protection {" + id + "}.");
	}
}