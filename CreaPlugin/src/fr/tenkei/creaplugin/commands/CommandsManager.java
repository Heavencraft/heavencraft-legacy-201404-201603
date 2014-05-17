package fr.tenkei.creaplugin.commands;

import org.bukkit.command.defaults.ListCommand;

import fr.tenkei.creaplugin.MyPlugin;
import fr.tenkei.creaplugin.commands.admin.BiomeCommand;
import fr.tenkei.creaplugin.commands.admin.OldCommand;
import fr.tenkei.creaplugin.commands.admin.SetspawnCommand;
import fr.tenkei.creaplugin.commands.admin.TphereCommand;
import fr.tenkei.creaplugin.commands.builder.BuildCommand;
import fr.tenkei.creaplugin.commands.builder.HeadCommand;
import fr.tenkei.creaplugin.commands.builder.TpPosCommand;
import fr.tenkei.creaplugin.commands.modo.TpCommand;
import fr.tenkei.creaplugin.commands.user.HomeCommandHome;
import fr.tenkei.creaplugin.commands.user.HomeCommandSetHome;
import fr.tenkei.creaplugin.commands.user.HpsCommand;
import fr.tenkei.creaplugin.commands.user.JetonCommand;
import fr.tenkei.creaplugin.commands.user.ProtectionCommand;
import fr.tenkei.creaplugin.commands.user.teleport.AccepterCommand;
import fr.tenkei.creaplugin.commands.user.teleport.RejoindreCommand;
import fr.tenkei.creaplugin.commands.user.teleport.SpawnCommand;

public class CommandsManager {

	public CommandsManager(MyPlugin plugin){
		new ListCommand();
		
		// User
		new RejoindreCommand(plugin);
		new AccepterCommand(plugin);
		new SpawnCommand(plugin);
		
		new HomeCommandHome(plugin);
		new HomeCommandSetHome(plugin);
		
		new HpsCommand(plugin);
		new JetonCommand(plugin);
		
		new ProtectionCommand(plugin);

		
		// Builder
		new BuildCommand(plugin);
		new HeadCommand(plugin);
		new TpPosCommand(plugin);
		
		
		// Modo
		new TpCommand(plugin);
		
		
		// Admin
		new TphereCommand(plugin);
		new BiomeCommand(plugin);
		new OldCommand(plugin);
		new SetspawnCommand(plugin);
		
	}
}
