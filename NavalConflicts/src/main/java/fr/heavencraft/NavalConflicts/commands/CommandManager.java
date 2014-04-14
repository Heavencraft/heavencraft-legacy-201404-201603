package fr.heavencraft.NavalConflicts.commands;

import fr.heavencraft.NavalConflicts.commands.admin.ArenaCommands;
import fr.heavencraft.NavalConflicts.commands.admin.LobbyCommands;
import fr.heavencraft.NavalConflicts.commands.user.ClasseCommand;
import fr.heavencraft.NavalConflicts.commands.user.JoinCommand;
import fr.heavencraft.NavalConflicts.commands.user.LeaveCommand;
import fr.heavencraft.NavalConflicts.commands.user.VoteCommand;


public class CommandManager {
	public CommandManager()
	{
		/*
		 * Commandes Joueurs
		 */
		new JoinCommand();
		new LeaveCommand();
		new ClasseCommand();
		new VoteCommand();
		/*
		 * Commandes Administrateurs
		 */
		new LobbyCommands();
		new ArenaCommands();
	}
}
