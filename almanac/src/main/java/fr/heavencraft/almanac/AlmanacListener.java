package fr.heavencraft.almanac;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

public class AlmanacListener implements Listener
{
	public AlmanacListener()
	{
		Bukkit.getPluginManager().registerEvents(this, AlmanacPlugin.getInstance());
	}

	@EventHandler
	public void onItemCrafted(CraftItemEvent event)
	{
		ItemStack item = event.getCurrentItem();

		if (item.getType() != Material.WRITTEN_BOOK)
			return;

		BookMeta book = (BookMeta) item.getItemMeta();

		if (!book.getAuthor().equals(AlmanacManager.HEAVENCRAFT))
			return;

		book.setAuthor(event.getWhoClicked().getName());
		book.setTitle(ChatColor.RED + "COPIE PIRATE");

		item.setItemMeta(book);
	}

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event)
	{
		if (event.getAction() != Action.RIGHT_CLICK_BLOCK)
			return;

		Block block = event.getClickedBlock();

		if (block.getType() == Material.BOOKSHELF && AlmanacManager.isLocationCorrect(block.getLocation()))
		{
			onBookshelfClick(event.getPlayer(), block);
			event.setCancelled(true);
		}
	}

	private void onBookshelfClick(Player player, Block block)
	{
		if (!AlmanacManager.canGetAlmanac(player.getName()))
		{
			player.sendMessage(ChatColor.GOLD + "Vous avez déjà pris l'Almanach d'aujourd'hui.");
			return;
		}

		Location location = block.getLocation();

		location.getWorld().playEffect(location, Effect.POTION_BREAK, 1);

		AlmanacManager.giveAlmanac(player);

		Bukkit.getScheduler().scheduleSyncDelayedTask(AlmanacPlugin.getInstance(),
				new RecreateBookshelf(location), 30);
		block.setType(Material.AIR);
	}

	class RecreateBookshelf implements Runnable
	{
		private final Location _location;

		public RecreateBookshelf(Location location)
		{
			_location = location;
		}

		@Override
		public void run()
		{
			_location.getBlock().setType(Material.BOOKSHELF);
		}
	}
}