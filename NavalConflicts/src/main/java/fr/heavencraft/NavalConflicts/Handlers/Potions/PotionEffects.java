package fr.heavencraft.NavalConflicts.Handlers.Potions;

import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;

import fr.heavencraft.NavalConflicts.Handlers.Player.NCPlayer;
import fr.heavencraft.NavalConflicts.Handlers.Player.NCPlayerManager;

public class PotionEffects {
	public static void applyClassEffects(Player p) {
		NCPlayer IP = NCPlayerManager.getNCPlayer(p);

		if (!IP.getNCClass(IP.getTeam()).getEffects().isEmpty())
			for (PotionEffect PE : IP.getNCClass(IP.getTeam()).getEffects())
				p.addPotionEffect(PE);
	}

	public static void addEffectOnContact(Player p, Player u) {
		NCPlayer IP = NCPlayerManager.getNCPlayer(p);
		if (!IP.getNCClass(IP.getTeam()).getContactEffects().isEmpty())
			for (PotionEffect PE : IP.getNCClass(IP.getTeam()).getContactEffects())
				u.addPotionEffect(PE);
	}
}
