package fr.heavencraft.NavalConflicts.commands.user;

import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.heavencraft.NavalConflicts.NavalConflicts;
import fr.heavencraft.NavalConflicts.Utils;
import fr.heavencraft.NavalConflicts.Handlers.Arena;
import fr.heavencraft.NavalConflicts.Handlers.Lobby;
import fr.heavencraft.NavalConflicts.Handlers.Lobby.GameState;
import fr.heavencraft.NavalConflicts.Handlers.Player.NCPlayer;
import fr.heavencraft.NavalConflicts.Handlers.Player.NCPlayerManager;
import fr.heavencraft.NavalConflicts.Messages.Msgs;
import fr.heavencraft.NavalConflicts.commands.NavalCommand;
import fr.heavencraft.NavalConflicts.exceptions.NavalException;

public class VoteCommand extends NavalCommand{

	public VoteCommand()
	{
		super("vote");
	}

	@Override
	protected void onPlayerCommand(Player player, String[] args) throws NavalException {
		NCPlayer ip =  NCPlayerManager.getNCPlayer(player);

		if (!Lobby.isInGame(player))
			player.sendMessage(Msgs.Error_Game_Not_In.getString());
		else if (Lobby.getGameState() != GameState.Voting && Lobby.getGameState() != GameState.InLobby)
			player.sendMessage(Msgs.Error_Game_Started.getString());
		else if (ip.getVote() != null)
			player.sendMessage(Msgs.Error_Already_Voted.getString());
		else
		{
			// If the user didn't specify an arena, open the voting
			// GUI
			if (args.length == 0)
				NavalConflicts.Menus.voteMenu.open(player);
			else
			{
				// Check if the user voted for Random
				Arena arena;
				if (args[0].equalsIgnoreCase("Random"))
				{
					int i;
					Random r = new Random();
					i = r.nextInt(Lobby.getArenas().size());
					arena = Lobby.getArenas().get(i);
					while (!Lobby.isArenaValid(arena))
					{
						i = r.nextInt(Lobby.getArenas().size());
						arena = Lobby.getArenas().get(i);
					}
				} else
				{
					// Assign arena to what ever the user said
					arena = Lobby.getArena(args[1]);
				}
				// If its a valid arena, let the user vote and set
				// everything
				if (Lobby.isArenaValid(arena))
				{
					arena.setVotes(arena.getVotes() + ip.getAllowedVotes());
					ip.setVote(arena);

					for (Player u : Lobby.getPlayersInGame())
					{
						u.sendMessage(Msgs.Command_Vote.getString("<player>", player.getName(), "<arena>", arena.getName()) + ChatColor.GRAY + (ip.getAllowedVotes() != 0 ? " (x" + ip.getAllowedVotes() + ")" : ""));
						NCPlayer up = NCPlayerManager.getNCPlayer(u);
						up.getScoreBoard().showProperBoard();
					}
				}
				// If its not a valid arena tell them that
				else
					player.sendMessage(Msgs.Error_Arena_Not_Valid.getString());
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
