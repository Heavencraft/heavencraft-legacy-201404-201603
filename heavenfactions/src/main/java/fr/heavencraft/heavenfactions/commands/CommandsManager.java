package fr.heavencraft.heavenfactions.commands;

import fr.heavencraft.heavenfactions.commands.user.FAllianceCommand;
import fr.heavencraft.heavenfactions.commands.user.FChatCommand;

public class CommandsManager
{
	public CommandsManager()
	{
		new FChatCommand();
		new FAllianceCommand();
	}
}