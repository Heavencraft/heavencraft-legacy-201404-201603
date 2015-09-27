package fr.heavencraft.heavenrp.shops;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Chest;
import org.bukkit.block.DoubleChest;
import org.bukkit.block.Sign;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import fr.heavencraft.exceptions.HeavenException;
import fr.heavencraft.heavenrp.database.bankaccounts.BankAccount;
import fr.heavencraft.heavenrp.database.bankaccounts.BankAccountType;
import fr.heavencraft.heavenrp.database.bankaccounts.BankAccountsManager;
import fr.heavencraft.listeners.sign.SignListener;
import fr.heavencraft.utils.ChatUtil;
import fr.heavencraft.utils.DevUtil;

public class Stock2SignListener extends SignListener
{
	private static final BlockFace[] FACES =
	{ BlockFace.NORTH, BlockFace.EAST, BlockFace.SOUTH, BlockFace.WEST, BlockFace.DOWN };

	public Stock2SignListener()
	{
		super("Coffre2", "");
	}

	@Override
	protected boolean onSignPlace(Player player, SignChangeEvent event) throws HeavenException
	{
		Block sign = event.getBlock();
		Chest chest = getRelativeChest(event.getBlock());

		for (ItemStack item : chest.getInventory().getContents())
			if (item.getAmount() != 0)
				throw new HeavenException("Le coffre doit être vide.");

		String name = event.getLine(1);
		String account = event.getLine(2);

		BankAccount bankAccount;

		try
		{
			// Ville, entreprise
			bankAccount = BankAccountsManager.getBankAccountById(DevUtil.toUint(account));
		}
		catch (HeavenException ex)
		{
			// Joueur
			bankAccount = BankAccountsManager.getBankAccount(player.getName(), BankAccountType.USER);
		}

		if (!bankAccount.getOwnersNames().contains(player.getName()))
			throw new HeavenException("Vous n'êtes pas propriétaire de ce compte.");

		Stock2Provider.createChest(name, bankAccount, getChestLocation(chest), sign.getLocation());
		return true;
	}

	@Override
	protected void onSignClick(Player player, Sign sign) throws HeavenException
	{
		// Do nothing
	}

	@Override
	protected void onSignBreak(Player player, Sign sign) throws HeavenException
	{
		try
		{
			Stock2 stock = Stock2Provider.getStockBySignLocation(sign.getLocation());

			for (CommandSender owner : stock.getBankAccount().getOwners())
				ChatUtil.sendMessage(owner, "Le panneau du coffre {%1$s} vient d'être cassé.", stock.getName());

			stock.remove();
		}
		catch (HeavenException ex)
		{
			ex.printStackTrace();
			return;
		}
	}

	@EventHandler(ignoreCancelled = true)
	private void onBlockBreak(BlockBreakEvent event)
	{
		Block block = event.getBlock();

		if (block.getType() != Material.CHEST)
			return;

		try
		{
			Stock2 stock = Stock2Provider.getStockByChestLocation(getChestLocation((Chest) block.getState()));

			for (CommandSender owner : stock.getBankAccount().getOwners())
				ChatUtil.sendMessage(owner, "Le coffre {%1$s} vient d'être cassé.", stock.getName());

			stock.remove();
		}
		catch (HeavenException ex)
		{
			// ex.printStackTrace();
			DevUtil.logInfo(ex.getMessage());
			return;
		}
	}

	private static Chest getRelativeChest(Block block) throws HeavenException
	{
		for (BlockFace face : FACES)
		{
			Block relativeBlock = block.getRelative(face);

			if (relativeBlock.getType() == Material.CHEST && relativeBlock.getState() instanceof Chest)
				return (Chest) relativeBlock.getState();
		}

		throw new HeavenException("Aucun coffre n'a été trouvé.");
	}

	private static Location getChestLocation(Chest chest)
	{
		InventoryHolder holder = chest.getInventory().getHolder();

		if (holder instanceof DoubleChest)
			return ((Chest) ((DoubleChest) holder).getLeftSide()).getLocation();
		else
			return chest.getLocation();
	}
}