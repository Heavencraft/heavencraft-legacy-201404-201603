package fr.heavencraft.heavenproxy.commands;

import java.util.Calendar;

import net.md_5.bungee.api.CommandSender;
import fr.heavencraft.heavenproxy.Utils;
import fr.heavencraft.heavenproxy.exceptions.HeavenException;
import fr.heavencraft.heavenproxy.users.UsersManager;
import fr.heavencraft.heavenproxy.users.User;

public class ActifCommand extends HeavenCommand
{

	public ActifCommand()
	{
		super("actif", null, new String[] {});
	}

	@Override
	protected void onCommand(CommandSender sender, String[] args) throws HeavenException
	{
		if (args.length != 1)
		{
			sendUsage(sender);
			return;
		}
		
		String name = Utils.getRealName(args[0]);
		User user = UsersManager.getUserByName(name);
		
		Calendar limit = Calendar.getInstance();
		limit.add(Calendar.DATE, -21);
		
		String status;
		
		if (user.getLastLogin() == null)
			status = "inctif";
		else if (user.getLastLogin().before(limit.getTime()))
			status = "inactif";
		else
			status = "actif";
		
		Utils.sendMessage(sender, "Le joueur {%1$s} est {%2$s}.", name, status);
	}

	private void sendUsage(CommandSender sender)
	{
		Utils.sendMessage(sender, "/{actif} <joueur>");
	}
}
