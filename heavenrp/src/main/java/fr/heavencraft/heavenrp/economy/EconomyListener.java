package fr.heavencraft.heavenrp.economy;

import java.util.Date;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import fr.heavencraft.Utils;
import fr.heavencraft.exceptions.HeavenException;
import fr.heavencraft.heavenrp.HeavenRP;
import fr.heavencraft.heavenrp.economy.bankaccount.BankAccountsManager;
import fr.heavencraft.heavenrp.economy.bankaccount.BankAccountsManager.BankAccount;
import fr.heavencraft.heavenrp.economy.bankaccount.BankAccountsManager.BankAccountType;
import fr.heavencraft.heavenrp.general.users.User;
import fr.heavencraft.heavenrp.general.users.UserProvider;

public class EconomyListener implements Listener
{
	public EconomyListener()
	{
		Bukkit.getPluginManager().registerEvents(this, HeavenRP.getInstance());
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) throws HeavenException
	{
		Player player = event.getPlayer();
		String playerName = player.getName();

		User user = UserProvider.getUserByName(playerName);
		BankAccount account = BankAccountsManager.getBankAccount(playerName, BankAccountType.USER);

		if (!Utils.isToday(user.getLastLogin()))
		{
			user.updateBalance(25);
			Utils.sendMessage(player, ChatColor.AQUA + "Vous venez d'obtenir 25 pièces d'or en vous connectant !");

			int benefit = (int) (account.getBalance() * 0.001D);

			if (benefit > 25)
				benefit = 25;

			if (benefit > 0)
			{
				account.updateBalance(benefit);
				Utils.sendMessage(player, ChatColor.AQUA + "Votre livret vous a rapporté %1$s pièces d'or.", benefit);
			}
		}

		user.updateLastLogin(new Date());
	}

	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent event) throws HeavenException
	{
		Player player = event.getEntity();

		User user = UserProvider.getUserByName(player.getName());

		int newBalance = (int) (user.getBalance() * 0.8D);
		int delta = user.getBalance() - newBalance;

		if (delta != 0)
		{
			user.updateBalance(-delta);
			Utils.sendMessage(player, "Vous avez perdu {%1$s} pièces d'or que vous aviez dans votre bourse.",
					new Object[] { Integer.valueOf(delta) });
			Utils.sendMessage(player, "Pensez à déposer vôtre argent à la banque la prochaine fois.");
		}
	}
}