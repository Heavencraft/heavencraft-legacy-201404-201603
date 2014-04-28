package fr.heavencraft.laposte.commands;

import fr.heavencraft.laposte.commands.admin.addOfficeCommand;
import fr.heavencraft.laposte.commands.admin.listPosteCommand;
import fr.heavencraft.laposte.commands.admin.reloadRegion;
import fr.heavencraft.laposte.commands.admin.removeOfficeCommand;
import fr.heavencraft.laposte.commands.user.colisCommand;


public class CommandManager {
	public CommandManager()
	{
		/*
		 * Commandes Joueurs
		 */
		new colisCommand();
		
		
		/*
		 * Commandes Administrateurs
		 */
		new reloadRegion();
		new addOfficeCommand();
		new removeOfficeCommand();
		new listPosteCommand();
	}
}
