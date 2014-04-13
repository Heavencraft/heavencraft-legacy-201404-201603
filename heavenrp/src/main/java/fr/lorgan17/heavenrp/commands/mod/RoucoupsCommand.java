package fr.lorgan17.heavenrp.commands.mod;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.heavencraft.HeavenCommand;
import fr.heavencraft.Utils;
import fr.heavencraft.exceptions.HeavenException;

public class RoucoupsCommand extends HeavenCommand {

	public RoucoupsCommand()
	{
		super("roucoups", "heavenrp.moderator.roucoups");
	}

	@Override
	protected void onPlayerCommand(Player player, String[] args) throws HeavenException
	{
		if (player.getAllowFlight())
		{
			player.setAllowFlight(false);
			Utils.sendMessage(player, "Vous venez de descendre de {Roucoups}.");
		}
		else
		{
			player.setAllowFlight(true);
			Utils.sendMessage(player, "{Roucoups} utilise vol.");
		}
	}

	@Override
	protected void onConsoleCommand(CommandSender sender, String[] args) throws HeavenException
	{
		Utils.sendMessage(sender, "Cette commande n'est pas utilisable depuis la console.");
	}

	@Override
	protected void sendUsage(CommandSender sender)
	{
	}
}
