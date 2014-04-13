package fr.lorgan17.lorganserver.commands.user;

import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.lorgan17.lorganserver.LorganServer;
import fr.lorgan17.lorganserver.commands.LorganCommand;
import fr.lorgan17.lorganserver.entities.Region;
import fr.lorgan17.lorganserver.exceptions.LorganException;
import fr.lorgan17.lorganserver.exceptions.NotEnoughNuggetsException;
import fr.lorgan17.lorganserver.exceptions.NotOwnerException;
import fr.lorgan17.lorganserver.managers.MoneyManager;
import fr.lorgan17.lorganserver.managers.WorldsManager;
import fr.lorgan17.lorganserver.managers.SelectionManager.Pair;

public class ProtectionCommand extends LorganCommand {

	public ProtectionCommand()
	{
		super("protection");
	}

	@Override
	protected void onPlayerCommand(Player player, String[] args) throws LorganException
	{
		if (!player.getWorld().equals(WorldsManager.getWorld()))
		{
			LorganServer.sendMessage(player, "Cette commande n'est pas accessible dans ce monde.");
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
			_plugin.getSelectionManager().enable(player.getName());
			LorganServer.sendMessage(player, "Bienvenue dans l'assistant de protection.");
			LorganServer.sendMessage(player, "Utilisez le clic gauche et le clic droit avec un bâton pour délimiter votre protection");
			LorganServer.sendMessage(player, "Puis faites /protection valider ou /protection annuler");
		}
		
		else if (args[0].equalsIgnoreCase("annuler"))
		{
			_plugin.getSelectionManager().disable(player.getName());
			LorganServer.sendMessage(player, "Création de la protection annulée.");
		}
		
		else if (args[0].equalsIgnoreCase("valider"))
		{
			int amount = _plugin.getSelectionManager().getPrice(playerName);
			
			if (!MoneyManager.hasEnough(player, amount))
				throw new NotEnoughNuggetsException(amount);
			
			Pair<Location, Location> s = _plugin.getSelectionManager().getSelection(player.getName());
			Region.createRegion(playerName, s.first.getBlockX(), s.first.getBlockZ(), s.second.getBlockX(), s.second.getBlockZ());
			
			MoneyManager.pay(player, amount);

			_plugin.getSelectionManager().disable(playerName);
			LorganServer.sendMessage(player, "Votre protection a été créée.");
			LorganServer.sendMessage(player, "Vous pouvez utiliser /protection ajouter pour ajouter des membres à votre protection.");
		}
		
		else if (args[0].equalsIgnoreCase("supprimer") && args.length == 2)
		{
			int regionId = Integer.parseInt(args[1]);

			Region region = Region.getRegionById(regionId);
			
			if (!region.isMember(playerName, true))
				throw new NotOwnerException(regionId);
			
			region.delete();
			
			LorganServer.sendMessage(player, "La protection {" + regionId + "} a été supprimée.");
		}

		else if (args[0].equalsIgnoreCase("ajouter") && args.length == 3)
		{
			int regionId = Integer.parseInt(args[1]);
			String user = args[2];
			
			Region region = Region.getRegionById(regionId);
			
			if (!region.isMember(playerName, true))
				throw new NotOwnerException(regionId);
			
			region.addMember(user, false);
			
			LorganServer.sendMessage(player, "Le joueur {" + user + "} est désormais membre de la protection {" + regionId + "}.");
		}
		
		else if (args[0].equalsIgnoreCase("enlever") && args.length == 3)
		{
			int regionId = Integer.parseInt(args[1]);
			String user = args[2];
			
			Region region = Region.getRegionById(regionId);
			
			if (!region.isMember(playerName, true))
				throw new NotOwnerException(regionId);
			
			region.removeMember(user);

			LorganServer.sendMessage(player, "Le joueur {" + user + "} n'est plus membre de la protection {" + regionId + "}.");
		}
		
		else
			sendUsage(player);
	}

	@Override
	protected void onConsoleCommand(CommandSender sender, String[] args) throws LorganException
	{
		LorganServer.sendMessage(sender, "Cette commande n'est pas utilisable depuis la console.");
	}

	@Override
	protected void sendUsage(CommandSender sender)
	{
		LorganServer.sendMessage(sender, "/{protection} creer : lance l'assistant de protection.");
		LorganServer.sendMessage(sender, "/{protection} valider : valider la création d'une protection.");
		LorganServer.sendMessage(sender, "/{protection} annuler : annule la création d'une protection");
		LorganServer.sendMessage(sender, "/{protection} ajouter <protection> <joueur> : ajoute un joueur à la protection.");
		LorganServer.sendMessage(sender, "/{protection} enlever <protection> <joueur> : enlève un joueur de la protection.");
		LorganServer.sendMessage(sender, "/{protection} supprimer <protection> : supprime la protection.");
	}
}
