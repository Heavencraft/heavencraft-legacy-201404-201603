package fr.lorgan17.heavenrp.listeners.sign;

import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import fr.heavencraft.exceptions.HeavenException;
import fr.heavencraft.heavenrp.HeavenRP;
import fr.heavencraft.listeners.SignListener;

public class AdminShop extends SignListener
{
	public AdminShop()
	{
		super("AdminShop", "heavenrp.administrator.sign.adminshop");
	}

	@Override
	protected boolean onSignPlace(Player player, SignChangeEvent event)
	{
		Material material = Material.valueOf(event.getLine(1));
		
		return material != null;
	}

	@Override
	protected void onSignClick(Player player, Sign sign) throws HeavenException
	{
		ItemStack item = getItemStack(Material.valueOf(sign.getLine(1)),
				sign.getLine(2),
				Integer.parseInt(sign.getLine(3)));
		
		player.getInventory().addItem(item);
	}
	
	private ItemStack getItemStack(Material type, String line2, int amount)
	{
		ItemStack item;
		
		switch (type)
		{
		case SKULL_ITEM:
			item = new ItemStack(Material.SKULL_ITEM, amount, (short) 3);
			
			SkullMeta meta = (SkullMeta) item.getItemMeta();
			meta.setOwner(line2);
			item.setItemMeta(meta);
			break;
			
		default:
			item = new ItemStack(type, amount);
			break;
		}
		
		return item;
	}
}