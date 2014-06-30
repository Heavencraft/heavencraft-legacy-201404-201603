package fr.heavencraft.listeners.sign;

import java.util.Random;

import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import fr.heavencraft.Permissions;
import fr.heavencraft.exceptions.HeavenException;

public class CookieSignListener extends SignListener
{
	private static final Random rand = new Random();

	public CookieSignListener()
	{
		super("Cookie", Permissions.COOKIE_SIGN);
	}

	@Override
	protected boolean onSignPlace(Player player, SignChangeEvent event) throws HeavenException
	{
		return true;
	}

	@Override
	protected void onSignClick(Player player, Sign sign) throws HeavenException
	{
		if (rand.nextInt(10) != 0)
			return;

		ItemStack cookie = new ItemStack(Material.COOKIE);
		ItemMeta meta = cookie.getItemMeta();
		meta.setDisplayName("Cookie de l'amitié");
		cookie.setItemMeta(meta);
		player.getInventory().addItem(cookie);
		player.updateInventory();
	}
}