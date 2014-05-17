package fr.tenkei.creaplugin.exceptions;

public class MemberNotFoundException extends MyException {

	private static final long serialVersionUID = 1L;

	public MemberNotFoundException(int id, String name)
	{
		super("Le joueur {" + name + "} n'est pas membre de la protection {" + id + "}.");
	}
}
