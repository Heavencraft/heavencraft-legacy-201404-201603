package fr.heavencraft.rpg.Parchemins.Parchemins;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import fr.heavencraft.Utils.ParticleEffect;
import fr.heavencraft.rpg.HeavenRPG;
import fr.heavencraft.rpg.Parchemins.IParchemin;
import fr.heavencraft.rpg.player.RPGPlayer;

public class ParcheminLeSouffleDuNecromentien implements IParchemin{

	public int RPGexpieirence() {
		return 0;
	}

	public boolean canDo(RPGPlayer player) {
		if(player.getRPGXp() >= RPGexpieirence())
			return false;
		return true;
	}

	public ItemStack getItem() {
		ItemStack parchemin = new ItemStack(Material.PAPER);
		ItemMeta met = parchemin.getItemMeta();
		met.setDisplayName(ChatColor.BLUE + "Souffle du nécromencien");
		parchemin.setItemMeta(met);
		return parchemin;
	}

	public void executeParchemin(final RPGPlayer player) {
		
		player.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 255, 255));
		player.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 255, 255));
		
		new BukkitRunnable() {
			@Override
			public void run() {

				
				
				ParticleEffect.CLOUD.display(player.getPlayer().getLocation().add(0, 1, 0), 10, 0, 0, 0, 10, 1000);
				
				
				List<Entity> entities = player.getPlayer().getNearbyEntities(5, 5, 5);
				for(Entity e : entities)
				{
					if(e instanceof Player)
					{
						Player player = (Player) e;
						
						player.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 255 ,1000));
					}
				}
				
				
				player.getPlayer().removePotionEffect(PotionEffectType.SLOW);
				player.getPlayer().removePotionEffect(PotionEffectType.SLOW_DIGGING);
			}	
		}.runTaskLater(HeavenRPG.getInstance(), 20);	
	}

}
