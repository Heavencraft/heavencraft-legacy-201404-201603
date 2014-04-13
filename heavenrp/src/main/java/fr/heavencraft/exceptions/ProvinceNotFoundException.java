package fr.heavencraft.exceptions;

public class ProvinceNotFoundException extends HeavenException {

	private static final long serialVersionUID = 1L;

	public ProvinceNotFoundException(int id)
	{
		super("La province {" + id + "} n'existe pas.");
	}
	
	public ProvinceNotFoundException(String name)
	{
		super("La province {" + name + "} n'existe pas.");
	}
}
