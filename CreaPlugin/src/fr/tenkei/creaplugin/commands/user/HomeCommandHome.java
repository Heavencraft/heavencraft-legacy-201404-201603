package fr.tenkei.creaplugin.commands.user;

import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.tenkei.creaplugin.MyPlugin;
import fr.tenkei.creaplugin.commands.Command;
import fr.tenkei.creaplugin.exceptions.MyException;
import fr.tenkei.creaplugin.managers.entities.User;
import fr.tenkei.creaplugin.utils.Message;
import fr.tenkei.creaplugin.utils.Stuff;

public class HomeCommandHome extends Command{

	public HomeCommandHome(MyPlugin plugin) {
		super("home", plugin);
	}

	@Override
	protected void onPlayerCommand(Player player, String[] args)
			throws  MyException {
		 int number = 1;
		 
		 if (args.length > 0)
			 number = Stuff.toUint(args[0]);	 

		 User u = getUser();

		 if (0 < number && number <= u.getHomeNumbre()) {
			 if (u.hasHome(number)) {
				 Location destination = u.getHome(number);
				 if (player.getWorld() != destination.getWorld()) {
					 Message.sendMessage(player, "Vous devez être dans le même monde que l'emplacement du {[/home " + number + "]} !");
					 return;
				 }
				 player.teleport(destination);
			 }
			 else
				 Message.sendMessage(player, "Vous n'avez pas configuré ce {[/sethome " + number + "]}.");
		 }
		 else
			 Message.sendMessage(player, "Ce {[/home " + number + "]} n'existe pas.");
	}

	@Override
	protected void onConsoleCommand(CommandSender sender, String[] args)
			throws MyException {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void sendUsage(CommandSender sender) {
		// TODO Auto-generated method stub
		
	}

}
