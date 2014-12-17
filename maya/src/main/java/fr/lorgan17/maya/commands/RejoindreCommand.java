package fr.lorgan17.maya.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.lorgan17.maya.MayaPlugin;

public class RejoindreCommand extends MayaCommand
{
	public RejoindreCommand()
	{
		super("rejoindre");
	}

	@Override
	public void onPlayerCommand(Player player, String[] args)
	{
		if (args.length == 1)
		{
			Player player2 = Bukkit.getPlayer(args[0]);
			
			if (player2 == null)
				MayaPlugin.sendMessage(player, "Le joueur {" + args[0] + "} n'est pas connecté.");
			else
				player.teleport(player2);
		}
		
		else
			sendUsage(player);
	}

	@Override
	public void onConsoleCommand(CommandSender sender, String[] args)
	{
		MayaPlugin.sendMessage(sender, "Cette commande n'est pas disponible depuis la console.");
	}

	@Override
	public void sendUsage(CommandSender sender)
	{
		MayaPlugin.sendMessage(sender, "{/rejoindre} <joueur> : se téléporter à un joueur.");
	}
}