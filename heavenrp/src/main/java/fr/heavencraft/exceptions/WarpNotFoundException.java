package fr.heavencraft.exceptions;

public class WarpNotFoundException extends HeavenException {

	private static final long serialVersionUID = 1L;

	public WarpNotFoundException(String name)
	{
		super("La warp {%1$s} n'existe pas.", name);
	}
}
