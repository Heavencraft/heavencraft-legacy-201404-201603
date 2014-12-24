package fr.heavencraft.heavenNoel;

import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.block.SignChangeEvent;

import fr.heavencraft.NoelPermissions;
import fr.heavencraft.SignListener;
import fr.heavencraft.exceptions.HeavenException;

public class EndRaceSignListener extends SignListener{
	public EndRaceSignListener()
	{
		super("Arrivee", NoelPermissions.NOEL_ADMIN);
	}

	@Override
	protected boolean onSignPlace(Player player, SignChangeEvent event) throws HeavenException {
		return true;
	}

	@Override
	protected void onSignClick(Player player, Sign sign) throws HeavenException {
		Racer r = RacerManager.getRacer(player);
		if(r != null)
			RacerManager.handleRacerEnd(r);
	}
}
