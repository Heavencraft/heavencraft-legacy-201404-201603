package fr.heavencraft.heavenrp.economy;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.heavencraft.HeavenCommand;
import fr.heavencraft.Permissions;
import fr.heavencraft.Utils;
import fr.heavencraft.exceptions.HeavenException;
import fr.heavencraft.heavenrp.economy.bankaccount.BankAccountsManager;
import fr.heavencraft.heavenrp.economy.bankaccount.BankAccountsManager.BankAccountType;

public class LivretproCommand extends HeavenCommand
{
	public LivretproCommand()
	{
		super("livretpro", Permissions.LIVRETPRO_COMMAND);
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
		case 2:
			if (args[0].equalsIgnoreCase("+ville"))
			{
				BankAccountsManager.createBankAccount(args[1], BankAccountType.TOWN);
				Utils.sendMessage(sender, "Le livret pro de la ville {%1$s} a bien été créé");
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
		Utils.sendMessage(sender, "/{livretpro} +ville <nom de la ville>");
		Utils.sendMessage(sender, "/{livretpro} -ville <nom de la ville>");
	}
}