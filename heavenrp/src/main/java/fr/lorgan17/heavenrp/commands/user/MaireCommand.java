package fr.lorgan17.heavenrp.commands.user;

import java.util.List;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.heavencraft.HeavenCommand;
import fr.heavencraft.Utils;
import fr.heavencraft.exceptions.HeavenException;
import fr.heavencraft.heavenrp.general.users.User;
import fr.heavencraft.heavenrp.general.users.UserProvider;
import fr.lorgan17.heavenrp.managers.TownsManager;

public class MaireCommand extends HeavenCommand
{

	public MaireCommand()
	{
		super("maire");
	}

	@Override
	protected void onPlayerCommand(Player player, String[] args) throws HeavenException
	{
		onConsoleCommand(player, args);
	}

	@Override
	protected void onConsoleCommand(CommandSender sender, String[] args) throws HeavenException
	{
		switch (args.length)
		{
			case 1: // /maire <ville>

				if (!TownsManager.regionExists(args[0]))
					throw new HeavenException("La ville {%1$s} n'existe pas.", args[0]);

				List<String> mayors = TownsManager.getMayors(args[0]);

				if (mayors.size() == 0)
					Utils.sendMessage(sender, "La ville {%1$s} n'a pas de maire.", args[0]);
				else
				{
					String message = "Maires de la ville {%1$s} :\n";

					for (String mayor : mayors)
						message += mayor + " ";

					Utils.sendMessage(sender, message, args[0]);
				}

				break;

			// /maire <ville> [+,-] <maire>
			case 3:
				if (!sender.hasPermission(TownsManager.ADD_REMOVE_MAYOR_PERMISSION))
					return;

				if (!TownsManager.regionExists(args[0]))
					throw new HeavenException("La ville {%1$s} n'existe pas.", args[0]);

				User mayor = UserProvider.getUserByName(Utils.getExactName(args[2]));

				if (args[1].equalsIgnoreCase("+"))
				{
					TownsManager.addMayor(args[0], mayor);
					Utils.sendMessage(sender, "Le joueur {%1$s} est désormais maire de la ville {%2$s}.",
							mayor.getName(), args[0]);
				}

				else if (args[1].equalsIgnoreCase("-"))
				{
					TownsManager.removeMayor(args[0], mayor);
					Utils.sendMessage(sender, "Le joueur {%1$s} n'est désormais plus maire de la ville {%2$s}.",
							mayor.getName(), args[0]);
				}

				break;

			default:
				sendUsage(sender);
				break;
		}
	}

	@Override
	protected void sendUsage(CommandSender sender)
	{
		Utils.sendMessage(sender, "{/maire} <ville> : liste les maires de la ville");
		Utils.sendMessage(sender, "{/maire} <ville> + <maire> : ajoute un maire à la ville");
		Utils.sendMessage(sender, "{/maire} <ville> - <maire> : enlève un maire de la ville");
	}
}
