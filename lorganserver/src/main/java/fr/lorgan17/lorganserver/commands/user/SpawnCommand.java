package fr.lorgan17.lorganserver.commands.user;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.lorgan17.lorganserver.LorganServer;
import fr.lorgan17.lorganserver.commands.LorganCommand;
import fr.lorgan17.lorganserver.exceptions.LorganException;
import fr.lorgan17.lorganserver.managers.WorldsManager;

public class SpawnCommand extends LorganCommand {

	public SpawnCommand()
	{
		super("spawn");
	}

	@Override
	protected void onPlayerCommand(Player player, String[] args) throws LorganException
	{
		player.teleport(WorldsManager.getSpawn());
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
