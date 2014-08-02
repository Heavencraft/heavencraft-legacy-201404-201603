package fr.heavencraft.listeners.sign;

import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.block.SignChangeEvent;

import fr.heavencraft.Permissions;
import fr.heavencraft.exceptions.HeavenException;
import fr.heavencraft.utils.ChatUtil;

public class LinkSignListener extends SignListener
{
	public LinkSignListener()
	{
		super("Lien", Permissions.LINK_SIGN);
	}

	@Override
	protected boolean onSignPlace(Player player, SignChangeEvent event)
	{
		return true;
	}

	@Override
	protected void onSignClick(Player player, Sign sign) throws HeavenException
	{
		ChatUtil.sendMessage(player, "Cliquez le lien suivant :");
		ChatUtil.sendMessage(player, "{http://" + sign.getLine(1) + "}");
	}
}