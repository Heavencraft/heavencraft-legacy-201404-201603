package fr.lorgan17.lorganserver.commands;

import fr.lorgan17.lorganserver.commands.admin.GcCommand;
import fr.lorgan17.lorganserver.commands.admin.SetspawnCommand;
import fr.lorgan17.lorganserver.commands.admin.TpCommand;
import fr.lorgan17.lorganserver.commands.mod.BanCommand;
import fr.lorgan17.lorganserver.commands.mod.KickCommand;
import fr.lorgan17.lorganserver.commands.mod.MuteCommand;
import fr.lorgan17.lorganserver.commands.mod.UnbanCommand;
import fr.lorgan17.lorganserver.commands.user.LitCommand;
import fr.lorgan17.lorganserver.commands.user.ProtectionCommand;
import fr.lorgan17.lorganserver.commands.user.SpawnCommand;
import fr.lorgan17.lorganserver.commands.user.TellCommand;

public class CommandsManager {

	public CommandsManager()
	{
		new GcCommand();
		new SetspawnCommand();
		new TpCommand();
		
		new BanCommand();
		new KickCommand();
		new MuteCommand();
		new UnbanCommand();
		
		new LitCommand();
		new ProtectionCommand();
		new SpawnCommand();
		new TellCommand();
	}
}
