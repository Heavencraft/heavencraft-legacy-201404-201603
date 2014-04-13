package fr.heavencraft.heavenrp.economy;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

public class LivretProSignListener extends SignListener implements Listener
{
	private static final String CONSULTER = "Consulter";
	private static final String DEPOSER = "Déposer";
	private static final String RETIRER = "Retirer";

	private final Map<String, Integer> deposants = new HashMap<String, Integer>();
	private final Map<String, Integer> retirants = new HashMap<String, Integer>();
	
	public LivretProSignListener()
	{
		super("LivretPro", Permissions.LIVRETPRO_SIGN);
		
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
			if (!deposants.containsKey(playerName))
			{
				displayAccounts(player);
				Utils.sendMessage(player, "{Trésorier} : Sur quel livret voulez-vous déposer ?");
				deposants.put(playerName, -1);
			}
		}
		
		else if (sign.getLine(1).equals(ChatColor.BLUE + RETIRER))
		{
			if (!retirants.containsKey(playerName))
			{
				displayAccounts(player);
				Utils.sendMessage(player, "{Trésorier} : Sur quel livret voulez-vous retirer ?");
				retirants.put(playerName, -1);
			}
		}
	}
	
	public void displayAccounts(Player player) throws HeavenException
	{
		String playerName = player.getName();
		
		List<BankAccount> accounts = BankAccountsManager.getAccountByOwner(playerName);
		
		if (accounts.size() == 0)
			throw new HeavenException("{Trésorier} : Vous n'avez accès à aucun livret...");
		
		Utils.sendMessage(player, "{Trésorier} : Voici la liste de vos livrets :");
		
		for (BankAccount account : accounts)
			Utils.sendMessage(player, "{%1$s} (%2$s) : %3$s pièces d'or", account.getId(), account.getName(), account.getBalance());
	}
	
	@EventHandler(ignoreCancelled = true)
	public void onAsyncPlayerChat(AsyncPlayerChatEvent event)
	{
		Player player = event.getPlayer();
		String playerName = player.getName();
		
		int accountId;
		boolean isDepot = false;

		if (deposants.containsKey(playerName))
		{
			accountId = deposants.get(playerName);
			isDepot = true;
		}
		else if (retirants.containsKey(playerName))
		{
			accountId = retirants.get(playerName);
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
			
			if (accountId == -1)
			{
				selectAccount(player, delta, isDepot ? deposants : retirants);
				return;
			}

			User user = UsersManager.getByName(playerName);
			BankAccount bank = BankAccountsManager.getBankAccountById(accountId);

			if (isDepot)
			{
				user.updateBalance(-delta);
				bank.updateBalance(delta);
				deposants.remove(playerName);
			}
			else
			{
				bank.updateBalance(-delta);
				user.updateBalance(delta);
				retirants.remove(playerName);
			}

			Utils.sendMessage(player, "{Trésorier} : L'opération a été effectuée avec succès.");
		}
		catch (HeavenException ex)
		{
			deposants.remove(playerName);
			retirants.remove(playerName);
			Utils.sendMessage(player, ex.getMessage());
		}
	}
	
	private void selectAccount(Player player, int id, Map<String, Integer> list) throws HeavenException
	{
		BankAccount account = BankAccountsManager.getBankAccountById(id);
		
		if (!account.getOwners().contains(player))
			throw new HeavenException("{Trésorier} : Vous n'êtes pas propriétaire de ce compte.");
		
		list.put(player.getName(), id);
		
		if (list.equals(deposants))
			Utils.sendMessage(player, "{Trésorier} : Combien de pièces d'or souhaitez-vous déposer ?");
		else
			Utils.sendMessage(player, "{Trésorier} : Combien de pièces d'or souhaitez-vous retirer ?");
	}
}