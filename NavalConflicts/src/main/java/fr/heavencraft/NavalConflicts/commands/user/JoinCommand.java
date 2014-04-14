package fr.heavencraft.NavalConflicts.commands.user;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.heavencraft.NavalConflicts.NavalConflicts;
import fr.heavencraft.NavalConflicts.Utils;
import fr.heavencraft.NavalConflicts.Events.NCJoinEvent;
import fr.heavencraft.NavalConflicts.GameMechanics.Settings;
import fr.heavencraft.NavalConflicts.Handlers.Lobby;
import fr.heavencraft.NavalConflicts.Handlers.Lobby.GameState;
import fr.heavencraft.NavalConflicts.Handlers.Player.NCPlayer;
import fr.heavencraft.NavalConflicts.Handlers.Player.NCPlayerManager;
import fr.heavencraft.NavalConflicts.Messages.Msgs;
import fr.heavencraft.NavalConflicts.commands.NavalCommand;
import fr.heavencraft.NavalConflicts.exceptions.NavalException;

public class JoinCommand extends NavalCommand{

	public JoinCommand()
	{
		super("join");
	}
	
	@Override
	protected void onPlayerCommand(Player player, String[] args) throws NavalException
	{
		NCPlayer ip = null;
		ip = NCPlayerManager.getNCPlayer(player);
		
		if (Lobby.getGameState() == GameState.Disabled)
			player.sendMessage(Msgs.Error_Misc_Plugin_Disabled.getString());

		else if (Lobby.getLocation() == null)
			player.sendMessage(Msgs.Error_Lobby_Doesnt_Exist.getString());

		else if (Lobby.getValidArenas().isEmpty())
			player.sendMessage(Msgs.Error_Arena_No_Valid.getString());

		else if (Settings.isJoiningDuringGamePrevented() && (Lobby.getGameState() == GameState.Started || Lobby.getGameState() == GameState.GameOver))
			player.sendMessage(Msgs.Error_Misc_Joining_While_Game_Started.getString());

		else if (Lobby.getPlayersInGame().contains(player.getName()))
			player.sendMessage(Msgs.Error_Game_In.getString());
		
		else
		{
			NCJoinEvent je = new NCJoinEvent(player);
			Bukkit.getPluginManager().callEvent(je);
			for (Player u : Lobby.getPlayersInGame())
				u.sendMessage(Msgs.Game_Joined_They.getString("<player>", player.getName()));

			ip.setInfo();
			Lobby.addPlayerInGame(player);
			
			// If the game isn't started and isn't infecting then
			// the players are all still in the lobby
			if (Lobby.getGameState() != GameState.Started)
				ip.tpToLobby();
			
			player.sendMessage(Msgs.Game_Joined_You.getString());
			
			// If the game hasn't started and there's enough players
			// for an autostart, start the timer
			if (Lobby.getGameState() == GameState.InLobby && Lobby.getPlayersInGame().size() >= Settings.getRequiredPlayers())
			{
				Bukkit.getScheduler().scheduleSyncDelayedTask(NavalConflicts.getInstance(), new Runnable()
				{
					@Override
					public void run() {

						Lobby.timerStartVote();
					}
				}, 100L);
			}
			// If voting has started, tell the new player how to
			// vote
			else if (Lobby.getGameState() == GameState.Voting)
				player.sendMessage(Msgs.Help_Vote.getString());

			// If the game has started already make the player a
			// zombie without calling any deaths(Event and stats)
			else if (Lobby.getGameState() == GameState.Started)
			{
				ip.setTimeIn(System.currentTimeMillis() / 1000);
				//Deaths.playerDiesWithoutDeathStat(player);
			}
			
		}

	}

	@Override
	protected void onConsoleCommand(CommandSender sender, String[] args) throws NavalException {
		Utils.sendMessage(sender, "Cette commande ne peut pas être utilisée depuis la {console}.");
		}

	@Override
	protected void sendUsage(CommandSender sender) {}
}
