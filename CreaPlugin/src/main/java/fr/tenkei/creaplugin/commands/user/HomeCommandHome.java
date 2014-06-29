package fr.tenkei.creaplugin.commands.user;

import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.heavencraft.commands.HeavenCommand;
import fr.heavencraft.exceptions.HeavenException;
import fr.heavencraft.utils.ChatUtil;
import fr.tenkei.creaplugin.managers.UserManager;
import fr.tenkei.creaplugin.managers.entities.User;
import fr.tenkei.creaplugin.utils.Stuff;

public class HomeCommandHome extends HeavenCommand
{
	public HomeCommandHome()
	{
		super("home");
	}

	@Override
	protected void onPlayerCommand(Player player, String[] args) throws HeavenException
	{
		int number = 1;

		if (args.length > 0)
			number = Stuff.toUint(args[0]);

		User u = UserManager.getUser(player.getName());

		if (0 < number && number <= u.getHomeNumbre())
		{
			if (u.hasHome(number))
			{
				Location destination = u.getHome(number);
				if (player.getWorld() != destination.getWorld())
				{
					ChatUtil.sendMessage(player,
							"Vous devez être dans le même monde que l'emplacement du {[/home %1$s]} !", number);
					return;
				}
				player.teleport(destination);
			}
			else
				ChatUtil.sendMessage(player, "Vous n'avez pas configuré ce {[/sethome " + number + "]}.");
		}
		else
			ChatUtil.sendMessage(player, "Ce {[/home " + number + "]} n'existe pas.");
	}

	@Override
	protected void onConsoleCommand(CommandSender sender, String[] args) throws HeavenException
	{
		// TODO Auto-generated method stub

	}

	@Override
	protected void sendUsage(CommandSender sender)
	{
		// TODO Auto-generated method stub

	}

}
