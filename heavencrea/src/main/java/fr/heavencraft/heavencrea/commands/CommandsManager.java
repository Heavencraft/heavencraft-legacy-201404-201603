package fr.heavencraft.heavencrea.commands;

import fr.heavencraft.commands.AccepterCommand;
import fr.heavencraft.commands.HeadCommand;
import fr.heavencraft.commands.RejoindreCommand;
import fr.heavencraft.commands.TpCommand;
import fr.heavencraft.commands.TphereCommand;
import fr.heavencraft.commands.TpposCommand;
import fr.heavencraft.commands.TpworldCommand;
import fr.heavencraft.heavencrea.commands.homes.BuyhomeCommand;
import fr.heavencraft.heavencrea.commands.homes.HomeCommand;
import fr.heavencraft.heavencrea.commands.homes.SethomeCommand;
import fr.heavencraft.heavencrea.commands.homes.TphomeCommand;
import fr.heavencraft.heavencrea.commands.money.HpsCommand;
import fr.heavencraft.heavencrea.commands.money.JetonsCommand;
import fr.heavencraft.heavencrea.commands.teleport.SpawnCommand;

public class CommandsManager
{

	public CommandsManager()
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
		new TpworldCommand();

		/*
		 * CréaPlugin
		 */

		// User
		new SpawnCommand();

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