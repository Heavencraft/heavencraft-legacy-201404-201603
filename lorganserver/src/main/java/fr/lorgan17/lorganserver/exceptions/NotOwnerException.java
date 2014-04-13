package fr.lorgan17.lorganserver.exceptions;

public class NotOwnerException extends LorganException {

	private static final long serialVersionUID = 1L;

	public NotOwnerException(int id)
	{
		super("Vous n'�tes pas le propri�taire de la protection {" + id + "}.");
	}
}
