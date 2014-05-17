package fr.heavencraft.heavenrp.provinces;

import org.bukkit.ChatColor;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.block.SignChangeEvent;

import fr.heavencraft.Permissions;
import fr.heavencraft.SignListener;
import fr.heavencraft.Utils;
import fr.heavencraft.exceptions.HeavenException;
import fr.heavencraft.heavenrp.general.users.User;
import fr.heavencraft.heavenrp.general.users.UserProvider;
import fr.heavencraft.heavenrp.provinces.ProvincesManager.Province;

public class ProvinceSignListener extends SignListener
{
	private static final String JOIN = "Rejoindre";
	private static final String LEAVE = "Quitter";

	public ProvinceSignListener()
	{
		super("Province", Permissions.PROVINCE_SIGN);
	}

	@Override
	protected boolean onSignPlace(Player player, SignChangeEvent event) throws HeavenException
	{
		if (event.getLine(1).equalsIgnoreCase(JOIN))
		{
			// On vérifie si la province existe.
			ProvincesManager.getProvinceByName(event.getLine(2));

			event.setLine(1, ChatColor.BLUE + JOIN);
			return true;
		}

		else if (event.getLine(1).equalsIgnoreCase(LEAVE))
		{
			event.setLine(1, ChatColor.BLUE + LEAVE);
			return true;
		}

		else
			return false;
	}

	@Override
	protected void onSignClick(Player player, Sign sign) throws HeavenException
	{
		if (sign.getLine(1).equals(ChatColor.BLUE + JOIN))
			onJoinSignClick(player, sign.getLine(2));

		else if (sign.getLine(1).equals(ChatColor.BLUE + LEAVE))
			onLeaveSignClick(player);
	}

	private void onJoinSignClick(Player player, String provinceName) throws HeavenException
	{
		User user = UserProvider.getUserByName(player.getName());

		if (user.getProvince() != null)
			throw new HeavenException("Vous êtes déjà habitant d'une province");

		Province province = ProvincesManager.getProvinceByName(provinceName);
		user.setProvince(province.getId());

		Utils.sendMessage(player, "Vous venez de rejoindre la province de {%1$s}.", province.getName());
	}

	private void onLeaveSignClick(Player player) throws HeavenException
	{
		User user = UserProvider.getUserByName(player.getName());

		if (user.getProvince() == null)
			throw new HeavenException("Vous n'êtes habitant d'aucune province.");

		user.updateBalance(-50);
		user.removeProvince();

		Utils.sendMessage(player, "Vous ne faîtes plus partie d'aucune province.");
		Utils.sendMessage(player, "Les frais de dossier vous ont coûté {50} pièces d'or.");
	}
}