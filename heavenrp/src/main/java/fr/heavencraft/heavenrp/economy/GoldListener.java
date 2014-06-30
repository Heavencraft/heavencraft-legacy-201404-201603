package fr.heavencraft.heavenrp.economy;

import static fr.heavencraft.utils.DevUtil.registerListener;

import java.util.Arrays;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryPickupItemEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import fr.heavencraft.exceptions.HeavenException;
import fr.heavencraft.heavenrp.general.users.User;
import fr.heavencraft.heavenrp.general.users.UserProvider;
import fr.heavencraft.utils.ChatUtil;
import fr.heavencraft.utils.DevUtil;

public class GoldListener implements Listener
{
	private static final Material GOLD_MATERIAL = Material.GOLD_NUGGET;
	private static final String GOLD_NAME = "Pièce d'or";

	public GoldListener()
	{
		registerListener(this);
	}

	@EventHandler(ignoreCancelled = true)
	private void onPlayerPickupItem(PlayerPickupItemEvent event)
	{
		Player player = event.getPlayer();

		try
		{
			if (!player.isOp())
				return;

			ItemStack item = event.getItem().getItemStack();

			if (!isGold(item))
				return;

			int amount = DevUtil.toUint(item.getItemMeta().getLore().get(0));
			event.getItem().remove();
			event.setCancelled(true);

			UserProvider.getUserByName(player.getName()).updateBalance(amount);
			ChatUtil.sendMessage(player, "Vous venez de trouver {%1$s} pièces d'or par terre. ({%2$s})", amount,
					event.getRemaining());
		}
		catch (HeavenException ex)
		{
			ex.printStackTrace();
			ChatUtil.sendMessage(player, ex.getMessage());
		}
	}

	@EventHandler(ignoreCancelled = true)
	private void onInventoryPickupItem(InventoryPickupItemEvent event)
	{
		if (isGold(event.getItem().getItemStack()))
		{
			event.getItem().remove();
			event.setCancelled(true);
		}
	}

	private static boolean isGold(ItemStack item)
	{
		if (item.getType() != GOLD_MATERIAL || !item.hasItemMeta())
			return false;

		ItemMeta meta = item.getItemMeta();

		if (!meta.hasDisplayName() || !meta.hasLore() || !GOLD_NAME.equals(item.getItemMeta().getDisplayName()))
			return false;

		return true;
	}

	private void onPlayerDeath2(PlayerDeathEvent event) throws HeavenException
	{

		Player player = event.getEntity();
		User user = UserProvider.getUserByName(player.getName());

		int amount = user.getBalance();

		if (amount != 0)
		{
			user.updateBalance(-amount);
			ChatUtil.sendMessage(player, "Vous avez perdu {%1$s} pièces d'or que vous aviez dans votre bourse.", amount);
			ChatUtil.sendMessage(player, "Pensez à déposer votre argent à la banque la prochaine fois.");

			dropGold(player.getLocation(), amount);
		}
	}

	private void dropGold(Location location, int qty)
	{
		ItemStack item = new ItemStack(GOLD_MATERIAL, 1);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(GOLD_NAME);
		meta.setLore(Arrays.asList(Integer.toString(qty)));
		item.setItemMeta(meta);
		location.getWorld().dropItem(location, item);
	}

	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent event) throws HeavenException
	{
		Player player = event.getEntity();

		if (player.isOp())
		{
			onPlayerDeath2(event);
			return;
		}

		User user = UserProvider.getUserByName(player.getName());

		int newBalance = (int) (user.getBalance() * 0.8D);
		int delta = user.getBalance() - newBalance;

		if (delta != 0)
		{
			user.updateBalance(-delta);
			ChatUtil.sendMessage(player, "Vous avez perdu {%1$s} pièces d'or que vous aviez dans votre bourse.",
					new Object[] { Integer.valueOf(delta) });
			ChatUtil.sendMessage(player, "Pensez à déposer vôtre argent à la banque la prochaine fois.");
		}
	}
}