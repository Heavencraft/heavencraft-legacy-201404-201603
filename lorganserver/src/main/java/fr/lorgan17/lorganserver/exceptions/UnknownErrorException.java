package fr.lorgan17.lorganserver.exceptions;

import fr.heavencraft.exceptions.HeavenException;

public class UnknownErrorException extends HeavenException
{
	private static final long serialVersionUID = 1L;

	public UnknownErrorException(String error)
	{
		super("Cette erreur n'est pas censée se produire : {" + error + "}.");
	}
}