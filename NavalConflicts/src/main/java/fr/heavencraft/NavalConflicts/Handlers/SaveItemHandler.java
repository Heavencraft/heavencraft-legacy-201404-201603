package fr.heavencraft.NavalConflicts.Handlers;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import fr.heavencraft.NavalConflicts.Tools.Files;

public class SaveItemHandler {
	public static void saveItem(Player p, ItemStack is) {
		List<String> list = getSavedItems(p);
		list.add(ItemHandler.getItemStackToString(is));
		Files.getPlayers().set("Players." + p.getName().toLowerCase() + ".Saved Inventory", list);
		Files.savePlayers();
	}

	@SuppressWarnings("unchecked")
	public static List<String> getSavedItems(Player p) {
		List<String> list = new ArrayList<String>();
		if (Files.getPlayers().get("Players." + p.getName().toLowerCase() + ".Saved Inventory") != null)
			try
			{
				list = (ArrayList<String>) Files.getPlayers().get("Players." + p.getName().toLowerCase() + ".Saved Inventory");
			} catch (Exception e)
			{
				p.sendMessage("Tell an Admin that your saved inventory is invalid!");
			}
		return list;
	}
}
