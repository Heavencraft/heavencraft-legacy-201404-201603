package fr.heavencraft.NavalConflicts.Handlers.Potions;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class PotionHandler {
	@SuppressWarnings("deprecation")
	public static String getPotionToString(PotionEffect pe) {
		return (pe.getType().getId() + ":" + pe.getDuration() + ":" + pe.getAmplifier());
	}

	@SuppressWarnings("deprecation")
	public static PotionEffect getPotion(String path) {
		Integer id = 0;
		Integer time = 0;
		Integer power = 0;
		String[] strings = path.split(":");
		id = Integer.valueOf(strings[0]);
		time = Integer.valueOf(strings[1]) * 20;
		power = Integer.valueOf(strings[2]) - 1;
		return new PotionEffect(PotionEffectType.getById(id), time, power);
	}

	@SuppressWarnings("deprecation")
	public static ArrayList<PotionEffect> getPotions(List<String> list) {
		ArrayList<PotionEffect> effects = new ArrayList<PotionEffect>();
		if (!list.isEmpty())
			for (String path : list)
			{
				Integer id = 0;
				Integer time = 0;
				Integer power = 0;
				String[] strings = path.split(":");
				id = Integer.valueOf(strings[0]);
				time = Integer.valueOf(strings[1]) * 20;
				power = Integer.valueOf(strings[2]) - 1;
				effects.add(new PotionEffect(PotionEffectType.getById(id),
						time, power));
			}
		return effects;
	}
}
