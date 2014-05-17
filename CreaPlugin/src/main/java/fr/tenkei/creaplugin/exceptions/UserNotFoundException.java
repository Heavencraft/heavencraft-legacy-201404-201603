package fr.tenkei.creaplugin.exceptions;

public class UserNotFoundException extends MyException {

	private static final long serialVersionUID = 1L;

	public UserNotFoundException(String name)
	{
		super("Le joueur {" + name + "} n'existe pas.");
	}
}
