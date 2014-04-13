package fr.heavencraft.heavenrp.economy;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import fr.heavencraft.Permissions;
import fr.heavencraft.SignListener;
import fr.heavencraft.Utils;
import fr.heavencraft.exceptions.HeavenException;
import fr.heavencraft.heavenrp.economy.bankaccount.BankAccountsManager;
import fr.heavencraft.heavenrp.economy.bankaccount.BankAccountsManager.BankAccount;
import fr.heavencraft.heavenrp.economy.bankaccount.BankAccountsManager.BankAccountType;
import fr.heavencraft.heavenrp.general.users.UsersManager;
import fr.heavencraft.heavenrp.general.users.UsersManager.User;

public class LivretSignListener extends SignListener implements Listener
{
	private static final String CONSULTER = "Consulter";
	private static final String DEPOSER = "Déposer";
	private static final String RETIRER = "Retirer";

	private final List<String> deposants = new ArrayList<String>();
	private final List<String> retirants = new ArrayList<String>();

	public LivretSignListener()
	{
		super("Livret", Permissions.LIVRET_SIGN);

		Utils.registerListener(this);
	}

	@Override
	protected boolean onSignPlace(Player player, SignChangeEvent event)
	{
		if (event.getLine(1).equalsIgnoreCase(CONSULTER))
		{
			event.setLine(1, ChatColor.BLUE + CONSULTER);
			return true;
		}

		else if (event.getLine(1).equalsIgnoreCase(DEPOSER))
		{
			event.setLine(1, ChatColor.BLUE + DEPOSER);
			return true;
		}

		else if (event.getLine(1).equalsIgnoreCase(RETIRER))
		{
			event.setLine(1, ChatColor.BLUE + RETIRER);
			return true;
		}

		else
			return false;
	}

	@Override
	protected void onSignClick(Player player, Sign sign) throws HeavenException
	{
		String playerName = player.getName();

		if (sign.getLine(1).equals(ChatColor.BLUE + CONSULTER))
		{
			Utils.sendMessage(player, "{Trésorier} : Vous avez {%1$d} pièces d'or sur votre livret.",
					BankAccountsManager.getBankAccount(playerName, BankAccountType.USER).getBalance());
		}

		else if (sign.getLine(1).equals(ChatColor.BLUE + DEPOSER))
		{
			Utils.sendMessage(player, "{Trésorier} : Combien de pièces d'or souhaitez-vous déposer ?");

			if (!deposants.contains(playerName))
				deposants.add(playerName);
		}

		else if (sign.getLine(1).equals(ChatColor.BLUE + RETIRER))
		{
			Utils.sendMessage(player, "{Trésorier} : Combien de pièces d'or souhaitez-vous retirer ?");

			if (!retirants.contains(playerName))
				retirants.add(playerName);
		}
	}

	@EventHandler(ignoreCancelled = true)
	public void onAsyncPlayerChat(AsyncPlayerChatEvent event)
	{
		Player player = event.getPlayer();
		String playerName = player.getName();
		boolean isDepot = false;

		if (deposants.contains(playerName))
		{
			deposants.remove(playerName);
			isDepot = true;
		}
		else if (retirants.contains(playerName))
		{
			retirants.remove(playerName);
			isDepot = false;
		}
		else
		{
			return;
		}

		event.setCancelled(true);

		try
		{
			int delta = Utils.toUint(event.getMessage());

			User user = UsersManager.getByName(playerName);
			BankAccount bank = BankAccountsManager.getBankAccount(playerName, BankAccountType.USER);

			if (isDepot)
			{
				user.updateBalance(-delta);
				bank.updateBalance(delta);
			}
			else
			{
				bank.updateBalance(-delta);
				user.updateBalance(delta);
			}

			Utils.sendMessage(player, "{Trésorier} : L'opération a bien été effectuée.");
		}
		catch (HeavenException ex)
		{
			Utils.sendMessage(player, ex.getMessage());
		}
	}
}