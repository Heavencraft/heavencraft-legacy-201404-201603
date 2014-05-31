package fr.lorgan17.lorganserver.commands.admin;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.lorgan17.lorganserver.LorganServer;
import fr.lorgan17.lorganserver.commands.LorganCommand;
import fr.lorgan17.lorganserver.exceptions.LorganException;

public class TpCommand extends LorganCommand {

	public TpCommand()
	{
		super("tp");
	}

	@Override
	protected void onPlayerCommand(Player player, String[] args) throws LorganException
	{
		if (args.length != 1)
		{
			sendUsage(player);
			return;
		}
		
		Player otherPlayer = LorganServer.getPlayer(args[0]);
		player.teleport(otherPlayer);
		
		LorganServer.sendMessage(player, "Vous avez été téléporté à {" + otherPlayer.getName() +"}.");
	}

	@Override
	protected void onConsoleCommand(CommandSender sender, String[] args) throws LorganException
	{
		LorganServer.sendMessage(sender, "Cette commande n'est pas utilisable depuis la console.");
	}

	@Override
	protected void sendUsage(CommandSender sender)
	{
		LorganServer.sendMessage(sender, "/{tp} <joueur>");
	}
}
