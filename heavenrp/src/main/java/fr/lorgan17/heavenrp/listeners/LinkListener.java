package fr.lorgan17.heavenrp.listeners;

import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.block.SignChangeEvent;

import fr.heavencraft.SignListener;
import fr.heavencraft.Utils;
import fr.heavencraft.exceptions.HeavenException;
import fr.heavencraft.heavenrp.HeavenRP;

public class LinkListener extends SignListener {

	public LinkListener(HeavenRP plugin)
	{
		super("Lien", "heavenrp.moderator.sign.lien");
	}

	@Override
	protected boolean onSignPlace(Player player, SignChangeEvent event)
	{
		return true;
	}

	@Override
	protected void onSignClick(Player player, Sign sign) throws HeavenException
	{
		Utils.sendMessage(player, "Cliquez le lien suivant :");
		Utils.sendMessage(player, "{http://" + sign.getLine(1) + "}");
	}
}
