package fr.lorgan17.lorganserver.commands;

import fr.heavencraft.commands.EndercheatCommand;
import fr.heavencraft.commands.GcCommand;
import fr.heavencraft.commands.InventoryCommand;
import fr.heavencraft.commands.RoucoupsCommand;
import fr.heavencraft.commands.TpCommand;
import fr.heavencraft.commands.TphereCommand;
import fr.heavencraft.commands.TpposCommand;
import fr.lorgan17.lorganserver.commands.admin.SetspawnCommand;
import fr.lorgan17.lorganserver.commands.user.LitCommand;
import fr.lorgan17.lorganserver.commands.user.ProtectionCommand;
import fr.lorgan17.lorganserver.commands.user.SpawnCommand;

public class CommandsManager
{
	public CommandsManager()
	{
		new EndercheatCommand();
		new GcCommand();
		new InventoryCommand();
		new RoucoupsCommand();
		new TpCommand();
		new TphereCommand();
		new TpposCommand();

		new SetspawnCommand();

		new LitCommand();
		new ProtectionCommand();
		new SpawnCommand();
	}
}
