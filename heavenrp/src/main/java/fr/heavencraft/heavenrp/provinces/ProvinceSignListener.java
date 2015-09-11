package fr.heavencraft.heavenrp.provinces;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.block.SignChangeEvent;

import fr.heavencraft.async.queries.BatchQuery;
import fr.heavencraft.async.queries.QueriesHandler;
import fr.heavencraft.async.queries.Query;
import fr.heavencraft.exceptions.HeavenException;
import fr.heavencraft.heavenrp.RPPermissions;
import fr.heavencraft.heavenrp.database.users.RemoveProvinceQuery;
import fr.heavencraft.heavenrp.database.users.UpdateUserBalanceQuery;
import fr.heavencraft.heavenrp.general.users.User;
import fr.heavencraft.heavenrp.general.users.UserProvider;
import fr.heavencraft.heavenrp.provinces.ProvincesManager.Province;
import fr.heavencraft.heavenrp.scoreboards.ProvinceScoreboard;
import fr.heavencraft.listeners.sign.SignListener;
import fr.heavencraft.utils.ChatUtil;

public class ProvinceSignListener extends SignListener
{
	private static final String JOIN = "Rejoindre";
	private static final String LEAVE = "Quitter";

	public ProvinceSignListener()
	{
		super("Province", RPPermissions.PROVINCE_SIGN);
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

		// Apply province colors
		ProvinceScoreboard.applyTeamColor(player, province);

		ChatUtil.sendMessage(player, "Vous venez de rejoindre la province de {%1$s}.", province.getName());
	}

	private void onLeaveSignClick(final Player player) throws HeavenException
	{
		User user = UserProvider.getUserByName(player.getName());

		if (user.getProvince() == null)
			throw new HeavenException("Vous n'êtes habitant d'aucune province.");

		List<Query> queries = new ArrayList<Query>();
		queries.add(new UpdateUserBalanceQuery(user, -50));
		queries.add(new RemoveProvinceQuery(user));
		QueriesHandler.addQuery(new BatchQuery(queries)
		{
			@Override
			public void onSuccess()
			{
				// Apply province colors
				ProvinceScoreboard.applyTeamColor(player, null);

				ChatUtil.sendMessage(player, "Vous ne faîtes plus partie d'aucune province.");
				ChatUtil.sendMessage(player, "Les frais de dossier vous ont coûté {50} pièces d'or.");
			}
		});
	}
}