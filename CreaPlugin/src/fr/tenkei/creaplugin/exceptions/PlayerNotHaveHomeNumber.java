package fr.tenkei.creaplugin.exceptions;

public class PlayerNotHaveHomeNumber extends MyException {

	private static final long serialVersionUID = 1L;

	public PlayerNotHaveHomeNumber(int number) {
		super("Vous n'avez pas le home {" + number + "}. Vous devez l'acheter.");
	}

}
