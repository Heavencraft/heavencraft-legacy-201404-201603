package fr.tenkei.creaplugin.exceptions;


public class PlayerNotConnectedException extends MyException {

	private static final long serialVersionUID = 1L;

	public PlayerNotConnectedException(String name)
	{
		super("Le joueur {" + name + "} n'est pas connecté.");
	}
}