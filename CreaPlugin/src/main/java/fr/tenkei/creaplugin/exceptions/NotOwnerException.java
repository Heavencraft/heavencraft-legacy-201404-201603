package fr.tenkei.creaplugin.exceptions;

public class NotOwnerException extends MyException {

	private static final long serialVersionUID = 1L;

	public NotOwnerException()
	{
		super("Vous n'êtes pas le propriétaire de la protection.");
	}
}
