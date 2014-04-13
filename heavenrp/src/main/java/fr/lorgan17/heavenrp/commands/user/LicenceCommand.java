package fr.lorgan17.heavenrp.commands.user;

import java.text.SimpleDateFormat;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.heavencraft.HeavenCommand;
import fr.heavencraft.Utils;
import fr.heavencraft.exceptions.HeavenException;
import fr.heavencraft.heavenrp.general.users.UsersManager;
import fr.heavencraft.heavenrp.general.users.UsersManager.User;

public class LicenceCommand extends HeavenCommand {
	private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

	public LicenceCommand()
	{
		super("licence");
	}

	@Override
	protected void onPlayerCommand(Player player, String[] args) throws HeavenException
	{
		User user = UsersManager.getByName(player.getName());
		
		switch (args.length)
		{
			case 1:

				if (args[0].equalsIgnoreCase("marchand"))
				{
					if (user.hasDealerLicense())
					{
						Utils.sendMessage(player, "Vous possédez déjà la licence de marchand.");
						Utils.sendMessage(player, "Elle expirera le %1$s.", dateFormat.format(user.getLicenseExpireDate()));
						Utils.sendMessage(player, "Faites {/licence marchand valider} pour acheter 1 mois supplémentaire. Il vous en coûtera 1000 pièces d'or.");
					}
					else if (user.alreadyHasDealerLicense())
						Utils.sendMessage(player, "La licence de marchand vous coûtera 1000 pièces d'or. Faites {/licence marchand valider} pour valider");
					else
						Utils.sendMessage(player, "La licence de marchand vous coûtera 500 pièces d'or. Faites {/licence marchand valider} pour valider");
				}
				/*else if (args[0].equalsIgnoreCase("ressources"))
				{
					if (user.hasSurvivalLicense())
						Utils.sendMessage(player, "Vous possédez déjà la licence d'accès au monde ressources.");
					else
						Utils.sendMessage(player, "La licence d'accès au monde ressources vous coûtera 500 pièces d'or. Faites {/licence ressources valider} pour valier");
				}*/
				break;
			case 2:

				if (args[1].equalsIgnoreCase("valider"))
				{
					if (args[0].equalsIgnoreCase("marchand"))
					{
						if (user.alreadyHasDealerLicense())
						{
							user.updateBalance(-1000);
							user.buyDealerLicense();
							Utils.sendMessage(player, "Vous venez d'acquérir la licence de marchand pour 1 mois.");
						}
						else
						{
							user.updateBalance(-500);
							user.buyDealerLicense();
							Utils.sendMessage(player, "Vous venez d'acquérir la licence de marchand pour 1 mois.");
						}
					}
					/*else if (args[0].equalsIgnoreCase("ressources"))
					{
						if (user.hasSurvivalLicense())
							Utils.sendMessage(player, "Vous possédez déjà la licence d'accès au monde ressources.");
						else
						{
							user.updateBalance(-500);
							user.buySurvivalLicense();
							Utils.sendMessage(player, "Vous venez d'acquérir la licence d'accès au monde ressources.");
						}
					}*/
				}
				
				
				break;
	
			default:
				sendUsage(player);
				return;
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
		Utils.sendMessage(sender, "/{licence} marchand : pour acheter la licence de marchand");
		//Utils.sendMessage(sender, "/{licence} ressources : pour acheter la licence d'accès au monde ressources");
	}
}
