package fr.lorgan17.heavenrp.commands.user;

import java.text.SimpleDateFormat;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.heavencraft.async.queries.QueriesHandler;
import fr.heavencraft.commands.HeavenCommand;
import fr.heavencraft.exceptions.HeavenException;
import fr.heavencraft.heavenrp.database.users.UpdateUserBalanceQuery;
import fr.heavencraft.heavenrp.general.users.User;
import fr.heavencraft.heavenrp.general.users.UserProvider;
import fr.heavencraft.utils.ChatUtil;

public class LicenceCommand extends HeavenCommand
{
	private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

	public LicenceCommand()
	{
		super("licence");
	}

	@Override
	protected void onPlayerCommand(final Player player, String[] args) throws HeavenException
	{
		final User user = UserProvider.getUserByName(player.getName());

		switch (args.length)
		{
			case 1:

				if (args[0].equalsIgnoreCase("marchand"))
				{
					if (user.hasDealerLicense())
					{
						ChatUtil.sendMessage(player, "Vous possédez déjà la licence de marchand.");
						ChatUtil.sendMessage(player, "Elle expirera le %1$s.",
								dateFormat.format(user.getLicenseExpireDate()));
						ChatUtil.sendMessage(player,
								"Faites {/licence marchand valider} pour acheter 1 mois supplémentaire. Il vous en coûtera 750 pièces d'or.");
					}
					else if (user.alreadyHasDealerLicense())
						ChatUtil.sendMessage(player,
								"La licence de marchand vous coûtera 750 pièces d'or. Faites {/licence marchand valider} pour valider");
					else
						ChatUtil.sendMessage(player,
								"La licence de marchand vous coûtera 400 pièces d'or. Faites {/licence marchand valider} pour valider");
				}
				/*
				 * else if (args[0].equalsIgnoreCase("ressources")) { if
				 * (user.hasSurvivalLicense()) ChatUtil.sendMessage(player,
				 * "Vous possédez déjà la licence d'accès au monde ressources."
				 * ); else ChatUtil.sendMessage(player,
				 * "La licence d'accès au monde ressources vous coûtera 500 pièces d'or. Faites {/licence ressources valider} pour valier"
				 * ); }
				 */
				break;
			case 2:

				if (args[1].equalsIgnoreCase("valider"))
				{
					if (args[0].equalsIgnoreCase("marchand"))
					{
						QueriesHandler.addQuery(new UpdateUserBalanceQuery(user,
								user.alreadyHasDealerLicense() ? -750 : -400)
						{
							@Override
							public void onSuccess()
							{
								user.buyDealerLicense();
								ChatUtil.sendMessage(player,
										"Vous venez d'acquérir la licence de marchand pour 1 mois.");
							}

							@Override
							public void onHeavenException(HeavenException ex)
							{
								ChatUtil.sendMessage(player, ex.getMessage());
							}
						});
					}
					/*
					 * else if (args[0].equalsIgnoreCase("ressources")) { if
					 * (user.hasSurvivalLicense()) ChatUtil.sendMessage(player,
					 * "Vous possédez déjà la licence d'accès au monde ressources."
					 * ); else { user.updateBalance(-500);
					 * user.buySurvivalLicense(); ChatUtil.sendMessage(player,
					 * "Vous venez d'acquérir la licence d'accès au monde ressources."
					 * ); } }
					 */
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
		ChatUtil.sendMessage(sender, "Cette commande n'est pas utilisable depuis la console.");
	}

	@Override
	protected void sendUsage(CommandSender sender)
	{
		ChatUtil.sendMessage(sender, "/{licence} marchand : pour acheter la licence de marchand");
		// ChatUtil.sendMessage(sender,
		// "/{licence} ressources : pour acheter la licence d'accès au monde ressources");
	}
}
