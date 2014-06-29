package fr.tenkei.creaplugin.commands.user;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.heavencraft.commands.HeavenCommand;
import fr.heavencraft.exceptions.HeavenException;
import fr.heavencraft.utils.ChatUtil;
import fr.tenkei.creaplugin.MyPlugin;
import fr.tenkei.creaplugin.managers.UserManager;
import fr.tenkei.creaplugin.managers.WorldsManager;
import fr.tenkei.creaplugin.managers.entities.User;
import fr.tenkei.creaplugin.utils.Stuff;

public class HomeCommandSetHome extends HeavenCommand
{

	public HomeCommandSetHome()
	{
		super("sethome");
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
			if (player.getWorld() == WorldsManager.getTheTravaux() && !player.hasPermission(MyPlugin.builder))
			{
				ChatUtil.sendMessage(player, "Impossible de configurer de home dans ce monde !");
				return;
			}

			if (u.setHome(number, player.getLocation()))
				ChatUtil.sendMessage(player, "Ce {[/home %1$s]} a bien été configuré.", number);
			else
				ChatUtil.sendMessage(player, "Impossible de configurer ce {[/home %1$s]}.", number);
		}
		else
			ChatUtil.sendMessage(player, "Ce {[/home %1$s]} n'existe pas.", number);
	}

	@Override
	protected void onConsoleCommand(CommandSender sender, String[] args) throws HeavenException
	{

	}

	@Override
	protected void sendUsage(CommandSender sender)
	{

	}

}
