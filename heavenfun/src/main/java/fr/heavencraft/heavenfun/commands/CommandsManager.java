package fr.heavencraft.heavenfun.commands;

import fr.heavencraft.commands.CreacheatCommand;
import fr.heavencraft.commands.EndercheatCommand;
import fr.heavencraft.commands.InventoryCommand;
import fr.heavencraft.commands.TpCommand;
import fr.heavencraft.commands.TphereCommand;
import fr.heavencraft.commands.TpposCommand;
import fr.heavencraft.commands.TpworldCommand;
import fr.heavencraft.heavenfun.commands.spawn.SpawnCommand;

public class CommandsManager
{
	public static void init()
	{
		new SpawnCommand();

		new CreacheatCommand();
		new EndercheatCommand();
		new InventoryCommand();
		new TpCommand();
		new TphereCommand();
		new TpposCommand();
		new TpworldCommand();
	}
}
