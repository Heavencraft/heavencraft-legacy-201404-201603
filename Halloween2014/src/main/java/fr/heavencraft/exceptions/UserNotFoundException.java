package fr.heavencraft.exceptions;

public class UserNotFoundException extends HeavenException
{
	private static final long serialVersionUID = 1L;

	public UserNotFoundException(String name)
	{
		super("Le joueur {%1$s} n'existe pas.", name);
	}
}