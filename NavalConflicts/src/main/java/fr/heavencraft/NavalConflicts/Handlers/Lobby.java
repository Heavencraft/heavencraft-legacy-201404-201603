package fr.heavencraft.NavalConflicts.Handlers;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import fr.heavencraft.NavalConflicts.Game;
import fr.heavencraft.NavalConflicts.NavalConflicts;
import fr.heavencraft.NavalConflicts.Utils;
import fr.heavencraft.NavalConflicts.Events.NCStartGame;
import fr.heavencraft.NavalConflicts.Events.NCStartVote;
import fr.heavencraft.NavalConflicts.GameMechanics.Settings;
import fr.heavencraft.NavalConflicts.Handlers.Player.NCPlayer;
import fr.heavencraft.NavalConflicts.Handlers.Player.NCPlayerManager;
import fr.heavencraft.NavalConflicts.Handlers.Player.Team;
import fr.heavencraft.NavalConflicts.Messages.Msgs;
import fr.heavencraft.NavalConflicts.Messages.Time;
import fr.heavencraft.NavalConflicts.Tools.Files;
import fr.heavencraft.NavalConflicts.Utils.Severity;

public class Lobby {
		// Toutes es arènes
		private static ArrayList<Arena> arenas = new ArrayList<Arena>();
		// Tout les joueurs dans Naval Conflicts
		private static ArrayList<Player> inGame = new ArrayList<Player>();
		// All the humans
		// What ever arena we're playing on
		private static Arena activeArena;
		
		// L'arène en edition acutelle
		private static Arena editedArena = null;
		
		// The games state
		private static GameState state = GameState.InLobby;

		private static int VotingTime;
		private static int GameTime;
		private static int TimeLeft;
		private static int currentGameTimer;
	
		public enum GameState
		{
			InLobby("In Lobby"),
			Voting("Voting"),
			Loading("Loading Arena"),
			Started("Started"),
			GameOver("Game Over"),
			Disabled("Disabled");

			private String s;

			private GameState(String string)
			{
				s = string;
			}

			public String toString() {
				return s;
			}
		};
		
		public Lobby()
		{
			loadArenas();
			NCClassManager.loadConfigClasses();
		}
		
		public static ArrayList<String> getValidArenas() {
			ArrayList<String> ga = new ArrayList<String>();
			for (Arena a : getArenas())
				if (isArenaValid(a))
					ga.add(a.getName());
			return ga;
		}

		public static ArrayList<String> getInValidArenas() {
			ArrayList<String> ba = new ArrayList<String>();
			for (Arena a : getArenas())
				if (!isArenaValid(a))
					ba.add(a.getName());
			return ba;
		}

		public static Location getLocation() {
			return LocationHandler.getPlayerLocation(Files.getConfig().getString("Lobby"));
		}

		public static void setLocation(Location loc) {
			Files.getConfig().set("Lobby", LocationHandler.getLocationToString(loc));
			Files.saveConfig();
		}

		public static ArrayList<Arena> getArenas() {
			return arenas;
		}

		public static boolean isInGame(Player p) {
			return inGame.contains(p);
		}

		public static void addPlayerInGame(Player p) {
			inGame.add(p);
		}

		public static void delPlayerInGame(Player p) {
			inGame.remove(p);
		}
		
		public static ArrayList<Player> getPlayersInGame() {
			return inGame;
		}
		
		@Deprecated
		public static ArrayList<Player> getInGame() {
			return inGame;
		}

		public static Team getPlayersTeam(Player p) {
			return NCPlayerManager.getNCPlayer(p).getTeam();
		}

		public static boolean isBlue(Player p) {
			return NCPlayerManager.getNCPlayer(p).getTeam() == Team.Blue;
		}

		public static boolean isRed(Player p) {
			return NCPlayerManager.getNCPlayer(p).getTeam() == Team.Red;
		}

		public static ArrayList<String> getBlue() {
			return getTeam(Team.Blue);
		}

		public static ArrayList<String> getRed() {
			return getTeam(Team.Red);
		}
		/**
		 * @return the teams list
		 */
		public static ArrayList<String> getTeam(Team team) {
			ArrayList<String> teamList = new ArrayList<String>();
			for (Player name : getPlayersInGame())
			{
				NCPlayer ip = NCPlayerManager.getNCPlayer(name);
				if (ip.getTeam() == team)
					teamList.add(name.getName());
			}
			return teamList;
		}

		/**
		 * Set the NCPlayer's team
		 * 
		 * @param ip
		 * @param team
		 */
		public static void setTeam(NCPlayer ip, Team team) {
			ip.setTeam(team);
		}

		public static boolean oppositeTeams(Player p, Player u) {
			NCPlayer ip = NCPlayerManager.getNCPlayer(p);
			NCPlayer iu = NCPlayerManager.getNCPlayer(u);
			return !(ip.getTeam() == iu.getTeam()) || (isInGame(p) && !isInGame(u)) || (!isInGame(p) && isInGame(u));
		}

		public static ArenaSettings getArenaSettings(Arena arena) {
			return arena.getSettings();
		}

		public static Arena getActiveArena() {
			if (activeArena == null)
				return new Arena("Default Arena");

			else
				return activeArena;
		}

		public static void setActiveArena(Arena arena) {
			activeArena = arena;
		}

		public static void setGameState(GameState gamestate) {
			state = gamestate;
		}

		public static GameState getGameState() {
			return state;
		}

		public static Arena addArena(Arena arena) {
			if (!arenas.contains(arena))
				arenas.add(arena);
			return arena;
		}

		public static Arena addArena(String arenaName) {
			Arena arena = new Arena(Utils.getWord(arenaName));
			if (arenas.contains(arena))
				arenas.add(arena);
			return arena;
		}

		public static Arena getArena(String arenaName) {
			for (Arena arena : arenas)
			{
				if (arena.getName().equalsIgnoreCase(arenaName))
					return arena;
			}
			return null;
		}

		public static void removeArena(Arena arena) {
			arenas.remove(arena);
			Files.getArenas().set("Arenas." + arena.getName(), null);
			Files.saveArenas();
		}

		public static void removeArena(String arenaName) {
			for (Arena arena : arenas)
			{
				if (arena.getName().equalsIgnoreCase(Utils.getWord(arenaName)))
					arenas.remove(arena);
			}
		}

		public static void loadArenas() {
			if (Files.getArenas().getConfigurationSection("Arenas") != null)
				for (String s : Files.getArenas().getConfigurationSection("Arenas").getKeys(false))
				{
					Arena arena = new Arena(Utils.getWord(s));
					addArena(arena);
					System.out.println("Loaded Arena: " + arena.getName());
				}
		}

		// Check if the arena is avalid
		public static boolean isArenaValid(String name) {
			name = Utils.getWord(name);
			if (!Files.getArenas().getStringList("Arenas." + name + ".Spawns").isEmpty() || (!Files.getArenas().getStringList("Arenas." + name + ".Zombie Spawns").isEmpty() && !Files.getArenas().getStringList("Arenas." + name + ".Human Spawns").isEmpty()))
				return true;
			else
				return !Files.getArenas().getStringList("Arenas." + name + ".Spawns").isEmpty();
		}

		// Check if the arena is avalid
		public static boolean isArenaValid(Arena arena) {
			if (arena == null)
				return false;
			else
			{
				if (!arena.getSpawns(Team.Global).isEmpty() || (!arena.getSpawns(Team.Red).isEmpty() && !arena.getSpawns(Team.Blue).isEmpty()))
					return true;
				else
					return !arena.getSpawns(Team.Global).isEmpty();
			}
		}

		public static void resetArena(Arena arena) {

			// Get the arena to fix any broken blocks
			arena.reset();

		}

		public static void reset() {
			stopTimer();
			Lobby.setGameState(GameState.InLobby);
			for (Arena a : Lobby.getArenas())
				a.setVotes(0);

			resetArena(getActiveArena());
			setActiveArena(null);
			for (Player name : getPlayersInGame())
				NCPlayerManager.getNCPlayer(name).setTeam(Team.Blue);

		}

		/**
		 * @return the currentTime
		 */
		public static int getTimeLeft() {
			return TimeLeft;
		}

		/**
		 * @param currentTime
		 *            the currentTime to set
		 */
		public static void setTimeLeft(int imeLeft) {
			TimeLeft = imeLeft;
		}

		/**
		 * @return the currentGameTimer
		 */
		public static int getCurrentGameTimer() {
			return currentGameTimer;
		}

		/**
		 * @param currentGameTimer
		 *            the currentGameTimer to set
		 */
		public static void setCurrentGameTimer(int currentGameTimer) {
			Lobby.currentGameTimer = currentGameTimer;
		}

		public static void stopTimer() {
			Bukkit.getScheduler().cancelTask(currentGameTimer);
			TimeLeft = 0;
		}

		public static void timerStartVote() {
			if (Lobby.getGameState() == GameState.InLobby)
			{
				stopTimer();
				VotingTime = Settings.getVotingTime();
				TimeLeft = VotingTime;

				setGameState(GameState.Voting);
				NCStartVote e = new NCStartVote();
				Bukkit.getPluginManager().callEvent(e);

				for (Player u : getPlayersInGame())
				{
					NCPlayer up = NCPlayerManager.getNCPlayer(u);
					up.getScoreBoard().showProperBoard();
					u.sendMessage("");
					u.sendMessage("");
					u.sendMessage("");
					u.sendMessage("");
					u.sendMessage("");
					u.sendMessage("");
					u.sendMessage("");
					u.sendMessage("");
					u.sendMessage("");
					u.sendMessage("");
					u.sendMessage("");
					u.sendMessage(Msgs.Format_Header.getString("<title>", " Vote "));
					u.sendMessage("");
					u.sendMessage(Msgs.Game_Time_Left_Voting.getString("<time>", Time.getTime((long) getTimeLeft())));
					u.sendMessage(Msgs.Help_Vote.getString());
					u.sendMessage("");
					u.sendMessage(Msgs.Format_Line.getString());
				}

				currentGameTimer = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(NavalConflicts.getInstance(), new Runnable()
				{

					@Override
					public void run() {
						if (getTimeLeft() != 0)
						{
							TimeLeft -= 1;

							for (Player u : getPlayersInGame())
								u.setLevel(getTimeLeft());

							if (TimeLeft == 60 || TimeLeft == 50 || TimeLeft == 40 || TimeLeft == 30 || TimeLeft == 20 || TimeLeft == 10 || TimeLeft == 9 || TimeLeft == 8 || TimeLeft == 7 || TimeLeft == 6 || TimeLeft == 5 || TimeLeft == 4 || TimeLeft == 3 || TimeLeft == 2 || TimeLeft == 1)
							{
								for (Player u : getPlayersInGame())
									u.sendMessage(Msgs.Game_Time_Left_Voting.getString("<time>", Time.getTime((long) getTimeLeft())));
							}
							if (TimeLeft == 5 || TimeLeft == 4 || TimeLeft == 3 || TimeLeft == 2)
							{
								for (Player u : getPlayersInGame())
								{
									u.playSound(u.getLocation(), Sound.NOTE_STICKS, 1, 1);
									u.playSound(u.getLocation(), Sound.NOTE_BASS_DRUM, 1, 1);
									u.playSound(u.getLocation(), Sound.NOTE_BASS_GUITAR, 1, 1);
								}
							}
							if (TimeLeft == 1)

								for (Player u : getPlayersInGame())
								{
									u.playSound(u.getLocation(), Sound.NOTE_STICKS, 1, 5);
									u.playSound(u.getLocation(), Sound.NOTE_BASS_DRUM, 1, 5);
									u.playSound(u.getLocation(), Sound.NOTE_BASS_GUITAR, 1, 5);
								}
							else if (TimeLeft == 0)
							{
								Arena arena = getArenas().get(0);
								for (Arena a : getArenas())
								{
									if (a.getVotes() > arena.getVotes())
										arena = a;
								}

								setActiveArena(arena);
								for (Player u : getPlayersInGame())
								{
									NCPlayer up = NCPlayerManager.getNCPlayer(u);
									up.getScoreBoard().showProperBoard();

									u.sendMessage("");
									u.sendMessage("");
									u.sendMessage("");
									u.sendMessage("");
									u.sendMessage("");
									u.sendMessage("");
									u.sendMessage("");
									u.sendMessage("");
									u.sendMessage("");
									u.sendMessage("");
									u.sendMessage("");
									u.sendMessage("");
									u.sendMessage("");
									u.sendMessage("");
									u.sendMessage("");
									u.sendMessage(Msgs.Format_Line.getString());
									u.sendMessage("");
									u.sendMessage(Msgs.Game_Starting_In_5.getString());
									u.sendMessage("");
									u.sendMessage(Msgs.Game_Info_Arena.getString("<arena>", getActiveArena().getName(), "<creator>", getActiveArena().getCreator()));
									u.sendMessage("");
									u.sendMessage(Msgs.Format_Line.getString());
									Lobby.setGameState(GameState.Loading);
									stopTimer();
								}
								Lobby.activeArena.initArena();
								currentGameTimer = Bukkit.getScheduler().scheduleSyncDelayedTask(NavalConflicts.getInstance(), new Runnable()
								{

									@Override
									public void run() {

										//preparation des equipes.
										
										Game.chooseTeams();			
										for (Player u : getPlayersInGame())
										{
											u.setGameMode(GameMode.SURVIVAL);
											NCPlayer up = NCPlayerManager.getNCPlayer(u);
											up.setTimeIn(System.currentTimeMillis() / 1000);
											up.respawn();
											up.getScoreBoard().showProperBoard();
											//TODO Equip.equip(u);
										}
										timerStartGame();
										
									}
								}, 100L);
							}
						}
					}
				}, 0L, 20L);
			}
		}

		public static void timerStartGame() {
			stopTimer();
			setGameState(GameState.Started);
			NCStartGame e = new NCStartGame();
			Bukkit.getPluginManager().callEvent(e);

			GameTime = getActiveArena().getSettings().getGameTime();
			TimeLeft = GameTime;

			for (Player u : getPlayersInGame())
			{
				NCPlayer up = NCPlayerManager.getNCPlayer(u);
				up.getScoreBoard().showProperBoard();
			}
			currentGameTimer = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(NavalConflicts.getInstance(), new Runnable()
			{

				@Override
				public void run() {
					if (TimeLeft != 0)
					{
						TimeLeft -= 1;
						for (Player u : getPlayersInGame())
							u.setLevel(TimeLeft);

						if (GameTime - TimeLeft == 10)
							for (Player u : getPlayersInGame())
								u.teleport(u.getLocation());

						if (TimeLeft == (GameTime / 4) * 3 || TimeLeft == GameTime / 2 || TimeLeft == GameTime / 4 || TimeLeft == 60 || TimeLeft == 10 || TimeLeft == 9 || TimeLeft == 8 || TimeLeft == 7 || TimeLeft == 6 || TimeLeft == 5 || TimeLeft == 4 || TimeLeft == 3 || TimeLeft == 2 || TimeLeft == 1)
						{
							if (TimeLeft > 61)
							{
								for (Player u : getPlayersInGame())
								{
									u.sendMessage(Msgs.Game_Time_Left_Game.getString("<time>", Time.getTime((long) TimeLeft)));
									u.sendMessage(Msgs.Game_Players_Left.getString("<Blue>", String.valueOf(getTeam(Team.Blue).size()), "<Red>", String.valueOf(getTeam(Team.Red).size())));
								}
							} else
								for (Player u : getPlayersInGame())
									u.sendMessage(Msgs.Game_Time_Left_Game.getString("<time>", Time.getTime((long) TimeLeft)));

						}
						if (TimeLeft == 5 || TimeLeft == 4 || TimeLeft == 3 || TimeLeft == 2)
						{

							for (Player u : getPlayersInGame())
							{
								u.playSound(u.getLocation(), Sound.NOTE_STICKS, 1, 1);
								u.playSound(u.getLocation(), Sound.NOTE_BASS_DRUM, 1, 1);
								u.playSound(u.getLocation(), Sound.NOTE_BASS_GUITAR, 1, 1);
							}
						}
						if (TimeLeft == 1)
						{
							for (Player u : getPlayersInGame())
							{
								u.playSound(u.getLocation(), Sound.ORB_PICKUP, 1, 5);
								u.playSound(u.getLocation(), Sound.LEVEL_UP, 1, 5);
							}
						} else if (TimeLeft == 0)
							Game.endGame(Team.Blue);
					}
				}
			}, 0L, 20L);
		}

		public static void loadAllArenas() {
			arenas.clear();
			if (Files.getArenas().getConfigurationSection("Arenas") != null)
				for (String a : Files.getArenas().getConfigurationSection("Arenas").getKeys(false))
				{
					Arena arena = new Arena(Utils.getWord(a));
					addArena(arena);

					if (Settings.logAreansEnabled())
						System.out.println("Loaded Arena: " + arena.getName());
				}
			else if (Settings.logAreansEnabled())
				System.out.println("Couldn't Find Any Arenas");

		}

		public static Location getLeave() {
			if (Files.getConfig().getString("Leave") == null || Files.getConfig().getString("Leave") == "")
				return null;
			else
				return LocationHandler.getPlayerLocation(Files.getConfig().getString("Leave"));
		}

		public static void setLeave(Location loc) {
			Files.getConfig().set("Leave", LocationHandler.getLocationToString(loc));
			Files.saveConfig();
		}
		
		
		/**
		 * @param currentEditedArena
		 *            the currentEditedArena to set
		 */
		public static void setEditedArena(Arena a) {
			if(editedArena != null){
				
				Utils.broadcastConsole(Bukkit.getConsoleSender(), Severity.WARNING , "Nouvelle arène va etre edité, preparation.");
				editedArena.setInEdit(false);
				//Teleporter les gens hors de cette arène.
				for(Player p : editedArena.getWorld().getPlayers())
				{
					p.teleport(Lobby.getLocation());
					NCPlayer ip = null;
					ip = NCPlayerManager.getNCPlayer(p);
					ip.setCreating("");
					p.sendMessage("Nouvelle arène en edition.");
				}
				editedArena.setInEdit(false);
				editedArena.reset();
			}
			
			if(a != null)
			{
			    editedArena = a;
			    a.setInEdit(true);
			}
			else
			{
				editedArena = null;
			}
		}

		/**
		 * @return the currenteditdArena
		 */
		public static Arena getEditedArena() {
			return editedArena;
		}
		
	
}
