package fr.heavencraft.HellCraft.exceptions;

public class HellException extends Exception
{
	private static final long serialVersionUID = 1L;

	public HellException(String message)
	{
		super(message);
	}

	public HellException(String message, Object... args)
	{
		super(String.format(message, args));
	}
}