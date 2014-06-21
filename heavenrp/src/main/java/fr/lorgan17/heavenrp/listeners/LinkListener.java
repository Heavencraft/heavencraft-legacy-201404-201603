package fr.lorgan17.heavenrp.listeners;

import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.block.SignChangeEvent;

import fr.heavencraft.exceptions.HeavenException;
import fr.heavencraft.heavenrp.RPPermissions;
import fr.heavencraft.listeners.SignListener;
import fr.heavencraft.utils.ChatUtil;

public class LinkListener extends SignListener
{
	public LinkListener()
	{
		super("Lien", RPPermissions.LINK_SIGN);
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