package fr.heavencraft.heavenrp.homes;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.heavencraft.HeavenCommand;
import fr.heavencraft.Utils;
import fr.heavencraft.exceptions.HeavenException;
import fr.heavencraft.heavenrp.general.users.UsersManager;
import fr.heavencraft.heavenrp.general.users.UsersManager.User;

public class BuyhomeCommand extends HeavenCommand {

	public BuyhomeCommand()
	{
		super("buyhome");
	}

	@Override
	protected void onPlayerCommand(Player player, String[] args) throws HeavenException
	{
		User user = UsersManager.getByName(player.getName());
		
		int homeNumber = user.getHomeNumber() + 1;
		int price = getPrice(homeNumber);
		
		switch (args.length)
		{
			case 0:
				Utils.sendMessage(player, "Le {home %1$d} vous coûtera {%2$d} pièces d'or. Tapez /buyhome valider pour l'acheter.", homeNumber, price);
				break;
			case 1:
				if (!args[0].equalsIgnoreCase("valider"))
				{
					sendUsage(player);
					return;
				}
				
				user.updateBalance(-price);
				user.incrementHomeNumber();
				
				Utils.sendMessage(player, "Votre {home %1$d} a bien été acheté pour {%2$d} pièces d'or.", homeNumber, price);
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
		Utils.sendMessage(sender, "/{buyhome}");
	}
	
	private static int getPrice(int homeNumber) throws HeavenException
	{
		switch (homeNumber)
		{
			case 3: return 1000;
			case 4: return 2000;
			case 5: return 3000;
			case 6: return 4000;
			case 7: return 5000;
			case 8: return 6000;
			case 9: return 7000;
			case 10: return 8000;
			default: throw new HeavenException("Vous avez déjà acheté tous les points d'habitation.");
		}
	}
}