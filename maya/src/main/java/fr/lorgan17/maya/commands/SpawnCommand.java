package fr.lorgan17.maya.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.lorgan17.maya.MayaCommand;
import fr.lorgan17.maya.MayaPlugin;

public class SpawnCommand extends MayaCommand
{
	public SpawnCommand()
	{
		super("spawn");
	}

	@Override
	public void onPlayerCommand(Player player, String[] args)
	{
		if (args.length == 0)
			player.teleport(MayaPlugin.spawn);
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
		MayaPlugin.sendMessage(sender, "{/spawn} : revenir au spawn.");
	}
}