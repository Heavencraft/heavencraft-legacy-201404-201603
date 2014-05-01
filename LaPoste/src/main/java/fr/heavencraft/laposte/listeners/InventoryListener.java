package fr.heavencraft.laposte.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import fr.heavencraft.laposte.LaPoste;

public class InventoryListener implements Listener{
	public InventoryListener()
	{
		Bukkit.getPluginManager().registerEvents(this, LaPoste.getInstance());
	}
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
	Player player = (Player) event.getWhoClicked(); // The player that clicked the item
	Inventory inventory = event.getInventory(); // The inventory that was clicked in
	

	}
}
