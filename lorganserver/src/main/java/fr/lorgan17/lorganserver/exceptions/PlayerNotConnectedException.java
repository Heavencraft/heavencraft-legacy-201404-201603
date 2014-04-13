package fr.lorgan17.lorganserver.exceptions;

public class PlayerNotConnectedException extends LorganException {

	private static final long serialVersionUID = 1L;

	public PlayerNotConnectedException(String name)
	{
		super("Le joueur {" + name + "} n'est pas connecté.");
	}
}
