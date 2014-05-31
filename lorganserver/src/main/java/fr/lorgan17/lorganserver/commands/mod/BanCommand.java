package fr.lorgan17.lorganserver.commands.mod;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.lorgan17.lorganserver.LorganServer;
import fr.lorgan17.lorganserver.commands.LorganCommand;
import fr.lorgan17.lorganserver.entities.User;
import fr.lorgan17.lorganserver.exceptions.LorganException;

public class BanCommand extends LorganCommand {

	public BanCommand()
	{
		super("ban");
	}

	@Override
	protected void onPlayerCommand(Player player, String[] args) throws LorganException
	{
		onConsoleCommand(player, args);
	}

	@Override
	protected void onConsoleCommand(CommandSender sender, String[] args) throws LorganException
	{
		if (args.length == 0)
		{
			sendUsage(sender);
			return;
		}
		
		String name = args[0];
		String reason = sender.getName() + " :";
		
		Player player = Bukkit.getPlayer(name);
		
		if (player != null)
			name = player.getName();
		
		for (int i = 1; i != args.length; i++)
			reason += " " + args[i];
		
		User.getUserByName(name).ban(reason.trim());
		
		if (player != null)
			player.kickPlayer(reason);
		
		LorganServer.sendMessage(sender, "Le joueur {" + name + "} a été banni.");
	}

	@Override
	protected void sendUsage(CommandSender sender)
	{
		LorganServer.sendMessage(sender, "/{ban} <joueur> <raison>");
	}
}
