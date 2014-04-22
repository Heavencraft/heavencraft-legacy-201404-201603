package fr.heavencraft.aventure.commands;

import fr.heavencraft.aventure.commands.admin.listRegionsCommand;
import fr.heavencraft.aventure.commands.admin.reloadRegion;
import fr.heavencraft.aventure.commands.admin.setEnabledCommand;
import fr.heavencraft.aventure.commands.admin.setGreetingCommand;
import fr.heavencraft.aventure.commands.admin.setSoundCommand;


public class CommandManager {
	public CommandManager()
	{
		/*
		 * Commandes Joueurs
		 */

		
		
		/*
		 * Commandes Administrateurs
		 */
		new reloadRegion();
		new setSoundCommand();
		new setGreetingCommand();
		new setEnabledCommand();
		new listRegionsCommand();
	}
}
