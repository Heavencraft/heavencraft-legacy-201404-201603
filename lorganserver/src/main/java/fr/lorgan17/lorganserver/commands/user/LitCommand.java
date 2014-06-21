package fr.lorgan17.lorganserver.commands.user;

import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.heavencraft.commands.HeavenCommand;
import fr.heavencraft.exceptions.HeavenException;
import fr.heavencraft.utils.ChatUtil;

public class LitCommand extends HeavenCommand
{
	public LitCommand()
	{
		super("lit");
	}

	@Override
	protected void onPlayerCommand(Player player, String[] args) throws HeavenException
	{
		Location bedLocation = player.getBedSpawnLocation();

		if (bedLocation == null)
			ChatUtil.sendMessage(player, "Votre lit a été détruit.");
		else
			player.teleport(bedLocation);
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