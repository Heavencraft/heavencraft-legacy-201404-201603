package fr.lorgan17.lorganserver.exceptions;

public class UserNotFoundException extends LorganException {

	private static final long serialVersionUID = 1L;

	public UserNotFoundException(String name)
	{
		super("Le joueur {" + name + "} n'existe pas.");
	}
}
