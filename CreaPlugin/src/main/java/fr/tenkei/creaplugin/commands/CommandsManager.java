package fr.tenkei.creaplugin.commands;

import fr.heavencraft.commands.AccepterCommand;
import fr.heavencraft.commands.HeadCommand;
import fr.heavencraft.commands.RejoindreCommand;
import fr.heavencraft.commands.TpCommand;
import fr.heavencraft.commands.TphereCommand;
import fr.heavencraft.commands.TpposCommand;
import fr.tenkei.creaplugin.MyPlugin;
import fr.tenkei.creaplugin.commands.admin.BiomeCommand;
import fr.tenkei.creaplugin.commands.admin.OldCommand;
import fr.tenkei.creaplugin.commands.admin.SetspawnCommand;
import fr.tenkei.creaplugin.commands.builder.BuildCommand;
import fr.tenkei.creaplugin.commands.homes.BuyhomeCommand;
import fr.tenkei.creaplugin.commands.homes.HomeCommand;
import fr.tenkei.creaplugin.commands.homes.SethomeCommand;
import fr.tenkei.creaplugin.commands.homes.TphomeCommand;
import fr.tenkei.creaplugin.commands.user.HpsCommand;
import fr.tenkei.creaplugin.commands.user.JetonsCommand;
import fr.tenkei.creaplugin.commands.user.ProtectionCommand;
import fr.tenkei.creaplugin.commands.user.teleport.SpawnCommand;
import fr.tenkei.creaplugin.commands.user.teleport.TutoCommand;

public class CommandsManager
{

	public CommandsManager(MyPlugin plugin)
	{
		/*
		 * HeavenCore
		 */

		new AccepterCommand();
		new HeadCommand();
		new RejoindreCommand();
		new TpCommand();
		new TphereCommand();
		new TpposCommand();

		/*
		 * CréaPlugin
		 */

		// User
		new SpawnCommand();
		new TutoCommand();

		// Homes
		new HomeCommand();
		new SethomeCommand();
		new BuyhomeCommand();
		new TphomeCommand();

		new HpsCommand();
		new JetonsCommand();

		new ProtectionCommand();

		// Builder
		new BuildCommand();

		// Modo

		// Admin
		new BiomeCommand();
		new OldCommand();
		new SetspawnCommand();
	}
}