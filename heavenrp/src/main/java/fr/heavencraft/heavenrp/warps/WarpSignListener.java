package fr.heavencraft.heavenrp.warps;

import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.block.SignChangeEvent;

import fr.heavencraft.Permissions;
import fr.heavencraft.SignListener;
import fr.heavencraft.Utils;
import fr.heavencraft.exceptions.HeavenException;

public class WarpSignListener extends SignListener
{
	public WarpSignListener()
	{
		super("Warp", Permissions.WARP_SIGN);
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
			Utils.sendMessage(player, ex.getMessage());
			return false;
		}
	}

	@Override
	protected void onSignClick(Player player, Sign sign) throws HeavenException
	{
		String name = sign.getLine(1);

		Utils.teleportPlayer(player, WarpsManager.getWarp(name).getLocation());
		Utils.sendMessage(player, "Vous avez été téléporté à {%1$s}.", name);
	}
}