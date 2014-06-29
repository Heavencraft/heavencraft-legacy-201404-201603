package fr.tenkei.creaplugin.exceptions;

import fr.heavencraft.exceptions.HeavenException;

public class MemberNotFoundException extends HeavenException
{
	private static final long serialVersionUID = 1L;

	public MemberNotFoundException(int id, String name)
	{
		super("Le joueur {%1$s} n'est pas membre de la protection {%2$s}.", name, id);
	}
}