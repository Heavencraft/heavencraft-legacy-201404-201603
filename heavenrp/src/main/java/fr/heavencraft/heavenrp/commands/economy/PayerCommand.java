package fr.heavencraft.heavenrp.commands.economy;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.heavencraft.commands.HeavenCommand;
import fr.heavencraft.exceptions.HeavenException;
import fr.heavencraft.heavenrp.economy.bankaccount.BankAccountsManager;
import fr.heavencraft.heavenrp.economy.bankaccount.BankAccountsManager.BankAccount;
import fr.heavencraft.heavenrp.economy.bankaccount.BankAccountsManager.BankAccountType;
import fr.heavencraft.heavenrp.general.users.User;
import fr.heavencraft.heavenrp.general.users.UserProvider;
import fr.heavencraft.utils.ChatUtil;
import fr.heavencraft.utils.DevUtil;
import fr.heavencraft.utils.PlayerUtil;

public class PayerCommand extends HeavenCommand
{
	private final static String MONEY_NOW = "Vous avez maintenant {%1$d} pièces d'or.";
	private final static String MONEY_BANK_NOW = "Vous avez maintenant {%1$d} pièces d'or sur votre livret.";
	private final static String MONEY_GIVE = "Vous avez envoyé {%1$d} pièces d'or à {%2$s}.";
	private final static String MONEY_RECEIVE = "Vous avez reçu {%1$d} pièces d'or de {%2$s}.";

	public PayerCommand()
	{
		super("payer");
	}

	@Override
	protected void onPlayerCommand(Player player, String[] args) throws HeavenException
	{
		if (args.length != 3)
		{
			sendUsage(player);
			return;
		}

		BankAccount dest;

		if (args[0].equalsIgnoreCase("joueur"))
			dest = BankAccountsManager.getBankAccount(PlayerUtil.getExactName(args[1]), BankAccountType.USER);

		else if (args[0].equalsIgnoreCase("ville"))
			dest = BankAccountsManager.getBankAccount(args[1], BankAccountType.TOWN);

		else if (args[0].equalsIgnoreCase("entreprise"))
			dest = BankAccountsManager.getBankAccount(args[1], BankAccountType.ENTERPRISE);

		else
		{
			sendUsage(player);
			return;
		}

		if (dest.getOwnersNames().contains(player.getName()))
			throw new HeavenException("Vous devez utiliser le guichet afin de faire des opérations sur votre compte");

		int delta = DevUtil.toUint(args[2]);

		User sender = UserProvider.getUserByName(player.getName());

		sender.updateBalance(-delta);
		dest.updateBalance(delta);

		ChatUtil.sendMessage(player, MONEY_GIVE, delta, dest.getName());
		ChatUtil.sendMessage(player, MONEY_NOW, sender.getBalance());

		ChatUtil.sendMessage(dest.getOwners(), MONEY_RECEIVE, delta, sender.getName());
		ChatUtil.sendMessage(dest.getOwners(), MONEY_BANK_NOW, dest.getBalance());

		DevUtil.logInfo("%1$s sent %2$s po to bank account %3$s.", player.getName(), delta, dest.getName());
	}

	@Override
	protected void onConsoleCommand(CommandSender sender, String[] args) throws HeavenException
	{
	}

	@Override
	protected void sendUsage(CommandSender sender)
	{
		ChatUtil.sendMessage(sender, "/{payer} joueur <nom du joueur> <somme>");
		ChatUtil.sendMessage(sender, "/{payer} ville <nom de la ville> <somme>");
		ChatUtil.sendMessage(sender, "/{payer} entreprise <nom de l'entreprise> <somme>");
	}
}