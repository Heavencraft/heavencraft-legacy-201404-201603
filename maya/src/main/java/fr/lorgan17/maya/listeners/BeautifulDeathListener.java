package fr.lorgan17.maya.listeners;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

import fr.lorgan17.maya.MayaPlugin;

public class BeautifulDeathListener implements Listener
{
	FireworkEffect.Type ANIMALS_FW = Type.BALL;
	FireworkEffect.Type MONSTER_FW = Type.BALL_LARGE;
	FireworkEffect.Type PLAYER_FIREWORK = Type.STAR;

	Map<EntityType, FireworkEffect> builders = new HashMap<EntityType, FireworkEffect>();

	public BeautifulDeathListener(MayaPlugin plugin)
	{
		Bukkit.getPluginManager().registerEvents(this, plugin);

		for (EntityType entityType : EntityType.values())
		{
			builders.put(entityType, FireworkEffect.builder().with(ANIMALS_FW).withColor(Color.BLUE).build());
		}
		//
		// // Animals
		// builders.put(EntityType.CHICKEN,
		// FireworkEffect.builder().with(ANIMALS_FW).trail(true).withColor(Color.AQUA)).flicker(
		// true);
		// builders.put(EntityType.COW,
		// FireworkEffect.builder().with(ANIMALS_FW).trail(false).withColor(Color.AQUA)).flicker(true);
		// builders.put(EntityType.MUSHROOM_COW,
		// FireworkEffect.builder().with(ANIMALS_FW).trail(true).withColor(Color.BLACK))
		// .flicker(true);
		// builders.put(EntityType.HORSE,
		// FireworkEffect.builder().with(ANIMALS_FW).trail(false).withColor(Color.BLACK)).flicker(
		// true);
		// builders.put(EntityType.OCELOT,
		// FireworkEffect.builder().with(ANIMALS_FW).trail(true).withColor(Color.BLUE))
		// .flicker(true);
		// builders.put(EntityType.PIG,
		// FireworkEffect.builder().with(ANIMALS_FW).trail(false).withColor(Color.BLUE)).flicker(true);
		// builders.put(EntityType.RABBIT,
		// FireworkEffect.builder().with(ANIMALS_FW).trail(true).withColor(Color.FUCHSIA)).flicker(
		// true);
		// builders.put(EntityType.SHEEP,
		// FireworkEffect.builder().with(ANIMALS_FW).trail(false).withColor(Color.FUCHSIA)).flicker(
		// true);
		// builders.put(EntityType.WOLF,
		// FireworkEffect.builder().with(ANIMALS_FW).trail(true).withColor(Color.GRAY)).flicker(true);
		//
		// // Monster
		// builders.put(EntityType.BLAZE,
		// FireworkEffect.builder().with(MONSTER_FW).trail(false).withColor(Color.GRAY))
		// .flicker(true);
		// builders.put(EntityType.CREEPER,
		// FireworkEffect.builder().with(MONSTER_FW).trail(true).withColor(Color.GREEN)).flicker(
		// true);
		// builders.put(EntityType.ENDERMAN,
		// FireworkEffect.builder().with(MONSTER_FW).trail(false).withColor(Color.GREEN)).flicker(
		// true);
		// builders.put(EntityType.ENDERMITE,
		// FireworkEffect.builder().with(MONSTER_FW).trail(true).withColor(Color.LIME)).flicker(
		// true);
		// builders.put(EntityType.GIANT,
		// FireworkEffect.builder().with(MONSTER_FW).trail(false).withColor(Color.LIME))
		// .flicker(true);
		// builders.put(EntityType.GUARDIAN,
		// FireworkEffect.builder().with(MONSTER_FW).trail(true).withColor(Color.MAROON)).flicker(
		// true);
		// builders.put(EntityType.SILVERFISH,
		// FireworkEffect.builder().with(MONSTER_FW).trail(false).withColor(Color.MAROON))
		// .flicker(true);
		// builders.put(EntityType.SKELETON,
		// FireworkEffect.builder().with(MONSTER_FW).trail(true).withColor(Color.NAVY)).flicker(
		// true);
		// builders.put(EntityType.SPIDER,
		// FireworkEffect.builder().with(MONSTER_FW).trail(false).withColor(Color.NAVY)).flicker(
		// true);
		// builders.put(EntityType.CAVE_SPIDER,
		// FireworkEffect.builder().with(MONSTER_FW).trail(true).withColor(Color.OLIVE))
		// .flicker(true);
		// builders.put(EntityType.WITCH,
		// FireworkEffect.builder().with(MONSTER_FW).trail(false).withColor(Color.OLIVE)).flicker(
		// true);
		// builders.put(EntityType.WITHER,
		// FireworkEffect.builder().with(MONSTER_FW).trail(true).withColor(Color.ORANGE)).flicker(
		// true);
		// builders.put(EntityType.ZOMBIE,
		// FireworkEffect.builder().with(MONSTER_FW).trail(false).withColor(Color.ORANGE)).flicker(
		// true);
		// builders.put(EntityType.PIG_ZOMBIE,
		// FireworkEffect.builder().with(MONSTER_FW).trail(true).withColor(Color.PURPLE))
		// .flicker(true);
	}

	@EventHandler
	private void onEntityDeath(EntityDeathEvent event)
	{

		Firework firework = (Firework) event.getEntity().getWorld()
				.spawnEntity(event.getEntity().getLocation(), EntityType.FIREWORK);

		FireworkEffect effect = builders.get(event.getEntityType());

		if (effect != null)
		{
			firework.getFireworkMeta().addEffects(effect);
			firework.getFireworkMeta().setPower(20);
		}
	}
}
