package fr.lorgan17.heavenrp.commands.user;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.heavencraft.HeavenCommand;
import fr.heavencraft.Utils;
import fr.heavencraft.exceptions.HeavenException;
import fr.heavencraft.heavenrp.general.users.UsersManager;
import fr.heavencraft.heavenrp.general.users.UsersManager.User;
import fr.lorgan17.heavenrp.managers.TownsManager;

public class ParcelleCommand extends HeavenCommand {
	
	public ParcelleCommand()
	{
		super("parcelle");
	}

	@Override
	protected void onPlayerCommand(Player player, String[] args) throws HeavenException
	{
		if (args.length == 0)
		{
			sendUsage(player);
			return;
		}
		
		String townName = args[0];
		User mayor = UsersManager.getByName(player.getName());
		
		if (!TownsManager.isMayor(mayor, townName))
			throw new HeavenException("Vous n'êtes pas maire de la ville {%1$s}.", townName);
		
		switch (args.length)
		{
			case 3:
				if (args[1].equals("+")) // /parcelle <ville> + <joueur>
				{
					TownsManager.createSubRegion(args[0],
							UsersManager.getByName(Utils.getExactName(args[2])),
							Utils.getWESelection(player),
							20, 10);

					Utils.sendMessage(player, "La parcelle a été créée avec succès.");
				}
				else if (args[1].equals("-"))
				{
					TownsManager.removeSubRegion(args[0], args[2]);

					Utils.sendMessage(player, "La parcelle a été supprimée avec succès.");
				}
				break;
			case 5:
				if (args[1].equals("+")) // /parcelle <ville> + <joueur> <up> <down>
				{
					int up = Utils.toUint(args[3]);
					int down = Utils.toUint(args[4]);
					
					TownsManager.createSubRegion(args[0],
							UsersManager.getByName(Utils.getExactName(args[2])),
							Utils.getWESelection(player),
							up, down);
					
					Utils.sendMessage(player, "La parcelle a été créée avec succès.");
				}
				break;
			default:
				sendUsage(player);
				break;
		}
	}
	
	@Override
	protected void onConsoleCommand(CommandSender sender, String[] args) throws HeavenException
	{
		Utils.sendMessage(sender, "Cette commande n'est pas utilisable depuis la console.");
	}

	@Override
	protected void sendUsage(CommandSender sender)
	{
		Utils.sendMessage(sender, "{/parcelle} <ville> + <joueur> : créé une parcelle à partir de la zone sélectionnée, et l'attribue à un joueur");
		Utils.sendMessage(sender, "{/parcelle} <ville> + <joueur> <up> <down> : même punition, mais avec la hauteur paramétrable");
		Utils.sendMessage(sender, "{/parcelle} <ville> - <parcelle> : supprime la parcelle <parcelle>");
	}
}
