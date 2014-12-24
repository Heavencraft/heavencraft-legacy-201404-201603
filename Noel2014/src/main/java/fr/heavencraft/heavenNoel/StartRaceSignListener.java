package fr.heavencraft.heavenNoel;

import org.bukkit.Bukkit;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.block.SignChangeEvent;

import fr.heavencraft.NoelPermissions;
import fr.heavencraft.SignListener;
import fr.heavencraft.exceptions.HeavenException;

public class StartRaceSignListener extends SignListener{
	public StartRaceSignListener()
	{
		super("Depart", NoelPermissions.NOEL_ADMIN);
	}

	@Override
	protected boolean onSignPlace(Player player, SignChangeEvent event) throws HeavenException {
		return true;
	}

	@Override
	protected void onSignClick(Player player, Sign sign) throws HeavenException {
		if(RacerManager.getRacer(player) == null)
		{
			Racer racer = new Racer(player);
			RacerManager.createRacer(racer);
		}
		else
			RacerManager.getRacer(player);
		RacerManager.handleRacerStart(player);
	}
}
