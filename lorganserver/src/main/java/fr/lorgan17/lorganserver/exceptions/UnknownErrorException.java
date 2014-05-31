package fr.lorgan17.lorganserver.exceptions;

public class UnknownErrorException extends LorganException {

	private static final long serialVersionUID = 1L;

	public UnknownErrorException(String error)
	{
		super("Cette erreur n'est pas censée se produire : {" + error + "}.");
	}
}
