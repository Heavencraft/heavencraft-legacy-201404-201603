package fr.heavencraft.heavenrp.warps;

import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.block.SignChangeEvent;

import fr.heavencraft.exceptions.HeavenException;
import fr.heavencraft.heavenrp.RPPermissions;
import fr.heavencraft.listeners.sign.SignListener;
import fr.heavencraft.utils.ChatUtil;
import fr.heavencraft.utils.PlayerUtil;

public class WarpSignListener extends SignListener
{
	public WarpSignListener()
	{
		super("Warp", RPPermissions.WARP_SIGN);
	}

	@Override
	protected boolean onSignPlace(Player player, SignChangeEvent event)
	{
		try
		{
			WarpsManager.getWarp(event.getLine(1));
			return true;
		}
		catch (HeavenException ex)
		{
			ChatUtil.sendMessage(player, ex.getMessage());
			return false;
		}
	}

	@Override
	protected void onSignClick(Player player, Sign sign) throws HeavenException
	{
		String name = sign.getLine(1);

		PlayerUtil.teleportPlayer(player, WarpsManager.getWarp(name).getLocation());
		ChatUtil.sendMessage(player, "Vous avez été téléporté à {%1$s}.", name);
	}
}