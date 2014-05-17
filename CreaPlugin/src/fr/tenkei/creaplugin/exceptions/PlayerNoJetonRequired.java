package fr.tenkei.creaplugin.exceptions;

public class PlayerNoJetonRequired extends MyException {

	private static final long serialVersionUID = 1L;

	public PlayerNoJetonRequired(int amount) {
		super("Vous n'avez pas les jetons requis. Il vous en faut {" + amount + "}.");
	}

}
