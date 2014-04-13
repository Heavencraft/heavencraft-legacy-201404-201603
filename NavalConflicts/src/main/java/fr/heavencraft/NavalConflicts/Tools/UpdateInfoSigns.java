package fr.heavencraft.NavalConflicts.Tools;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Sign;

import fr.heavencraft.NavalConflicts.NavalConflicts;
import fr.heavencraft.NavalConflicts.GameMechanics.Settings;
import fr.heavencraft.NavalConflicts.Handlers.Lobby;
import fr.heavencraft.NavalConflicts.Handlers.Lobby.GameState;
import fr.heavencraft.NavalConflicts.Handlers.LocationHandler;

public class UpdateInfoSigns {

	/**
	 * Update all signs every few seconds(Set in the config)
	 */
	public static void update() {

		Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(NavalConflicts.getInstance(), new Runnable()
		{

			@Override
			public void run() {
				if (!Files.getSigns().getStringList("Info Signs").isEmpty())
				{
					for (String loc : Files.getSigns().getStringList("Info Signs"))
					{
						String status = Lobby.getGameState().toString();

						int time = Lobby.getTimeLeft();

						Location location = LocationHandler.getObjectLocation(loc);
						if (location.getBlock().getType() == Material.SIGN_POST || location.getBlock().getType() == Material.WALL_SIGN)
						{
							Sign sign = (Sign) location.getBlock().getState();
							sign.setLine(1, ChatColor.GREEN + "Playing: " + ChatColor.DARK_GREEN + String.valueOf(Lobby.getPlayersInGame().size()));
							sign.setLine(2, ChatColor.GOLD + status);
							if (Lobby.getGameState() == GameState.Started || Lobby.getGameState() == GameState.Voting)
								sign.setLine(3, ChatColor.GRAY + "Time: " + ChatColor.YELLOW + String.valueOf(time));
							else
								sign.setLine(3, "");
							sign.update();
						}
					}

				}
			}
		}, 100L, Settings.InfoSignsUpdateTime() * 20);
	}
}
