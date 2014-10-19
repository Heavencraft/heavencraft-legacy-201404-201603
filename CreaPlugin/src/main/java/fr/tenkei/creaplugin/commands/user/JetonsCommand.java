package fr.tenkei.creaplugin.commands.user;

import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.heavencraft.commands.HeavenCommand;
import fr.heavencraft.exceptions.HeavenException;
import fr.heavencraft.utils.ChatUtil;
import fr.heavencraft.utils.DevUtil;
import fr.heavencraft.utils.PlayerUtil;
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
		{
			ChatUtil.sendMessage(player, "Vous avez {%1$s} jetons sur vous.",
					UserProvider.getUserByName(player.getName()).getJetons());
			return;
		}

		switch (args[0])
		{
			case "donner":
				if (args.length == 3)
				{
					String dest = PlayerUtil.getExactName(args[1]);
					int delta = DevUtil.toUint(args[2]);

					UserProvider.getUserByName(player.getName()).updateBalance(-delta);
					UserProvider.getUserByName(dest).updateBalance(delta);

					ChatUtil.sendMessage(player, "Vous avez donné {%1$s} jetons à {%2$s}.", delta, dest);
				}
				break;
		}
	}

	@Override
	protected void onConsoleCommand(CommandSender sender, String[] args) throws HeavenException
	{
		if (args.length != 3)
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