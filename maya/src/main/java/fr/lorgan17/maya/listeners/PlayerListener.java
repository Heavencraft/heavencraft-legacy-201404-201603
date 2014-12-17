package fr.lorgan17.maya.listeners;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import fr.lorgan17.maya.MayaPlugin;
import fr.lorgan17.maya.managers.ProtectionManager;

public class PlayerListener implements Listener
{
	Random random = new Random();
	
	public PlayerListener(MayaPlugin plugin)
	{
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event)
	{
		event.setJoinMessage("");
		
		Player player = event.getPlayer();
		
		MayaPlugin.sendMessage(player,"{Bienvenue à l'event fin du monde !}");
		MayaPlugin.sendMessage(player,"Commandes disponibles :");
		MayaPlugin.sendMessage(player, "{/rejoindre} <joueur> : se téléporter à un joueur.");
		MayaPlugin.sendMessage(player, "{/tppos} <x> <y> <z> : se tp à une position (go dynmap)");
		MayaPlugin.sendMessage(player, "{/roucoups} : ow you, pokémons..");
		MayaPlugin.sendMessage(player, "{/spawn} : revenir au spawn.");
		MayaPlugin.sendMessage(player, "{/spawnmob} <mob> <nombre> : faire spawner des mobs.");

		setInventory(player);
		player.teleport(MayaPlugin.spawn);
		
		MayaPlugin.sendMessage(player, "Vous venez de percevoir {votre paquetage réglementaire}. Bonne fin du monde !");
	}
	
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event)
	{
		event.setQuitMessage("");
	}
	
	@EventHandler(ignoreCancelled = true)
	public void onPlayerKick(PlayerKickEvent event)
	{
		event.setLeaveMessage("");
	}
	
	@EventHandler
	public void onPlayerRespawn(PlayerRespawnEvent event)
	{
		setInventory(event.getPlayer());
		event.setRespawnLocation(MayaPlugin.spawn);
	}

	@EventHandler
	public void onEntityDamage(EntityDamageEvent event)
	{
		if (ProtectionManager.isProtected(event.getEntity().getLocation().getBlock()))
			event.setCancelled(true);
	}

	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent event)
	{
		event.setDeathMessage("");
	}
	
	private static void setInventory(Player player)
	{
		PlayerInventory inventory = player.getInventory();
		inventory.clear();
		
		inventory.addItem(enchant(new ItemStack(Material.DIAMOND_SWORD)));
		inventory.addItem(new ItemStack(Material.BOW));
		
		inventory.addItem(new ItemStack(Material.DIAMOND_PICKAXE));
		inventory.addItem(new ItemStack(Material.DIAMOND_AXE));
		inventory.addItem(new ItemStack(Material.DIAMOND_SPADE));

		inventory.addItem(new ItemStack(Material.GRILLED_PORK, 64));
		
		inventory.addItem(new ItemStack(Material.LAVA_BUCKET));
		
		inventory.addItem(new ItemStack(Material.FLINT_AND_STEEL));

		for (int i = 0; i != 9; i++)
			inventory.addItem(new ItemStack(Material.TNT, 64));
		
		inventory.addItem(new ItemStack(Material.FLINT_AND_STEEL));
		
		inventory.addItem(new ItemStack(Material.ARROW, 64));
		inventory.addItem(new ItemStack(Material.ARROW, 64));
		
		inventory.addItem(new ItemStack(Material.DIRT, 64));
		inventory.addItem(new ItemStack(Material.DIRT, 64));
		inventory.addItem(new ItemStack(Material.COBBLESTONE, 64));
		inventory.addItem(new ItemStack(Material.COBBLESTONE, 64));

		inventory.addItem(new ItemStack(Material.LAVA_BUCKET));
		inventory.addItem(new ItemStack(Material.WATER_BUCKET));
		inventory.addItem(new ItemStack(Material.WATER_BUCKET));
		
		inventory.setHelmet(enchant(new ItemStack(Material.DIAMOND_HELMET)));
		inventory.setChestplate(enchant(new ItemStack(Material.DIAMOND_CHESTPLATE)));
		inventory.setLeggings(enchant(new ItemStack(Material.DIAMOND_LEGGINGS)));
		inventory.setBoots(enchant(new ItemStack(Material.DIAMOND_BOOTS)));
		
	}
	
	private static ItemStack enchant(ItemStack item)
	{
		switch (item.getType())
		{
			case DIAMOND_BOOTS:
				item.addEnchantment(Enchantment.PROTECTION_FALL, 4);
			case DIAMOND_HELMET:
			case DIAMOND_CHESTPLATE:
			case DIAMOND_LEGGINGS:
				item.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4);
				item.addEnchantment(Enchantment.PROTECTION_EXPLOSIONS, 4);
				item.addEnchantment(Enchantment.PROTECTION_FIRE, 4);
				item.addEnchantment(Enchantment.PROTECTION_PROJECTILE, 4);
				break;
			case DIAMOND_SWORD:
				item.addEnchantment(Enchantment.KNOCKBACK, 2);
				item.addEnchantment(Enchantment.FIRE_ASPECT, 2);
				item.addEnchantment(Enchantment.DAMAGE_ALL, 5);
				break;
		}
		
		return item;
	}
}