package fr.tenkei.creaplugin.commands.modo;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.tenkei.creaplugin.MyPlugin;
import fr.tenkei.creaplugin.commands.Command;
import fr.tenkei.creaplugin.exceptions.MyException;
import fr.tenkei.creaplugin.utils.Message;


public class TpCommand extends Command {

	public TpCommand(MyPlugin plugin)
	{
		super("tp", plugin);
	}

	@Override
	protected void onPlayerCommand(Player player, String[] args) throws MyException
	{
		if(!player.hasPermission(MyPlugin.archiModo))
			return;
		
		if (args.length != 1) {
			sendUsage(player);
			return;
		}
		
		Player otherPlayer = getPlayer(args[0]);
		player.teleport(otherPlayer);
		
		Message.sendMessage(player, "Vous avez été téléporté à {" + otherPlayer.getName() +"}.");
	}

	@Override
	protected void onConsoleCommand(CommandSender sender, String[] args) throws MyException
	{
		Message.sendMessage(sender, "Cette commande n'est pas utilisable depuis la console.");
	}

	@Override
	protected void sendUsage(CommandSender sender)
	{
		Message.sendMessage(sender, "/{tp} <joueur>");
	}
}
