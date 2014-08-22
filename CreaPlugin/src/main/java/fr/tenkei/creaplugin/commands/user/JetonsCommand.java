package fr.tenkei.creaplugin.commands.user;

import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.heavencraft.commands.HeavenCommand;
import fr.heavencraft.exceptions.HeavenException;
import fr.heavencraft.utils.ChatUtil;
import fr.tenkei.creaplugin.users.UserProvider;

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
			ChatUtil.sendMessage(player, "Vous avez {" + UserProvider.getUserByName(player.getName()).getJetons()
					+ "} jetons sur vous.");
	}

	@Override
	protected void onConsoleCommand(CommandSender sender, String[] args) throws HeavenException
	{
		if(args.length != 3)
			return;
		UserProvider.getUserByName(args[1]).updateBalance(Integer.parseInt(args[2]));
		Bukkit.getLogger().log(Level.INFO, "Solde mis a jour");
	}

	@Override
	protected void sendUsage(CommandSender sender)
	{
		ChatUtil.sendMessage(sender, "/{jetons} give <player> <Jeton> : {Pas disponnible");
	}
}