package fr.heavencraft.heavenhallow.levels;

import org.bukkit.Bukkit;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.block.SignChangeEvent;

import fr.heavencraft.Hallowpermissions;
import fr.heavencraft.SignListener;
import fr.heavencraft.exceptions.HeavenException;
import fr.heavencraft.heavenhallow.player.HallowPlayerManager;

public class LevelSignListener extends SignListener{
	public LevelSignListener()
	{
		super("Stage", Hallowpermissions.DONJON_SIGN);
	}

	@Override
	protected boolean onSignPlace(Player player, SignChangeEvent event) throws HeavenException {
		return true;
	}

	@Override
	protected void onSignClick(Player player, Sign sign) throws HeavenException {
			Level next = Level.LOBBY;
			if(sign.getLine(1).equalsIgnoreCase(Level.LOBBY.getStageName()))
				next = Level.LOBBY;
			else if(sign.getLine(1).equalsIgnoreCase(Level.THEBEGIN.getStageName()))
				next = Level.THEBEGIN;
			else if(sign.getLine(1).equalsIgnoreCase(Level.THEFOREST.getStageName()))
				next = Level.THEFOREST;
			else if(sign.getLine(1).equalsIgnoreCase(Level.THETIGER.getStageName()))
				next = Level.THETIGER;
			else if(sign.getLine(1).equalsIgnoreCase(Level.THEMOON.getStageName()))
				next = Level.THEMOON;
			else if(sign.getLine(1).equalsIgnoreCase(Level.THEFALL.getStageName()))
				next = Level.THEFALL;
			else
				Bukkit.broadcastMessage("MAUVAIS STAGE! CLAMPIN!");
			
			HallowPlayerManager.handlePlayerStage(next, player);
	}
}
