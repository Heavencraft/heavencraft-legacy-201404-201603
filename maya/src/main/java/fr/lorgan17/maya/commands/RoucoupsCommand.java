package fr.lorgan17.maya.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.lorgan17.maya.MayaCommand;
import fr.lorgan17.maya.MayaPlugin;

public class RoucoupsCommand extends MayaCommand
{
	public RoucoupsCommand()
	{
		super("roucoups");
	}

	@Override
	public void onPlayerCommand(Player player, String[] args)
	{
		if (player.getAllowFlight())
		{
			player.setAllowFlight(false);
			MayaPlugin.sendMessage(player, "Vous venez de descendre de {Roucoups}.");
		}
		else
		{
			player.setAllowFlight(true);
			MayaPlugin.sendMessage(player, "{Roucoups} utilise vol.");
		}
	}

	@Override
	public void onConsoleCommand(CommandSender sender, String[] args)
	{
	}

	@Override
	public void sendUsage(CommandSender sender)
	{
	}
}