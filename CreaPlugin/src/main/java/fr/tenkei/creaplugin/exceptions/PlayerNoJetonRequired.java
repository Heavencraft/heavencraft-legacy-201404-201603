package fr.tenkei.creaplugin.exceptions;

import fr.heavencraft.exceptions.HeavenException;

public class PlayerNoJetonRequired extends HeavenException
{
	private static final long serialVersionUID = 1L;

	public PlayerNoJetonRequired(int amount)
	{
		super("Vous n'avez pas les jetons requis. Il vous en faut {%1$s}.", amount);
	}
}