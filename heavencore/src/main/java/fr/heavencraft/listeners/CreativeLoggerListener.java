package fr.heavencraft.listeners;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCreativeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import fr.heavencraft.utils.DevUtil;

public class CreativeLoggerListener implements Listener
{
	public CreativeLoggerListener()
	{
		DevUtil.registerListener(this);
	}

	@EventHandler(ignoreCancelled = true)
	private void onInventoryCreative(InventoryCreativeEvent event)
	{
		HumanEntity player = event.getWhoClicked();
		CreativeInventory current = new CreativeInventory(player.getInventory());

		Bukkit.getScheduler().runTaskLaterAsynchronously(DevUtil.getPlugin(),
				new CheckInventoryTask(player.getUniqueId(), current), 1);

		// // Player take something in his survival inventory
		// if (event.getCursor().getType() == Material.AIR)
		// {
		// previousCursor.put(event.getWhoClicked().getName(),
		// event.getCurrentItem().getType());
		// }
		//
		// // Player drop something
		// else
		// {
		// Material previous =
		// previousCursor.remove(event.getWhoClicked().getName());
		//
		// // Item is taken from creative mode
		// if (previous == null)
		// {
		// DevUtil.logInfo("From creative mode : %1$s %2$s",
		// event.getCursor().getAmount(), event.getCursor().getType());
		// }
		// }
		//
		// if (event.getCurrentItem() == null)
		// {
		// DevUtil.logInfo("Click : %1$s, SlotType : %2$s, Action : %3$s, Cursor : %4$s %5$s",
		// event.getClick(),
		// event.getSlotType(), event.getAction(),
		// event.getCursor().getAmount(), event.getCursor().getType());
		// }
		// else
		// {
		// DevUtil.logInfo("Click : %1$s, SlotType : %2$s, Action : %3$s, Cursor : %4$s %5$s, CurrentItem : %6$s %7$s",
		// event
		// .getClick(), event.getSlotType(), event.getAction(),
		// event.getCursor().getAmount(), event.getCursor()
		// .getType(), event.getCurrentItem().getAmount(),
		// event.getCurrentItem().getType());
		// }
	}

	private class CheckInventoryTask implements Runnable
	{
		private UUID player;
		private CreativeInventory previous;

		public CheckInventoryTask(UUID player, CreativeInventory previous)
		{
			this.player = player;
			this.previous = previous;
		}

		@Override
		public void run()
		{
			CreativeInventory current = new CreativeInventory(Bukkit.getPlayer(player).getInventory());

			for (Material type : Material.values())
			{
				int previousAmount = previous.getAmount(type);
				int currentAmount = current.getAmount(type);

				if (previousAmount < currentAmount)
					DevUtil.logInfo("From creative mode : %1$s %2$s", type, currentAmount - previousAmount);
			}
		}
	}

	private class CreativeInventory
	{
		private final Map<Material, Integer> items = new HashMap<Material, Integer>();

		public CreativeInventory(PlayerInventory inventory)
		{
			for (ItemStack item : inventory.getContents())
				if (item != null)
					add(item.getType(), item.getAmount());
		}

		private void add(Material type, int amount)
		{
			if (items.containsKey(type))
				items.put(type, items.get(type) + amount);
			else
				items.put(type, amount);
		}

		public int getAmount(Material type)
		{
			return items.containsKey(type) ? items.get(type) : 0;
		}
	}
}