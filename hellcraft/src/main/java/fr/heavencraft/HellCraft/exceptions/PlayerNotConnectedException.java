package fr.heavencraft.HellCraft.exceptions;

public class PlayerNotConnectedException extends HellException
{
	private static final long serialVersionUID = 1L;

	public PlayerNotConnectedException(String name)
	{
		super("Le joueur {%1$s} n'est pas connecté.", name);
	}
}