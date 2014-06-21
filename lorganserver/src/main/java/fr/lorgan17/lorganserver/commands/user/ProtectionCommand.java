package fr.lorgan17.lorganserver.commands.user;

import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.heavencraft.commands.HeavenCommand;
import fr.heavencraft.exceptions.HeavenException;
import fr.heavencraft.utils.ChatUtil;
import fr.lorgan17.lorganserver.entities.Region;
import fr.lorgan17.lorganserver.exceptions.NotEnoughNuggetsException;
import fr.lorgan17.lorganserver.exceptions.NotOwnerException;
import fr.lorgan17.lorganserver.managers.MoneyManager;
import fr.lorgan17.lorganserver.managers.SelectionManager;
import fr.lorgan17.lorganserver.managers.SelectionManager.Pair;
import fr.lorgan17.lorganserver.managers.WorldsManager;

public class ProtectionCommand extends HeavenCommand
{
	public ProtectionCommand()
	{
		super("protection");
	}

	@Override
	protected void onPlayerCommand(Player player, String[] args) throws HeavenException
	{
		if (!player.getWorld().equals(WorldsManager.getWorld()))
		{
			ChatUtil.sendMessage(player, "Cette commande n'est pas accessible dans ce monde.");
			return;
		}

		if (args.length == 0)
		{
			sendUsage(player);
			return;
		}

		String playerName = player.getName();

		if (args[0].equalsIgnoreCase("creer"))
		{
			SelectionManager.enable(player.getName());
			ChatUtil.sendMessage(player, "Bienvenue dans l'assistant de protection.");
			ChatUtil.sendMessage(player,
					"Utilisez le clic gauche et le clic droit avec un bâton pour délimiter votre protection");
			ChatUtil.sendMessage(player, "Puis faites /protection valider ou /protection annuler");
		}

		else if (args[0].equalsIgnoreCase("annuler"))
		{
			SelectionManager.disable(player.getName());
			ChatUtil.sendMessage(player, "Création de la protection annulée.");
		}

		else if (args[0].equalsIgnoreCase("valider"))
		{
			int amount = SelectionManager.getPrice(playerName);

			if (!MoneyManager.hasEnough(player, amount))
				throw new NotEnoughNuggetsException(amount);

			Pair<Location, Location> s = SelectionManager.getSelection(player.getName());
			Region.createRegion(playerName, s.first.getBlockX(), s.first.getBlockZ(), s.second.getBlockX(),
					s.second.getBlockZ());

			MoneyManager.pay(player, amount);

			SelectionManager.disable(playerName);
			ChatUtil.sendMessage(player, "Votre protection a été créée.");
			ChatUtil.sendMessage(player,
					"Vous pouvez utiliser /protection ajouter pour ajouter des membres à votre protection.");
		}

		else if (args[0].equalsIgnoreCase("supprimer") && args.length == 2)
		{
			int regionId = Integer.parseInt(args[1]);

			Region region = Region.getRegionById(regionId);

			if (!region.isMember(playerName, true))
				throw new NotOwnerException(regionId);

			region.delete();

			ChatUtil.sendMessage(player, "La protection {" + regionId + "} a été supprimée.");
		}

		else if (args[0].equalsIgnoreCase("ajouter") && args.length == 3)
		{
			int regionId = Integer.parseInt(args[1]);
			String user = args[2];

			Region region = Region.getRegionById(regionId);

			if (!region.isMember(playerName, true))
				throw new NotOwnerException(regionId);

			region.addMember(user, false);

			ChatUtil.sendMessage(player, "Le joueur {" + user + "} est désormais membre de la protection {" + regionId
					+ "}.");
		}

		else if (args[0].equalsIgnoreCase("enlever") && args.length == 3)
		{
			int regionId = Integer.parseInt(args[1]);
			String user = args[2];

			Region region = Region.getRegionById(regionId);

			if (!region.isMember(playerName, true))
				throw new NotOwnerException(regionId);

			region.removeMember(user);

			ChatUtil.sendMessage(player, "Le joueur {" + user + "} n'est plus membre de la protection {" + regionId
					+ "}.");
		}

		else
			sendUsage(player);
	}

	@Override
	protected void onConsoleCommand(CommandSender sender, String[] args) throws HeavenException
	{
		ChatUtil.sendMessage(sender, "Cette commande n'est pas utilisable depuis la console.");
	}

	@Override
	protected void sendUsage(CommandSender sender)
	{
		ChatUtil.sendMessage(sender, "/{protection} creer : lance l'assistant de protection.");
		ChatUtil.sendMessage(sender, "/{protection} valider : valider la création d'une protection.");
		ChatUtil.sendMessage(sender, "/{protection} annuler : annule la création d'une protection");
		ChatUtil.sendMessage(sender, "/{protection} ajouter <protection> <joueur> : ajoute un joueur à la protection.");
		ChatUtil.sendMessage(sender, "/{protection} enlever <protection> <joueur> : enlève un joueur de la protection.");
		ChatUtil.sendMessage(sender, "/{protection} supprimer <protection> : supprime la protection.");
	}
}