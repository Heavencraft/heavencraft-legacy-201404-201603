package fr.heavencraft.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.heavencraft.Permissions;
import fr.heavencraft.exceptions.HeavenException;
import fr.heavencraft.utils.ChatUtil;

public class RoucoupsCommand extends HeavenCommand
{
	public RoucoupsCommand()
	{
		super("roucoups", Permissions.ROUCOUPS);
	}

	@Override
	protected void onPlayerCommand(Player player, String[] args) throws HeavenException
	{
		if (player.getAllowFlight())
		{
			player.setAllowFlight(false);
			ChatUtil.sendMessage(player, "Vous venez de descendre de {Roucoups}.");
		}
		else
		{
			player.setAllowFlight(true);
			ChatUtil.sendMessage(player, "{Roucoups} utilise vol.");
		}
	}

	@Override
	protected void onConsoleCommand(CommandSender sender, String[] args) throws HeavenException
	{
		ChatUtil.sendMessage(sender, "Cette commande n'est pas utilisable depuis la console.");
	}

	@Override
	protected void sendUsage(CommandSender sender)
	{
	}
}