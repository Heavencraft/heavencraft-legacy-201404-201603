package fr.lorgan17.lorganserver.commands.mod;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.lorgan17.lorganserver.LorganServer;
import fr.lorgan17.lorganserver.commands.LorganCommand;
import fr.lorgan17.lorganserver.exceptions.LorganException;

public class MuteCommand extends LorganCommand {

	public MuteCommand()
	{
		super("mute");
	}

	@Override
	protected void onPlayerCommand(Player player, String[] args) throws LorganException
	{
		onConsoleCommand(player, args);
	}

	@Override
	protected void onConsoleCommand(CommandSender sender, String[] args) throws LorganException
	{
		if (args.length != 1)
		{
			sendUsage(sender);
			return;
		}
		
		String playerName = LorganServer.getPlayer(args[0]).getName();
		
		if (_plugin.getMuteManager().isMuted(playerName))
		{
			_plugin.getMuteManager().unmute(playerName);
			LorganServer.sendMessage(sender, "Le joueur {" + playerName + "} n'est plus muet.");
		}
		
		else
		{
			_plugin.getMuteManager().mute(playerName);
			LorganServer.sendMessage(sender, "Le joueur {" + playerName + "} est muet.");
		}
	}

	@Override
	protected void sendUsage(CommandSender sender)
	{
		LorganServer.sendMessage(sender, "/{mute} <joueur>");
	}
}
