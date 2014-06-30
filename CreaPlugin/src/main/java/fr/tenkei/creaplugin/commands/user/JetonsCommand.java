package fr.tenkei.creaplugin.commands.user;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.heavencraft.commands.HeavenCommand;
import fr.heavencraft.exceptions.HeavenException;
import fr.heavencraft.utils.ChatUtil;
import fr.tenkei.creaplugin.managers.UserManager;

public class JetonsCommand extends HeavenCommand
{
	public JetonsCommand()
	{
		super("jetons");
	}

	@Override
	protected void onPlayerCommand(Player player, String[] args) throws HeavenException
	{
		if (args.length == 0)
			ChatUtil.sendMessage(player, "Vous avez {" + UserManager.getUser(player.getName()).getJeton()
					+ "} jetons sur vous.");
	}

	@Override
	protected void onConsoleCommand(CommandSender sender, String[] args) throws HeavenException
	{

	}

	@Override
	protected void sendUsage(CommandSender sender)
	{
		ChatUtil.sendMessage(sender, "/{jetons} give <player> <Jeton> : {Pas disponnible");
	}
}