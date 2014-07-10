package fr.heavencraft.rpg.Parchemins.Parchemins;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import fr.heavencraft.Utils.ParticleEffect;
import fr.heavencraft.rpg.HeavenRPG;
import fr.heavencraft.rpg.Parchemins.Parchemin;
import fr.heavencraft.rpg.player.RPGPlayer;

public class ParcheminPousseeQuantique implements Parchemin{

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
		met.setDisplayName(ChatColor.GREEN + "Pousée Quantique");
		parchemin.setItemMeta(met);
		return parchemin;
	}

	public void executeParchemin(final RPGPlayer player) {
		
		player.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 255, 255));
		player.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 255, 255));
		player.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 255, 255));
		
		new BukkitRunnable() {
			@Override
			public void run() {

				
				
				ParticleEffect.CLOUD.display(player.getPlayer().getLocation().add(0, 1, 0), 10, 0, 0, 0, 10, 1000);
				
				
				List<Entity> entities = player.getPlayer().getNearbyEntities(10, 10, 10);
				for(Entity e : entities)
				{
					Vector unitVector = e.getLocation().toVector().subtract(player.getPlayer().getLocation().toVector()).normalize();
					// Set speed and push entity:
					e.setVelocity(unitVector.multiply(4.0));
				}
				
				
				player.getPlayer().removePotionEffect(PotionEffectType.SLOW);
				player.getPlayer().removePotionEffect(PotionEffectType.SLOW_DIGGING);
				player.getPlayer().removePotionEffect(PotionEffectType.BLINDNESS);
			}	
		}.runTaskLater(HeavenRPG.getInstance(), 20);	
	}

}
