package net.milkbowl.vault.economy.plugins;

import java.util.List;

import net.milkbowl.vault.economy.AbstractEconomy;
import net.milkbowl.vault.economy.EconomyResponse;
import net.milkbowl.vault.economy.EconomyResponse.ResponseType;
import fr.heavencraft.exceptions.HeavenException;
import fr.heavencraft.heavenrp.general.users.UserProvider;

public class Economy_HeavenRP extends AbstractEconomy
{
	@Override
	public boolean isEnabled()
	{
		return true;
	}

	@Override
	public String getName()
	{
		return "HeavenRP";
	}

	@Override
	public boolean hasBankSupport()
	{
		return false;
	}

	@Override
	public int fractionalDigits()
	{
		return 0;
	}

	@Override
	public String format(double amount)
	{
		return null;
	}

	@Override
	public String currencyNamePlural()
	{
		return "pièces d'or";
	}

	@Override
	public String currencyNameSingular()
	{
		return "pièce d'or";
	}

	@Override
	public boolean hasAccount(String playerName)
	{
		return true;
	}

	@Override
	public boolean hasAccount(String playerName, String worldName)
	{
		return false;
	}

	@Override
	public double getBalance(String playerName)
	{
		try
		{
			return UserProvider.getUserByName(playerName).getBalance();
		}
		catch (HeavenException ex)
		{
			ex.printStackTrace();
			return 0;
		}
	}

	@Override
	public boolean has(String playerName, double amount)
	{
		return getBalance(playerName) >= amount;
	}

	@Override
	public EconomyResponse withdrawPlayer(String playerName, double amount)
	{
		try
		{
			UserProvider.getUserByName(playerName).updateBalance(-toInt(amount));
			return new EconomyResponse(amount, getBalance(playerName), ResponseType.SUCCESS, "");
		}
		catch (HeavenException ex)
		{
			ex.printStackTrace();
			return new EconomyResponse(amount, getBalance(playerName), ResponseType.FAILURE, ex.getMessage());
		}
	}

	@Override
	public EconomyResponse depositPlayer(String playerName, double amount)
	{
		try
		{
			UserProvider.getUserByName(playerName).updateBalance(toInt(amount));
			return new EconomyResponse(amount, getBalance(playerName), ResponseType.SUCCESS, "");
		}
		catch (HeavenException ex)
		{
			ex.printStackTrace();
			return new EconomyResponse(amount, getBalance(playerName), ResponseType.FAILURE, ex.getMessage());
		}
	}

	@Override
	public boolean createPlayerAccount(String playerName)
	{
		return false;
	}

	/*
	 * We don't support account by world
	 */

	@Override
	public double getBalance(String playerName, String world)
	{
		return 0;
	}

	@Override
	public boolean has(String playerName, String worldName, double amount)
	{
		return false;
	}

	@Override
	public EconomyResponse withdrawPlayer(String playerName, String worldName, double amount)
	{
		return null;
	}

	@Override
	public EconomyResponse depositPlayer(String playerName, String worldName, double amount)
	{
		return null;
	}

	@Override
	public boolean createPlayerAccount(String playerName, String worldName)
	{
		return false;
	}

	/*
	 * We don't support bank
	 */

	@Override
	public EconomyResponse createBank(String name, String player)
	{
		return null;
	}

	@Override
	public EconomyResponse deleteBank(String name)
	{
		return null;
	}

	@Override
	public EconomyResponse bankBalance(String name)
	{
		return null;
	}

	@Override
	public EconomyResponse bankHas(String name, double amount)
	{
		return null;
	}

	@Override
	public EconomyResponse bankWithdraw(String name, double amount)
	{
		return null;
	}

	@Override
	public EconomyResponse bankDeposit(String name, double amount)
	{
		return null;
	}

	@Override
	public EconomyResponse isBankOwner(String name, String playerName)
	{
		return null;
	}

	@Override
	public EconomyResponse isBankMember(String name, String playerName)
	{
		return null;
	}

	@Override
	public List<String> getBanks()
	{
		return null;
	}

	private static int toInt(double amount) throws HeavenException
	{
		if (amount % 1 == 0)
		{
			return (int) amount;
		}
		else
		{
			throw new HeavenException("{%1$s} n'est pas un nombre entier.", amount);
		}
	}
}