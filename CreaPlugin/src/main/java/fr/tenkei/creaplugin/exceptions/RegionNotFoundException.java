package fr.tenkei.creaplugin.exceptions;

public class RegionNotFoundException extends MyException {

	private static final long serialVersionUID = 1L;

	public RegionNotFoundException(int id)
	{
		super("La protection {" + id + "} n'existe pas.");
	}
}
