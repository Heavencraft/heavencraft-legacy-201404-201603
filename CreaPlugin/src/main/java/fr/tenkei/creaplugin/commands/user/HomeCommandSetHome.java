package fr.tenkei.creaplugin.commands.user;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.tenkei.creaplugin.MyPlugin;
import fr.tenkei.creaplugin.commands.Command;
import fr.tenkei.creaplugin.exceptions.MyException;
import fr.tenkei.creaplugin.managers.WorldsManager;
import fr.tenkei.creaplugin.managers.entities.User;
import fr.tenkei.creaplugin.utils.Message;
import fr.tenkei.creaplugin.utils.Stuff;

public class HomeCommandSetHome extends Command{

	public HomeCommandSetHome(MyPlugin plugin) {
		super("sethome", plugin);
	}

	@Override
	protected void onPlayerCommand(Player player, String[] args)
			throws MyException {
		int number = 1;
		 if (args.length > 0)
			 number = Stuff.toUint(args[0]);	 

		 User u = getUser();
		
        if (0 < number && number <= u.getHomeNumbre()) {
        	if(player.getWorld() == WorldsManager.getTheTravaux() && !player.hasPermission(MyPlugin.builder)) {
        		Message.sendMessage(player, "Impossible de configurer de home dans ce monde !");
        		return;
        	}
        	
        	if (u.setHome(number, player.getLocation()))
        		Message.sendMessage(player, "Ce {[/home " + number + "]} a bien été configuré.");
        	else
        		Message.sendMessage(player, "Impossible de configurer ce {[/home " + number + "]}.");
        }
        else
        	Message.sendMessage(player,"Ce {[/home " + number + "]} n'existe pas.");
	}

	@Override
	protected void onConsoleCommand(CommandSender sender, String[] args)
			throws MyException {
		
	}

	@Override
	protected void sendUsage(CommandSender sender) {
		
	}

}
