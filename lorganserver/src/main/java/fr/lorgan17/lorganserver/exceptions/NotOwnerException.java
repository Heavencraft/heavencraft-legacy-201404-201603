package fr.lorgan17.lorganserver.exceptions;

import fr.heavencraft.exceptions.HeavenException;

public class NotOwnerException extends HeavenException
{
	private static final long serialVersionUID = 1L;

	public NotOwnerException(int id)
	{
		super("Vous n'êtes pas le propriétaire de la protection {" + id + "}.");
	}
}