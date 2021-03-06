package fr.heavencraft.NavalConflicts.Handlers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.configuration.Configuration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;

import fr.heavencraft.NavalConflicts.Utils;

public class ItemHandler {

	@SuppressWarnings("deprecation")
	public static ItemStack getItemStack(String path) {
		ItemStack stack = new ItemStack(Material.AIR, 1);
		if (path != null)
		{
			if (path.contains(" "))
			{
				String[] line = path.split(" ");

				for (String data : line)
				{

					if (data.startsWith("id") || data.startsWith("item"))
					{
						Material mat = null;
						String item = data.split(":")[1];
						try
						{
							mat = Material.getMaterial(Integer.valueOf(item));
						} catch (NumberFormatException e)
						{
							if (Material.getMaterial(item) != null)
								mat = Material.getMaterial(item);
							else if (Material.getMaterial(ItemNames.getMaterialName(item)) != null)
								mat = Material.getMaterial(item);
							else
								mat = Material.AIR;
						}
						stack.setType(mat);
					} else if (data.startsWith("item"))
						stack.setType(Material.getMaterial(data.split(":")[1]));

					else if (data.startsWith("amount") || data.startsWith("quantity"))
						stack.setAmount(Integer.parseInt(data.split(":")[1]));

					else if (data.startsWith("data") || data.startsWith("durability") || data.startsWith("damage"))
						stack.setDurability(Short.parseShort(data.split(":")[1]));

					else if (data.startsWith("enchantment") || data.startsWith("enchant"))
					{
						String s = data.split(":")[1];
						if (s.contains("-"))
							stack.addUnsafeEnchantment(Enchantment.getById(Integer.parseInt(s.split("-")[0])), Integer.parseInt(s.split("-")[1]));
						else
							stack.addUnsafeEnchantment(Enchantment.getById(Integer.parseInt(s)), 1);

					} else if (data.startsWith("name") || data.startsWith("title"))
					{
						ItemMeta im = stack.getItemMeta();
						im.setDisplayName(Utils.format(data.split(":")[1]));
						stack.setItemMeta(im);
					} else if (data.startsWith("owner") || data.startsWith("player"))
					{
						SkullMeta im = (SkullMeta) stack.getItemMeta();
						im.setOwner(data.split(":")[1]);
						stack.setItemMeta(im);
					} else if (data.startsWith("color") || data.startsWith("colour"))
					{
						try
						{
							LeatherArmorMeta im = (LeatherArmorMeta) stack.getItemMeta();
							String[] s = data.replaceAll("color:", "").replaceAll("colour", "").split(",");
							int red = Integer.parseInt(s[0]);
							int green = Integer.parseInt(s[1]);
							int blue = Integer.parseInt(s[2]);
							im.setColor(Color.fromRGB(red, green, blue));
							stack.setItemMeta(im);
						} catch (ClassCastException notLeather)
						{
						}
					} else if (data.startsWith("lore") || data.startsWith("desc"))
					{
						String s = data.split(":")[1];
						List<String> lores = new ArrayList<String>();
						for (String lore : s.split("\\|"))
						{
							lores.add(Utils.format(lore.replace('_', ' ')));
						}
						ItemMeta meta = stack.getItemMeta();
						meta.setLore(lores);
						stack.setItemMeta(meta);
					}
				}
			} else
			{
				if (path.startsWith("id"))
				{
					stack.setType(Material.getMaterial(Integer.parseInt(path.split(":")[1])));
				} else
				{
					stack = getOldItemStack(path);
				}
			}
		}
		return stack;
	}

	@Deprecated
	private static Integer getItemID(String Path) {
		String itemid = null;
		String string = Path;
		if (string.contains(":"))
		{
			String[] ss = string.split(":");
			itemid = ss[0];
		} else if (string.contains(","))
		{
			String[] ss = string.split(",");
			itemid = ss[0];
		} else if (string.contains("-"))
		{
			String[] ss = string.split("-");
			itemid = ss[0];
		} else if (string.contains("@"))
		{
			String[] ss = string.split("@");
			itemid = ss[0];
		} else if (string.contains("%"))
		{
			String[] ss = string.split("%");
			itemid = ss[0];
		} else
			itemid = string;
		int i = 0;
		try
		{
			i = Integer.valueOf(itemid);
		} catch (NumberFormatException nfe)
		{
			i = 0;
		}
		return i;
	}

	@Deprecated
	private static Short getItemData(String Path) {
		String itemdata = null;
		String string = Path;
		if (string.contains(":"))
		{
			String[] s = string.split(":");
			if (s[1].contains(","))
			{
				String[] ss = s[1].split(",");
				itemdata = ss[0];
			} else if (s[1].contains("-"))
			{
				String[] ss = s[1].split("-");
				itemdata = ss[0];
			} else if (s[1].contains("@"))
			{
				String[] ss = s[1].split("@");
				itemdata = ss[0];
			} else if (s[1].contains("%"))
			{
				String[] ss = s[1].split("%");
				itemdata = ss[0];
			} else
				itemdata = s[1];

		} else
			itemdata = "0";
		Short s = 0;
		try
		{
			s = Short.valueOf(itemdata);
		} catch (NumberFormatException nfe)
		{
			s = 0;
		}
		return s;
	}

	@Deprecated
	private static Integer getItemAmount(String Path) {
		String itemdata = null;
		String string = Path;
		if (string.contains(","))
		{
			String[] s = string.split(",");
			if (s[1].contains("-"))
			{
				String[] ss = s[1].split("-");
				itemdata = ss[0];
			} else if (s[1].contains("@"))
			{
				String[] ss = s[1].split("@");
				itemdata = ss[0];
			} else if (s[1].contains("%"))
			{
				String[] ss = s[1].split("%");
				itemdata = ss[0];
			} else
				itemdata = s[1];
		} else
			itemdata = "1";
		int i = 1;
		try
		{
			i = Integer.valueOf(itemdata);
		} catch (NumberFormatException nfe)
		{
			i = 1;
		}
		return i;
	}

	@Deprecated
	private static int getItemEnchant(String Path) {
		String itemdata = null;
		String string = Path;
		if (string.contains("-"))
		{
			String[] s = string.split("-");
			if (s[1].contains("@"))
			{
				String[] ss = s[1].split("@");
				itemdata = ss[0];
			} else if (s[1].contains("%"))
			{
				String[] ss = s[1].split("%");
				itemdata = ss[0];
			} else
			{
				itemdata = s[1];
			}
		} else
			itemdata = "0";
		int i = 1;
		try
		{
			i = Integer.valueOf(itemdata);
		} catch (NumberFormatException nfe)
		{
			i = 1;
		}
		return i;
	}

	@Deprecated
	private static int getItemEnchantLvl(String Path) {
		String itemdata = null;
		String string = Path;
		if (string.contains("@"))
		{
			String[] s = string.split("@");
			if (s[1].contains("-"))
			{
				String[] ss = s[1].split("-");
				itemdata = ss[0];
			} else if (s[1].contains("%"))
			{
				String[] ss = s[1].split("%");
				itemdata = ss[0];
			} else
			{
				itemdata = s[1];
			}
		} else
			itemdata = "1";
		int i = 1;
		try
		{
			i = Integer.valueOf(itemdata);
		} catch (NumberFormatException nfe)
		{
			i = 1;
		}
		return i;
	}

	@Deprecated
	private static String getItemName(String Path) {
		String itemName = null;
		if (Path.contains("%"))
		{
			String[] ss = Path.split("%");
			itemName = ChatColor.translateAlternateColorCodes('&', ss[1]);
		} else
		{
			itemName = null;
		}
		return itemName;
	}

	@Deprecated
	private static ItemStack getOldItemStack(String location) {

		if (location == null)
			return null;

		ItemStack is = null;
		if (Material.getMaterial(getItemID(location)) != null)
			is = new ItemStack(Material.getMaterial(getItemID(location)),
					getItemAmount(String.valueOf(location)));
		else
			is = new ItemStack(Material.AIR);

		is.setDurability(getItemData(location));

		if (!(getItemName(location) == null))
		{
			ItemMeta im = is.getItemMeta();
			String name = getItemName(location).replaceAll("_", " ");
			im.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
			is.setItemMeta(im);
		}
		if (is.getType() != Material.AIR)
			if (location.contains("-"))
			{
				int i;
				String enchants[] = location.split("-");
				for (i = 1; i != enchants.length; i++)
				{
					if (enchants[i] != null)
					{
						enchants[i] = "-" + enchants[i];

						is.addUnsafeEnchantment(Enchantment.getById(getItemEnchant(enchants[i])), getItemEnchantLvl(enchants[i]));
					}
				}
			}
		return is;
	}

	@SuppressWarnings("deprecation")
	public static String getItemStackToString(ItemStack i) {
		String itemCode = "id:0";

		if (i.getTypeId() != 0 && i != null)
		{

			itemCode = "id:" + String.valueOf(i.getTypeId());

			if (i.getDurability() != 0)
				itemCode = itemCode + " data:" + i.getDurability();
			if (i.getAmount() > 1)
				itemCode = itemCode + " amount:" + i.getAmount();
			for (Entry<Enchantment, Integer> ench : i.getEnchantments().entrySet())
			{
				itemCode = itemCode + " enchantment:" + ench.getKey().getId();
				if (ench.getValue() > 1)
					itemCode = itemCode + "-" + ench.getValue();
			}
			if (i.getItemMeta().getDisplayName() != null)
				itemCode = itemCode + " name:" + i.getItemMeta().getDisplayName().replaceAll(" ", "_").replaceAll("�", "&");

			if (i.getItemMeta().hasLore())
			{
				itemCode = itemCode + " lore:";
				for (String string : i.getItemMeta().getLore())
					itemCode = itemCode + string.replaceAll(" ", "_").replaceAll("�", "&") + "|";
				itemCode.substring(0, itemCode.length() - 2);
			}
			if (i.getType().toString().toLowerCase().contains("leather") && ((LeatherArmorMeta) i.getItemMeta()).getColor() != null)
			{
				LeatherArmorMeta im = (LeatherArmorMeta) i.getItemMeta();
				itemCode = itemCode + " color:" + im.getColor().getRed() + "," + im.getColor().getGreen() + "," + im.getColor().getBlue();
			}
		}
		return itemCode;
	}

	// Loop through a list of these Item Codes and make a ItemStack[]
	public static ArrayList<ItemStack> getItemStackList(List<String> list) {
		ArrayList<ItemStack> items = new ArrayList<ItemStack>();
		if (!list.isEmpty())
			for (String string : list)
				items.add(getItemStack(string));

		return items;
	}

	public static HashMap<Integer, ItemStack> getItemHashMap(Configuration config, String path) {
		HashMap<Integer, ItemStack> killstreaks = new HashMap<Integer, ItemStack>();
		if (config.getConfigurationSection(path) != null)
			for (String string : config.getConfigurationSection(path).getKeys(true))
				if (!string.contains("."))
					killstreaks.put(Integer.valueOf(string), getItemStack(config.getString(path + "." + string)));

		return killstreaks;
	}

	public static ItemStack getItemStackIgnoreDamage(ItemStack stack) {
		if (stack == null)
			stack = new ItemStack(Material.AIR);
		stack.setDurability((short) 0);
		return stack;
	}

	public static ItemStack getFancyMessageItem(String title, String... strings) {
		ItemStack item = new ItemStack(Material.STONE);
		ItemMeta im = item.getItemMeta();
		List<String> lore = Arrays.asList(strings);
		im.setDisplayName(title);
		im.setLore(lore);
		item.setItemMeta(im);
		return item;
	}
}
