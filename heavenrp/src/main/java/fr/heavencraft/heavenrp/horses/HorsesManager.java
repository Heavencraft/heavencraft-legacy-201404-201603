package fr.heavencraft.heavenrp.horses;

import org.bukkit.entity.Horse;
import org.bukkit.entity.Player;

import fr.heavencraft.heavenrp.RPPermissions;
import fr.heavencraft.utils.ChatUtil;

public class HorsesManager
{
	public static boolean isWild(Horse horse)
	{
		return horse.getOwner() == null;
	}

	public static boolean canUse(Horse horse, Player player)
	{
		if (player.hasPermission(RPPermissions.HORSE_BYPASS))
			return true;
		else if (isWild(horse))
			return true;
		else
			return horse.getOwner().getUniqueId().equals(player.getUniqueId());
	}

	public static void sendWarning(Horse horse, Player player)
	{
		ChatUtil.sendMessage(player, "Ce cheval appartient à {%1$s}.", horse.getOwner().getName());
	}
}