package fr.tenkei.creaplugin.commands.user;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.tenkei.creaplugin.MyPlugin;
import fr.tenkei.creaplugin.commands.Command;
import fr.tenkei.creaplugin.exceptions.MyException;
import fr.tenkei.creaplugin.utils.Message;

public class JetonCommand extends Command{

	public JetonCommand(MyPlugin plugin) {
		super("jeton", plugin);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onPlayerCommand(Player player, String[] args)
			throws MyException {
		
		if (args.length == 0)
			Message.sendMessage(player, "Vous avez {" + getUser(player.getName()).getJeton() + "} jetons sur vous.");
		
	}

	@Override
	protected void onConsoleCommand(CommandSender sender, String[] args)
			throws MyException {
	
	}

	@Override
	protected void sendUsage(CommandSender sender) {
		Message.sendMessage(sender, "/{jeton} give <player> <Jeton> : {Pas disponnible");
	}

}
