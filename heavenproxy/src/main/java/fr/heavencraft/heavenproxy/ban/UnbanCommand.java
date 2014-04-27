package fr.heavencraft.heavenproxy.ban;

import net.md_5.bungee.api.CommandSender;
import fr.heavencraft.heavenproxy.Utils;
import fr.heavencraft.heavenproxy.commands.HeavenCommand;
import fr.heavencraft.heavenproxy.exceptions.HeavenException;
import fr.heavencraft.heavenproxy.listeners.LogListener;

public class UnbanCommand extends HeavenCommand
{
	public UnbanCommand()
	{
		super("unban", "heavencraft.commands.unban", new String[] { "gunban" });
	}

	@Override
	protected void onCommand(CommandSender sender, String[] args) throws HeavenException
	{
		switch (args.length)
		{
			case 1:
				BanManager.unbanPlayer(args[0]);
				Utils.sendMessage(sender, "Le joueur {%1$s} a été débanni d'Heavencraft.", args[0]);
				LogListener.addUnban(args[0], sender.getName());
				break;
			default:
				sendUsage(sender);
				break;
		}
	}

	private void sendUsage(CommandSender sender)
	{
		Utils.sendMessage(sender, "/{unban} <joueur>");
	}
}