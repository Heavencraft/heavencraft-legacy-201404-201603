package fr.lorgan17.heavenrp.commands.user;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.heavencraft.HeavenCommand;
import fr.heavencraft.Utils;
import fr.heavencraft.exceptions.HeavenException;
import fr.heavencraft.heavenrp.HeavenRP;
import fr.lorgan17.heavenrp.managers.AuctionManager;

public class EncheresCommand extends HeavenCommand
{
	public EncheresCommand()
	{
		super("enchere");
	}

	@Override
	protected void onPlayerCommand(Player player, String[] args) throws HeavenException
	{
		if (args.length == 0)
		{
			sendUsage(player);
			return;
		}
		
		if (args[0].equalsIgnoreCase("creer") && args.length > 3)
		{
			if (!player.hasPermission(AuctionManager.PERMISSION))
				return;
			
			String objectName = "";
			
			for (int i = 2; i != args.length; i++)
				objectName += args[i] + " ";
			
			objectName = objectName.substring(0, objectName.length() - 1);
			int startPrice = Integer.parseInt(args[1]);
			
			HeavenRP.getAuctionManager().create(player, objectName, startPrice);
		}
		else if (args[0].equalsIgnoreCase("encherir") && args.length == 2)
		{
			int newPrice = Integer.parseInt(args[1]);
			
			HeavenRP.getAuctionManager().bid(player.getName(), newPrice);
		}
		else if (args[0].equalsIgnoreCase("stop") && args.length == 1)
		{
			if (!player.hasPermission(AuctionManager.PERMISSION))
				return;
			
			HeavenRP.getAuctionManager().stop();
		}
		else if (args[0].equalsIgnoreCase("entrer") && args.length == 1)
		{
			HeavenRP.getAuctionManager().enterRoom(player);
		}
		else if (args[0].equalsIgnoreCase("sortir") && args.length == 1)
		{
			HeavenRP.getAuctionManager().exitRoom(player);
		}
		else
			sendUsage(player);
	}

	@Override
	protected void onConsoleCommand(CommandSender sender, String[] args) throws HeavenException
	{
		Utils.sendMessage(sender, "Cette commande ne peut pas être utilisée depuis la {console}.");
	}

	@Override
	protected void sendUsage(CommandSender sender)
	{
		Utils.sendMessage(sender, "Enchères :");
		Utils.sendMessage(sender, "{/enchere} creer <prix de départ> <Objet>");
		Utils.sendMessage(sender, "{/enchere} entrer : entrer dans la salle d'enchères");
		Utils.sendMessage(sender, "{/enchere} encherir <prix> : enchérir");
		Utils.sendMessage(sender, "{/enchere} sortir : sortir de la salle d'enchères");
		Utils.sendMessage(sender, "{/enchere} stop");
	}
}