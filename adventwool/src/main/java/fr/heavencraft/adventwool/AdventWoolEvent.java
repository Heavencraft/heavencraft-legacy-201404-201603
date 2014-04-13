package fr.heavencraft.adventwool;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;


public class AdventWoolEvent
{
	public class RandomItem
	{
		private Material _itemMaterial;
		private short _itemDamage;
		private int _maximalQuantity;
		private Enchantment[] _enchants;

		public RandomItem(Material material, short damage, int quantity)
		{
			_itemMaterial = material;
			_itemDamage = damage;
			_maximalQuantity = quantity;
			_enchants = null;
		}
		
		public RandomItem(Material material, Enchantment[] enchants)
		{
			_itemMaterial = material;
			_itemDamage = -1;
			_maximalQuantity = 0;
			_enchants = enchants;
		}
		
		public RandomItem(Material material, int quantity)
		{
			_itemMaterial = material;
			_itemDamage = -1;
			_maximalQuantity = quantity;
			_enchants = null;
		}
		
		public ItemStack create()
		{
			int quantity = (_maximalQuantity == 0 ? 1 : _plugin.randNext(1, _maximalQuantity));
			ItemStack stack;
			if (_itemDamage == -1)
				stack = new ItemStack(_itemMaterial, quantity);
			else
				stack = new ItemStack(_itemMaterial, quantity, _itemDamage);
			if (_enchants != null)
			{
				int max = _plugin.randNext(0, _enchants.length * 2);
				while (max > 0)
				{
					Enchantment ench = _enchants[_plugin.randNext(0, _enchants.length)];
					if (!stack.containsEnchantment(ench))
						stack.addEnchantment(ench, _plugin.randNext(ench.getStartLevel(), ench.getMaxLevel() + 1));
					max--;
				}
			}
			return stack;
		}
	}
	
	public class RecreateWool implements Runnable
	{
		private Block _block;
		
		public RecreateWool(Block block)
		{
			_block = block;
		}
		
		@Override
		public void run()
		{
			_block.setTypeIdAndData(Material.WOOL.getId(), (byte) _plugin.randNext(0, 15), false);
		}
	}
	
	private AdventWoolPlugin _plugin;
	private List<RandomItem> _rndItems;
	
	public AdventWoolEvent(AdventWoolPlugin plugin)
	{
		_plugin = plugin;
		_rndItems = new ArrayList<RandomItem>();
		createRndItems();
	}

	private void addRndItem(Material material, Enchantment[] enchants)
	{
		_rndItems.add(new RandomItem(material, enchants));
	}
	
	private void addRndItem(Material material, int damage, int quantity)
	{
		_rndItems.add(new RandomItem(material, (short) damage, quantity));
	}
	
	private void addRndItem(Material material, int quantity)
	{
		_rndItems.add(new RandomItem(material, quantity));
	}
	
	private void createRndItems()
	{
		addRndItem(Material.STONE, 64);
		addRndItem(Material.GRASS, 32);
		addRndItem(Material.DIRT, 64);
		addRndItem(Material.COBBLESTONE, 64);
		
		addRndItem(Material.WOOD, 0, 64);
		addRndItem(Material.WOOD, 1, 16);
		addRndItem(Material.WOOD, 2, 16);
		addRndItem(Material.WOOD, 3, 32);

		addRndItem(Material.WOOD, 4, 32);
		addRndItem(Material.WOOD, 4, 32);
		addRndItem(Material.WOOD, 5, 32);
		addRndItem(Material.WOOD, 5, 32);

		addRndItem(Material.SAPLING, 0, 64);
		addRndItem(Material.SAPLING, 1, 32);
		addRndItem(Material.SAPLING, 2, 32);
		addRndItem(Material.SAPLING, 3, 16);

		addRndItem(Material.SAND, 1, 12);
		addRndItem(Material.GRAVEL, 1, 14);
		
		addRndItem(Material.LOG, 0, 64);
		addRndItem(Material.LOG, 1, 32);
		addRndItem(Material.LOG, 2, 32);
		addRndItem(Material.LOG, 3, 16);

		addRndItem(Material.GLASS, 32);

		addRndItem(Material.LAPIS_BLOCK, 12);
		
		addRndItem(Material.IRON_INGOT, 12);
		addRndItem(Material.GOLD_INGOT, 6);
		addRndItem(Material.DIAMOND, 3);
		
		addRndItem(Material.SMOOTH_BRICK, 0, 32);
		addRndItem(Material.SMOOTH_BRICK, 1, 16);
		addRndItem(Material.SMOOTH_BRICK, 2, 16);
		addRndItem(Material.SMOOTH_BRICK, 3, 8);

		addRndItem(Material.STICK, 32);
		addRndItem(Material.BLAZE_ROD, 8);
		
		addRndItem(Material.REDSTONE, 32);
		
		addRndItem(Material.SEEDS, 45);
		addRndItem(Material.PUMPKIN_SEEDS, 45);
		addRndItem(Material.MELON_SEEDS, 25);

		addRndItem(Material.WOOD_PICKAXE, new Enchantment[] { Enchantment.SILK_TOUCH, Enchantment.DIG_SPEED, Enchantment.DURABILITY, Enchantment.LOOT_BONUS_BLOCKS });
		addRndItem(Material.STONE_PICKAXE, new Enchantment[] { Enchantment.SILK_TOUCH, Enchantment.DIG_SPEED, Enchantment.DURABILITY, Enchantment.LOOT_BONUS_BLOCKS });
		addRndItem(Material.IRON_PICKAXE, 0 /*new Enchantment[] { Enchantment.SILK_TOUCH, Enchantment.DIG_SPEED, Enchantment.DURABILITY, Enchantment.LOOT_BONUS_BLOCKS }*/);
		addRndItem(Material.GOLD_PICKAXE, new Enchantment[] { Enchantment.SILK_TOUCH, Enchantment.DIG_SPEED, Enchantment.DURABILITY, Enchantment.LOOT_BONUS_BLOCKS });
		addRndItem(Material.DIAMOND_PICKAXE, 0 /*new Enchantment[] { Enchantment.SILK_TOUCH, Enchantment.DIG_SPEED, Enchantment.DURABILITY, Enchantment.LOOT_BONUS_BLOCKS }*/);

		addRndItem(Material.WOOD_HOE, 0);
		addRndItem(Material.STONE_HOE, 0);
		addRndItem(Material.IRON_HOE, 0);
		addRndItem(Material.GOLD_HOE, 0);
		addRndItem(Material.DIAMOND_HOE, 0);

		addRndItem(Material.WOOD_SWORD, new Enchantment[] { Enchantment.DAMAGE_ALL, Enchantment.DAMAGE_ARTHROPODS, Enchantment.DAMAGE_UNDEAD, Enchantment.KNOCKBACK, Enchantment.FIRE_ASPECT, Enchantment.LOOT_BONUS_MOBS });
		addRndItem(Material.STONE_SWORD, new Enchantment[] { Enchantment.DAMAGE_ALL, Enchantment.DAMAGE_ARTHROPODS, Enchantment.DAMAGE_UNDEAD, Enchantment.KNOCKBACK, Enchantment.FIRE_ASPECT, Enchantment.LOOT_BONUS_MOBS });
		addRndItem(Material.IRON_SWORD, 0 /*new Enchantment[] { Enchantment.DAMAGE_ALL, Enchantment.DAMAGE_ARTHROPODS, Enchantment.DAMAGE_UNDEAD, Enchantment.KNOCKBACK, Enchantment.FIRE_ASPECT, Enchantment.LOOT_BONUS_MOBS }*/);
		addRndItem(Material.GOLD_SWORD, new Enchantment[] { Enchantment.DAMAGE_ALL, Enchantment.DAMAGE_ARTHROPODS, Enchantment.DAMAGE_UNDEAD, Enchantment.KNOCKBACK, Enchantment.FIRE_ASPECT, Enchantment.LOOT_BONUS_MOBS });
		addRndItem(Material.DIAMOND_SWORD, 0 /*new Enchantment[] { Enchantment.DAMAGE_ALL, Enchantment.DAMAGE_ARTHROPODS, Enchantment.DAMAGE_UNDEAD, Enchantment.KNOCKBACK, Enchantment.FIRE_ASPECT, Enchantment.LOOT_BONUS_MOBS }*/);

		addRndItem(Material.WOOD_SPADE, new Enchantment[] { Enchantment.SILK_TOUCH, Enchantment.DIG_SPEED, Enchantment.DURABILITY, Enchantment.LOOT_BONUS_BLOCKS });
		addRndItem(Material.STONE_SPADE, new Enchantment[] { Enchantment.SILK_TOUCH, Enchantment.DIG_SPEED, Enchantment.DURABILITY, Enchantment.LOOT_BONUS_BLOCKS });
		addRndItem(Material.IRON_SPADE, 0 /*new Enchantment[] { Enchantment.SILK_TOUCH, Enchantment.DIG_SPEED, Enchantment.DURABILITY, Enchantment.LOOT_BONUS_BLOCKS }*/);
		addRndItem(Material.GOLD_SPADE, new Enchantment[] { Enchantment.SILK_TOUCH, Enchantment.DIG_SPEED, Enchantment.DURABILITY, Enchantment.LOOT_BONUS_BLOCKS });
		addRndItem(Material.DIAMOND_SPADE, 0 /*new Enchantment[] { Enchantment.SILK_TOUCH, Enchantment.DIG_SPEED, Enchantment.DURABILITY, Enchantment.LOOT_BONUS_BLOCKS }*/);

		addRndItem(Material.WOOD_AXE, new Enchantment[] { Enchantment.SILK_TOUCH, Enchantment.DIG_SPEED, Enchantment.DURABILITY, Enchantment.LOOT_BONUS_BLOCKS });
		addRndItem(Material.STONE_AXE, new Enchantment[] { Enchantment.SILK_TOUCH, Enchantment.DIG_SPEED, Enchantment.DURABILITY, Enchantment.LOOT_BONUS_BLOCKS });
		addRndItem(Material.IRON_AXE, 0 /*new Enchantment[] { Enchantment.SILK_TOUCH, Enchantment.DIG_SPEED, Enchantment.DURABILITY, Enchantment.LOOT_BONUS_BLOCKS }*/);
		addRndItem(Material.GOLD_AXE, new Enchantment[] { Enchantment.SILK_TOUCH, Enchantment.DIG_SPEED, Enchantment.DURABILITY, Enchantment.LOOT_BONUS_BLOCKS });
		addRndItem(Material.DIAMOND_AXE, 0 /*new Enchantment[] { Enchantment.SILK_TOUCH, Enchantment.DIG_SPEED, Enchantment.DURABILITY, Enchantment.LOOT_BONUS_BLOCKS }*/);

		addRndItem(Material.GOLDEN_APPLE, 0);
		addRndItem(Material.BREAD, 16);
		addRndItem(Material.FLINT, 32);
		addRndItem(Material.COOKIE, 16);
		addRndItem(Material.FEATHER, 64);
		addRndItem(Material.SULPHUR, 32);
	}
	
	private ItemStack[] generateRandomItems()
	{
		HashSet<ItemStack> items = new HashSet<ItemStack>();
		
		int itemCount = _plugin.randNext(3, 6);
		
		while (itemCount > 0)
		{
			RandomItem rndItem = _rndItems.get(_plugin.randNext(0, _rndItems.size()));
			items.add(rndItem.create());
			itemCount--;
		}
		
		return items.toArray(new ItemStack[]{});
	}
	
	private boolean isLocationCorrect(Location location)
	{
		int x = location.getBlockX();
		int y = location.getBlockY();
		int z = location.getBlockZ();
		if (x == 214 && y == 95 && z == 103)
			return true;
		if (x == 214 && y == 96 && z == 97)
			return true;
		if (x == 215 && y == 101 && z == 97)
			return true;
		if (x == 216 && y == 100 && z == 107)
			return true;
		if (x == 220 && y == 100 && z == 116)
			return true;
		if (x == 220 && y == 106 && z == 114)
			return true;
		if (x == 216 && y == 107 && z == 104)
			return true;
		if (x == 216 && y == 107 && z == 98)
			return true;
		if (x == 220 && y == 206 && z == 86)
			return true;
		if (x == 227 && y == 111 && z == 80)
			return true;
		if (x == 219 && y == 112 && z == 91)
			return true;
		if (x == 219 && y == 112 && z == 109)
			return true;
		if (x == 226 && y == 112 && z == 119)
			return true;
		if (x == 223 && y == 116 && z == 114)
			return true;
		if (x == 220 && y == 116 && z == 109)
			return true;
		if (x == 219 && y == 112 && z == 91)
			return true;
		if (x == 221 && y == 124 && z == 106)
			return true;
		if (x == 229 && y == 123 && z == 119)
			return true;
		if (x == 237 && y == 128 && z == 123)
			return true;
		if (x == 231 && y == 130 && z == 119)
			return true;
		if (x == 226 && y == 131 && z == 113)
			return true;
		if (x == 222 && y == 137 && z == 101)
			return true;
		if (x == 224 && y == 146 && z == 97)
			return true;
		if (x == 225 && y == 148 && z == 94)
			return true;
		return false;
	}
	
	public void onWoolClick(Player player, Block block)
	{
		//User user = _plugin.getUsersManager().getUser(player.getName());
		Location location = block.getLocation();
		if (isLocationCorrect(location))
		{
			//if (!user.getBooleanVariable("adventWool")) return;
			if (!_plugin.canAdventWool(player.getName())) return;
			//user.setBooleanVariable("adventWool", false);
			//user.saveToFile();
			_plugin.setAventDate(player.getName());
			player.sendMessage(ChatColor.RED + "[No�l] " + ChatColor.GOLD + "Vous venez d'ouvrir votre laine de l'avent !");
			block.getWorld().playEffect(location, Effect.POTION_BREAK, 1);
			ItemStack[] stacks = generateRandomItems();
			for (ItemStack stack : stacks)
			{
				if (stack == null) continue;
				if (stack.getTypeId() == 0) continue;
				block.getWorld().dropItemNaturally(location, stack);
			}
			_plugin.getServer().getScheduler().scheduleSyncDelayedTask(_plugin, new RecreateWool(block), 30);
			block.setType(Material.AIR);
		}
	}
}
