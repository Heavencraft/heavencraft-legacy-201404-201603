package fr.lorgan17.maya.listeners;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.bukkit.Color;
import org.bukkit.DyeColor;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Builder;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.meta.FireworkMeta;

import fr.lorgan17.maya.MayaListener;

public class BeautifulDeathListener extends MayaListener
{
	private static final Random rnd = new Random();

	private static final FireworkEffect.Type ANIMALS_FW = Type.BALL;
	private static final FireworkEffect.Type MONSTER_FW = Type.BALL_LARGE;
	private static final FireworkEffect.Type PLAYER_FW = Type.STAR;
	private static final FireworkEffect.Type CREEPER_FW = Type.CREEPER;

	private final Map<EntityType, Builder> builders = new HashMap<EntityType, Builder>();

	public BeautifulDeathListener()
	{
		super();

		// Animals
		addBuilder(EntityType.CHICKEN, ANIMALS_FW, getRandomColor(), getRandomColor());
		addBuilder(EntityType.COW, ANIMALS_FW, getRandomColor(), getRandomColor());
		addBuilder(EntityType.HORSE, ANIMALS_FW, getRandomColor(), getRandomColor());
		addBuilder(EntityType.MUSHROOM_COW, ANIMALS_FW, getRandomColor(), getRandomColor());
		addBuilder(EntityType.OCELOT, ANIMALS_FW, getRandomColor(), getRandomColor());
		addBuilder(EntityType.PIG, ANIMALS_FW, getRandomColor(), getRandomColor());
		addBuilder(EntityType.RABBIT, ANIMALS_FW, getRandomColor(), getRandomColor());
		addBuilder(EntityType.SHEEP, ANIMALS_FW, getRandomColor(), getRandomColor());
		addBuilder(EntityType.WOLF, ANIMALS_FW, getRandomColor(), getRandomColor());

		// Monster
		addBuilder(EntityType.BLAZE, MONSTER_FW, getRandomColor(), getRandomColor());
		addBuilder(EntityType.CAVE_SPIDER, MONSTER_FW, getRandomColor(), getRandomColor());
		addBuilder(EntityType.ENDERMAN, MONSTER_FW, getRandomColor(), getRandomColor());
		addBuilder(EntityType.ENDERMITE, MONSTER_FW, getRandomColor(), getRandomColor());
		addBuilder(EntityType.GIANT, MONSTER_FW, getRandomColor(), getRandomColor());
		addBuilder(EntityType.GUARDIAN, MONSTER_FW, getRandomColor(), getRandomColor());
		addBuilder(EntityType.PIG_ZOMBIE, MONSTER_FW, getRandomColor(), getRandomColor());
		addBuilder(EntityType.SILVERFISH, MONSTER_FW, getRandomColor(), getRandomColor());
		addBuilder(EntityType.SKELETON, MONSTER_FW, getRandomColor(), getRandomColor());
		addBuilder(EntityType.SPIDER, MONSTER_FW, getRandomColor(), getRandomColor());
		addBuilder(EntityType.WITCH, MONSTER_FW, getRandomColor(), getRandomColor());
		addBuilder(EntityType.WITHER, MONSTER_FW, getRandomColor(), getRandomColor());
		addBuilder(EntityType.ZOMBIE, MONSTER_FW, getRandomColor(), getRandomColor());

		// Player
		addBuilder(EntityType.PLAYER, PLAYER_FW, getRandomColor(), getRandomColor());

		// Creeper
		addBuilder(EntityType.CREEPER, CREEPER_FW, getRandomColor(), getRandomColor());
	}

	private void addBuilder(EntityType entityType, Type type, Color color1, Color color2)
	{
		Builder builder = FireworkEffect.builder().withFlicker().withColor(color1).withFade(color2).with(type).withTrail();
		builders.put(entityType, builder);
	}

	private static Color getRandomColor()
	{
		DyeColor color = DyeColor.values()[rnd.nextInt(DyeColor.values().length)];

		switch (color)
		{
			case BLACK:
			case GRAY:
			case WHITE:
				return getRandomColor();

			default:
				return color.getColor();
		}
	}

	@EventHandler
	private void onEntityDeath(EntityDeathEvent event)
	{
		Firework firework = (Firework) event.getEntity().getWorld()
				.spawnEntity(event.getEntity().getLocation(), EntityType.FIREWORK);

		Builder builder = builders.get(event.getEntityType());

		if (builder != null)
		{
			FireworkMeta meta = firework.getFireworkMeta();

			meta.addEffects(builder.build());
			meta.setPower(1);

			firework.setFireworkMeta(meta);
		}
	}
}