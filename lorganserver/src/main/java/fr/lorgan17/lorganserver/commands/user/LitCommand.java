package fr.lorgan17.lorganserver.commands.user;

import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.lorgan17.lorganserver.LorganServer;
import fr.lorgan17.lorganserver.commands.LorganCommand;
import fr.lorgan17.lorganserver.exceptions.LorganException;

public class LitCommand extends LorganCommand {

	public LitCommand()
	{
		super("lit");
	}

	@Override
	protected void onPlayerCommand(Player player, String[] args) throws LorganException
	{
		Location bedLocation = player.getBedSpawnLocation();
		
		if (bedLocation == null)
			LorganServer.sendMessage(player, "Votre lit a été détruit.");
		else
			player.teleport(bedLocation);
	}

	@Override
	protected void onConsoleCommand(CommandSender sender, String[] args) throws LorganException
	{
		LorganServer.sendMessage(sender, "Cette commande n'est pas utilisable depuis la console.");
	}

	@Override
	protected void sendUsage(CommandSender sender)
	{
	}
}
