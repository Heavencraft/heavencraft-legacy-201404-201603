package fr.heavencraft.HellCraft.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;

import fr.heavencraft.HellCraft.HellCraft;

public class PlayerListener implements Listener
{
	public PlayerListener()
	{
		Bukkit.getPluginManager().registerEvents(this, HellCraft.getInstance());
	}

	@EventHandler
	public void onPlayerRespawn(PlayerRespawnEvent event)
	{
		final ItemStack[] items = { new ItemStack(Material.IRON_SWORD, 1), new ItemStack(Material.BOW, 1),
				new ItemStack(Material.BREAD, 32), new ItemStack(Material.TORCH, 16), new ItemStack(Material.ARROW, 32),
				new ItemStack(Material.IRON_HELMET, 1), new ItemStack(Material.IRON_CHESTPLATE, 1),
				new ItemStack(Material.LEATHER_LEGGINGS, 1), new ItemStack(Material.IRON_BOOTS, 1) };

		event.getPlayer().getPlayer().getInventory().addItem(items);
	}
}