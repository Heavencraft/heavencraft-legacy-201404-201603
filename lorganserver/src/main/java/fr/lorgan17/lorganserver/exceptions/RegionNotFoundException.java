package fr.lorgan17.lorganserver.exceptions;

public class RegionNotFoundException extends LorganException {

	private static final long serialVersionUID = 1L;

	public RegionNotFoundException(int id)
	{
		super("La protection {" + id + "} n'existe pas.");
	}
}
