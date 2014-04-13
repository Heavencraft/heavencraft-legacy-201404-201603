package fr.heavencraft.NavalConflicts.exceptions;

public class NavalException extends Exception{
	private static final long serialVersionUID = 1L;

	public NavalException(String message)
	{
		super(message);
	}
	public NavalException(String message, Object... args)
	{
		super(String.format(message, args));
	}
}
