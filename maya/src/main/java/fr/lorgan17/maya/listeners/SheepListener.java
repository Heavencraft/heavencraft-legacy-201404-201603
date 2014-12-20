package fr.lorgan17.maya.listeners;

import java.util.Random;

import org.bukkit.DyeColor;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Sheep;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDeathEvent;

import fr.lorgan17.maya.MayaListener;

/**
 * SheepListener Change the behavior of sheeps.
 * 
 * @author lorgan17
 */
public class SheepListener extends MayaListener
{
	private static final Random rnd = new Random();

	private static DyeColor getRandomColor()
	{
		return DyeColor.values()[rnd.nextInt(DyeColor.values().length)];
	}

	@EventHandler
	private void onEntityDeath(EntityDeathEvent event)
	{
		if (event.getEntityType() == EntityType.SHEEP)
		{
			Sheep sheep = (Sheep) event.getEntity();

			if (sheep.isAdult())
			{
				log.info("Adult sheep death");

				// Create an explosion (4 = TNT force)
				sheep.getWorld().createExplosion(sheep.getLocation(), 4);

				// Spawn 4 babies
				for (int i = 0; i != 4; i++)
				{
					Sheep baby = (Sheep) sheep.getWorld().spawnEntity(sheep.getLocation(), EntityType.SHEEP);
					baby.setBaby();
					baby.setColor(getRandomColor());
				}
			}
			else
			{
				log.info("Baby sheep death");

				// Create a small explosion
				sheep.getWorld().createExplosion(sheep.getLocation(), 1);
			}
		}
	}
}