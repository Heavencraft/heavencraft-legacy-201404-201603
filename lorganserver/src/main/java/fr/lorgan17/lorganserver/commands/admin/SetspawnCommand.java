package fr.lorgan17.lorganserver.commands.admin;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.lorgan17.lorganserver.LorganServer;
import fr.lorgan17.lorganserver.commands.LorganCommand;
import fr.lorgan17.lorganserver.exceptions.LorganException;
import fr.lorgan17.lorganserver.managers.WorldsManager;

public class SetspawnCommand extends LorganCommand {

	public SetspawnCommand()
	{
		super("setspawn");
	}

	@Override
	protected void onPlayerCommand(Player player, String[] args) throws LorganException
	{
		WorldsManager.setSpawn(player.getLocation());
		LorganServer.sendMessage(player, "L'emplacement du spawn a bien été changé.");
	}

	@Override
	protected void onConsoleCommand(CommandSender sender, String[] args) throws LorganException
	{
	}

	@Override
	protected void sendUsage(CommandSender sender)
	{
	}
}
