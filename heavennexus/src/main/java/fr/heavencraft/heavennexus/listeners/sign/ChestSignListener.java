package fr.heavencraft.heavennexus.listeners.sign;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import fr.heavencraft.exceptions.HeavenException;
import fr.heavencraft.listeners.SignListener;

public class ChestSignListener extends SignListener
{
	public ChestSignListener()
	{
		super("Coffre", "");
	}

	@Override
	protected boolean onSignPlace(Player player, SignChangeEvent event)
	{
		Material material = Material.matchMaterial(event.getLine(1));

		if (material == null)
			return false;
		else
		{
			event.setLine(1, material.name());
			return true;
		}
	}

	@Override
	protected void onSignClick(Player player, Sign sign) throws HeavenException
	{
		Material material = Material.getMaterial(sign.getLine(1));

		Inventory inventory = Bukkit.createInventory(player, 54, "Heavencraft <3");
		inventory.addItem(new ItemStack(material, 3456));

		player.openInventory(inventory);
	}
}