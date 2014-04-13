package fr.heavencraft.NavalConflicts.Tools;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import fr.heavencraft.NavalConflicts.NavalConflicts;

public class IconMenu implements Listener {
	private String name;
	private int size;
	private OptionClickEventHandler handler;

	private String[] optionNames;
	private ItemStack[] optionIcons;

	public IconMenu(String name, int size, OptionClickEventHandler handler)
	{
		this.name = name;
		this.size = size;
		this.handler = handler;
		this.optionNames = new String[size];
		this.optionIcons = new ItemStack[size];
		NavalConflicts.getInstance().getServer().getPluginManager().registerEvents(this, NavalConflicts.getInstance());
	}

	public IconMenu setOption(int position, ItemStack icon, String name, String... info) {
		optionNames[position] = name;
		optionIcons[position] = setItemNameAndLore(icon, name, info);
		return this;
	}

	public void open(Player player) {
		Inventory inventory = Bukkit.createInventory(player, size, name);
		for (int i = 0; i < optionIcons.length; i++)
		{
			if (optionIcons[i] != null)
			{
				inventory.setItem(i, optionIcons[i]);
			}
		}
		player.openInventory(inventory);
	}

	public void destroy() {
		HandlerList.unregisterAll(this);
		handler = null;
		optionNames = null;
		optionIcons = null;
	}

	@EventHandler(priority = EventPriority.MONITOR)
	void onInventoryClick(InventoryClickEvent event) {
		if (event.getInventory().getTitle().equals(name))
		{
			event.setCancelled(true);
			int slot = event.getRawSlot();
			if (slot >= 0 && slot < size && optionNames[slot] != null)
			{
				OptionClickEvent e = new OptionClickEvent((Player) event.getWhoClicked(), slot,optionNames[slot], event.getClick());
				handler.onOptionClick(e);
				if (e.willClose())
				{
					final Player p = (Player) event.getWhoClicked();
					Bukkit.getScheduler().scheduleSyncDelayedTask(NavalConflicts.getInstance(), new Runnable()
					{

						public void run() {
							p.closeInventory();
						}
					}, 1);
				}
				if (e.willDestroy())
				{
					destroy();
				}
			}
		}
	}

	public interface OptionClickEventHandler {

		public void onOptionClick(OptionClickEvent event);
	}

	public class OptionClickEvent {

		private Player player;
		private int position;
		private String name;
		private boolean close;
		private boolean destroy;
		private ClickType clickType;

		public OptionClickEvent(Player player, int position, String name,
				ClickType clickType)
		{
			this.player = player;
			this.position = position;
			this.name = name;
			this.close = true;
			this.destroy = false;
			this.clickType = clickType;
		}

		public Player getPlayer() {
			return player;
		}

		public int getPosition() {
			return position;
		}

		public String getName() {
			return name;
		}

		public boolean willClose() {
			return close;
		}

		public boolean willDestroy() {
			return destroy;
		}

		public void setWillClose(boolean close) {
			this.close = close;
		}

		public void setWillDestroy(boolean destroy) {
			this.destroy = destroy;
		}

		public ClickType getClickType() {
			return clickType;
		}

		public void setClickType(ClickType clickType) {
			this.clickType = clickType;
		}
	}

	private ItemStack setItemNameAndLore(ItemStack item, String name, String[] lore) {
		if (item != null)
		{
			ItemMeta im = item.getItemMeta();
			if (im != null)
			{
				if (name != null)
					im.setDisplayName(name);
				if (lore != null)
					im.setLore(Arrays.asList(lore));

				item.setItemMeta(im);
			}
		}
		return item;
	}
}
