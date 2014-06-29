package fr.tenkei.creaplugin.exceptions;

import fr.heavencraft.exceptions.HeavenException;

public class NotOwnerException extends HeavenException
{
	private static final long serialVersionUID = 1L;

	public NotOwnerException()
	{
		super("Vous n'êtes pas le propriétaire de la protection.");
	}
}