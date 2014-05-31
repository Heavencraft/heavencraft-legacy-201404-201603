package fr.lorgan17.lorganserver.exceptions;

public class MemberNotFoundException extends LorganException {

	private static final long serialVersionUID = 1L;

	public MemberNotFoundException(int id, String name)
	{
		super("Le joueur {" + name + "} n'est pas membre de la protection {" + id + "}.");
	}
}
