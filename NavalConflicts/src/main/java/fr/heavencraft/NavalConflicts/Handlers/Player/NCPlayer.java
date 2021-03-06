package fr.heavencraft.NavalConflicts.Handlers.Player;

import java.util.Map.Entry;
import java.util.Random;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.scoreboard.DisplaySlot;

import fr.heavencraft.NavalConflicts.Game;
import fr.heavencraft.NavalConflicts.GameMechanics.Settings;
import fr.heavencraft.NavalConflicts.GameMechanics.Stats;
import fr.heavencraft.NavalConflicts.Handlers.Arena;
import fr.heavencraft.NavalConflicts.Handlers.ItemHandler;
import fr.heavencraft.NavalConflicts.Handlers.Lobby;
import fr.heavencraft.NavalConflicts.Handlers.LocationHandler;
import fr.heavencraft.NavalConflicts.Handlers.NCClass;
import fr.heavencraft.NavalConflicts.Handlers.NCClassManager;
import fr.heavencraft.NavalConflicts.Handlers.SaveItemHandler;
import fr.heavencraft.NavalConflicts.Handlers.Lobby.GameState;
import fr.heavencraft.NavalConflicts.Handlers.Potions.PotionEffects;
import fr.heavencraft.NavalConflicts.Messages.Msgs;
import fr.heavencraft.NavalConflicts.Tools.IconMenu;
import fr.heavencraft.NavalConflicts.Events.NCLeaveEvent;
import fr.heavencraft.NavalConflicts.Extras.ScoreBoard;

public class NCPlayer {
	private Player player;
	private UUID uuid;
	private Arena vote;
	private String name;
	private int killstreak = 0;
	private long timeIn;
	private GameMode gamemode;
	private int level;
	private float exp;
	private double health;
	private int food;
	private ItemStack[] armor;
	private ItemStack[] inventory;
	private Location location;
	private String creating;
	private Player lastDamager;
	private NCClass ncClass;
	private Team team = Team.Blue;
	private boolean isWinner = true;
	private boolean isInfChatting = false;
	private ScoreBoard ScoreBoard = new ScoreBoard(this);
	



	public NCPlayer(Player p)
	{
		name = p.getName();
		player = p;
		setUuid(p.getUniqueId());

	}
	
	/**
	 * @return the uuid
	 */
	public UUID getUuid() {
		return uuid;
	}

	/**
	 * @param uuid the uuid to set
	 */
	public void setUuid(UUID uuid) {
		this.uuid = uuid;
	}

	public ScoreBoard getScoreBoard() {
		return ScoreBoard;
	}

	public boolean isInGame() {
		return Lobby.isInGame(player);
	}

	/**
	 * Saves: <li>Location - Name - Gamemode - Level - Exp - Health - Food -
	 * Inventory - Armor</li> Clears: <li>Armor - Inventory Sets: <li>Gamemode
	 * to adventure - Sets level to 0 - Sets the players exp to 0 - Sets the
	 * players health to 20 - Sets the food to 20
	 */
	public void setInfo() {
		location = player.getLocation();
		name = player.getName();
		gamemode = player.getGameMode();
		level = player.getLevel();
		exp = player.getExp();
		health = player.getHealth();
		food = player.getFoodLevel();
		inventory = player.getInventory().getContents();
		armor = player.getInventory().getArmorContents();
		clearEquipment();
		setNCClass(Team.Blue, NCClassManager.getDefaultClass());
		setNCClass(Team.Red, NCClassManager.getDefaultClass());

		player.setGameMode(GameMode.ADVENTURE);
		player.setLevel(0);
		player.setExp(0.0F);
		player.setHealth(20);
		player.setFoodLevel(20);
	}

	/**
	 * Clears the players armor and inventory
	 */
	public void clearEquipment() {
		player.getInventory().clear();
		player.getInventory().setArmorContents(null);
	}

	/**
	 * Restores: - Inventory - Armor - Gamemode - Level - Exp - Health - Food -
	 * Location Resets: - Potion Effects - timeIn
	 */
	@SuppressWarnings("deprecation")
	public void leaveNavalConflicts() {

		NCLeaveEvent le = new NCLeaveEvent(player);
		Bukkit.getPluginManager().callEvent(le);
		if (!le.isCancelled())
		{
			//unDisguise();
			clearEquipment();

			Player p = getPlayer();
			p.setGameMode(gamemode);
			p.setLevel(level);
			p.setExp(exp);
			p.setHealth(health);
			p.setFoodLevel(food);
			p.setFireTicks(0);
			p.getInventory().setContents(inventory);
			p.getInventory().setArmorContents(armor);
			p.updateInventory();

			for (PotionEffect effect : player.getActivePotionEffects())
				player.removePotionEffect(effect.getType());

			player.setFallDistance(0F);
			if (Lobby.getLeave() != null)
				p.teleport(Lobby.getLeave());
			else
				p.teleport(location);
			p.setFallDistance(0F);
			setTeam(Team.Blue);
			Lobby.delPlayerInGame(p);
			
			if (Lobby.getGameState() == GameState.Started)
				Stats.setPlayingTime(getName(), Stats.getPlayingTime(getName()) + getPlayingTime());

			if (getVote() != null && (Lobby.getGameState() == GameState.InLobby || Lobby.getGameState() == GameState.Voting))
				getVote().setVotes(getVote().getVotes() - getAllowedVotes());

			killstreak = 0;
			location = null;
			gamemode = null;
			level = 0;
			exp = 0;
			health = 20;
			food = 20;
			inventory = null;
			armor = null;
			vote = null;
			timeIn = 0;
			setNCChatting(false);

			player.sendMessage(Msgs.Game_Left_You.getString());

			manageLeaving();

			for (Player u : Lobby.getPlayersInGame())
				if (u.getName() != this.name)
					NCPlayerManager.getNCPlayer(name).getScoreBoard().showProperBoard();

			player.getScoreboard().clearSlot(DisplaySlot.SIDEBAR);
		}
	}

	public void manageLeaving() {
	
		// Is there anyone left in the lobby?
		if (Lobby.getPlayersInGame().size() == 0)
			Lobby.reset();

		// If nothing has started yet, just inform players they left
		else if (Lobby.getGameState() == GameState.InLobby)
			for (Player u : Lobby.getPlayersInGame())
				u.sendMessage(Msgs.Game_Left_They.getString("<player>", player.getName()));

		// If the game isn't fully started yet, this includes Voting, and before
		// and Infecteds chosen
		else if (Lobby.getGameState() == GameState.Voting )
		{

			for (Player u : Lobby.getPlayersInGame())
				u.sendMessage(Msgs.Game_Left_They.getString("<player>", player.getName()));

			// If theres only one person left in the lobby, end the game
			if (Lobby.getPlayersInGame().size() <= 1)
			{
				Lobby.setGameState(GameState.InLobby);
				for (Player u : Lobby.getPlayersInGame())
				{
					u.sendMessage(Msgs.Game_End_Not_Enough_Players.getString());
					NCPlayerManager.getNCPlayer(u).tpToLobby();
				}

				// Reset all the timers, lists, etc(Not including the ones for
				// people in Infected)
				Lobby.reset();
			}
		}

		// If the game has fully started
		else if (Lobby.getGameState() == GameState.Started)
		{
			for (Player u : Lobby.getPlayersInGame())
				u.sendMessage(Msgs.Game_Left_They.getString("<player>", player.getName()));

			// If theres only one person left in the lobby, end the game
			if (Lobby.getPlayersInGame().size() <= 1)
			{
				Lobby.setGameState(GameState.InLobby);
				for (Player u : Lobby.getPlayersInGame())
				{
					u.sendMessage(Msgs.Game_End_Not_Enough_Players.getString());
					NCPlayerManager.getNCPlayer(u).tpToLobby();
				}

				// Reset all the timers, lists, etc(Not including the ones for
				// people in Infected)
				Lobby.reset();
			}

			// If theres no zombies left
			else if (Lobby.getTeam(Team.Red).size() == 0)
				// equilibre les equipes
				Game.equilibrateTeams();

			// If theres no humans left(Player who left was human, or the new
			// zombie was the only human)
			else if (Lobby.getTeam(Team.Blue).size() == 0)
			{
				Lobby.setGameState(GameState.InLobby);
				for (Player u : Lobby.getPlayersInGame())
				{
					u.sendMessage(Msgs.Game_End_Not_Enough_Players.getString());
					NCPlayerManager.getNCPlayer(u).tpToLobby();
				}

				// Reset all the timers, lists, etc(Not including the ones for
				// people in Infected)
				Lobby.reset();
			}

		}
	}

//	/**
//	 * Disguises the player
//	 */
//	public void disguise() {
//		if (Settings.DisguisesEnabled())
//			if (!Disguises.isPlayerDisguised(player))
//				Disguises.disguisePlayer(player);
//	}
//
//	/**
//	 * Undisguises the player
//	 */
//	public void unDisguise() {
//		if (Settings.DisguisesEnabled())
//			if (Disguises.isPlayerDisguised(player))
//				Disguises.unDisguisePlayer(player);
//	}

	/**
	 * - Updates Scoreboard - Play a sound - Set level to 0 - Set exp to 0 -
	 * Teleport to the lobby - Set gamemode - Remove Fire - Set health to 20 -
	 * Set Food to 20 - Undisguise the player
	 */
	public void tpToLobby() {
		
		clearEquipment();
		setTeam(Team.Blue);

		player.setFlying(false);

		player.playSound(player.getLocation(), Sound.ENDERDRAGON_WINGS, 1, 1);
		player.setLevel(0);
		player.setExp(0.0F);
		player.setFallDistance(0F);
		player.teleport(Lobby.getLocation());
		player.setFallDistance(0F);
		player.setGameMode(GameMode.ADVENTURE);
		player.setFireTicks(0);
		player.setHealth(20.0);
		player.setFoodLevel(20);

		for (PotionEffect effect : player.getActivePotionEffects())
			player.removePotionEffect(effect.getType());

		if (!SaveItemHandler.getSavedItems(player).isEmpty())
			try
			{
				for (String string : SaveItemHandler.getSavedItems(player))
					player.getInventory().addItem(ItemHandler.getItemStack(string));

			} catch (Exception e)
			{
				player.sendMessage(Msgs.Format_Prefix.getString() + ChatColor.RED + ChatColor.BOLD + "Tell an Admin that your saved inventory is invalid!");
			}

//		unDisguise();
		killstreak = 0;
		vote = null;
		lastDamager = null;
		isWinner = true;

		getScoreBoard().showProperBoard();
	}

	/**
	 * - Set health and food to 20 - Remove Fire Ticks - Teleport to new spawn -
	 * Clear potion effects
	 */
	public void respawn() {
		getScoreBoard().showProperBoard();
		Player p = player;
		p.setHealth(20.0);
		p.setFoodLevel(20);
		p.setFireTicks(0);
		Random r = new Random();
		//choisir un spawn au hasard parmis ceux dispo
		int i = r.nextInt(Lobby.getActiveArena().getSpawns(team).size());
		String loc = Lobby.getActiveArena().getSpawns(team).get(i);

		p.setFallDistance(0F);
		p.teleport(LocationHandler.getPlayerLocation(loc));
		p.setFallDistance(0F);

		for (PotionEffect reffect : player.getActivePotionEffects())
			player.removePotionEffect(reffect.getType());

		PotionEffects.applyClassEffects(player);

		p.setFallDistance(0F);
		lastDamager = null;
	}

	/**
	 * Change team to Zombie Set winner to false Change their equipment to
	 * zombie apply potion effects disguise update scoreboard apply confussion
	 */
//	public void Infect() {
//		Player p = player;
//		p.setHealth(20.0);
//		p.setFoodLevel(20);
//		p.setFireTicks(0);
//		player.playSound(player.getLocation(), Sound.ZOMBIE_INFECT, 1, 1);
//		team = Team.Red;
//		isWinner = false;
//		//TODO Equip.equipToZombie(player);
//		for (PotionEffect reffect : player.getActivePotionEffects())
//			player.removePotionEffect(reffect.getType());
//
//		PotionEffects.applyClassEffects(player);
////		disguise();
//
//		getScoreBoard().showProperBoard();
//		player.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 20, 2));
//	}

	public long getPlayingTime() {
		return (System.currentTimeMillis() / 1000) - getTimeIn();
	}

	/**
	 * @return the player
	 */
	public Player getPlayer() {
		return player;
	}

	/**
	 * @param player
	 *            the player to set
	 */
	public void setPlayer(Player player) {
		this.player = player;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the points
	 */
	public int getPoints() {
		return Stats.getPoints(name);
	}

	/**
	 * @param points
	 *            the points to set
	 */
	public void setPoints(int points) {
		Stats.setPoints(name, points);
	}

	/**
	 * @return the score
	 */
	public int getScore() {
		return Stats.getScore(name);
	}

	/**
	 * @param score
	 *            the score to set
	 */
	public void setScore(int score) {
		Stats.setScore(name, score);
	}

	/**
	 * @return the killstreak
	 */
	public int getKillstreak() {
		return killstreak;
	}

	/**
	 * @param killstreak
	 *            the killstreak to set
	 */
	public void setKillstreak(int killstreak) {
		this.killstreak = killstreak;
	}

	/**
	 * @return the timeJoined
	 */
	public long getTimeIn() {
		return timeIn;
	}

	/**
	 * @param timeIn
	 *            the timeIn to set
	 */
	public void setTimeIn(long timeIn) {
		this.timeIn = timeIn;
	}

	/**
	 * @return the gamemode
	 */
	public GameMode getGamemode() {
		return gamemode;
	}

	/**
	 * @param gamemode
	 *            the gamemode to set
	 */
	public void setGamemode(GameMode gamemode) {
		this.gamemode = gamemode;
	}

	/**
	 * @return the level
	 */
	public int getLevel() {
		return level;
	}

	/**
	 * @param level
	 *            the level to set
	 */
	public void setLevel(int level) {
		this.level = level;
	}

	/**
	 * @return the exp
	 */
	public float getExp() {
		return exp;
	}

	/**
	 * @param exp
	 *            the exp to set
	 */
	public void setExp(float exp) {
		this.exp = exp;
	}

	/**
	 * @return the health
	 */
	public double getHealth() {
		return health;
	}

	/**
	 * @param health
	 *            the health to set
	 */
	public void setHealth(double health) {
		this.health = health;
	}

	/**
	 * @return the food
	 */
	public int getFood() {
		return food;
	}

	/**
	 * @param food
	 *            the food to set
	 */
	public void setFood(int food) {
		this.food = food;
	}

	/**
	 * @return the armor
	 */
	public ItemStack[] getArmor() {
		return armor;
	}

	/**
	 * @param armor
	 *            the armor to set
	 */
	public void setArmor(ItemStack[] armor) {
		this.armor = armor;
	}

	/**
	 * @return the inventory
	 */
	public ItemStack[] getInventory() {
		return inventory;
	}

	/**
	 * @param inventory
	 *            the inventory to set
	 */
	public void setInventory(ItemStack[] inventory) {
		this.inventory = inventory;
	}

	/**
	 * @return the location
	 */
	public Location getLocation() {
		return location;
	}

	/**
	 * @param location
	 *            the location to set
	 */
	public void setLocation(Location location) {
		this.location = location;
	}

	/**
	 * @return the creating
	 */
	public String getCreating() {
		return creating;
	}

	/**
	 * @param creating
	 *            the creating to set
	 */
	public void setCreating(String creating) {
		this.creating = creating;
	}

	/**
	 * @return the lastDamager
	 */
	public Player getLastDamager() {
		return lastDamager;
	}

	/**
	 * @param lastDamager
	 *            the lastDamager to set
	 */
	public void setLastDamager(Player lastDamager) {
		this.lastDamager = lastDamager;
	}

	/**
	 * @return the kills
	 */
	public int getKills() {
		return Stats.getKills(name);
	}

	/**
	 * @param kills
	 *            the kills to set
	 */
	public void setKills(int kills) {
		Stats.setKills(name, kills);
	}

	/**
	 * @return the deaths
	 */
	public int getDeaths() {
		return Stats.getDeaths(name);
	}

	/**
	 * @param deaths
	 *            the deaths to set
	 */
	public void setDeaths(int deaths) {
		this.setDeaths(deaths);
	}

	/**
	 * @return the infClass
	 */
	public NCClass getNCClass(Team team) {
		if (team == Team.Blue)
			return ncClass;
		else
			return ncClass;
	}

	/**
	 * @param infClass
	 *            the infClass to set
	 */
	public void setNCClass(Team team, NCClass Class) {
		if (team == Team.Blue)
			ncClass = Class;
		else
			ncClass = Class;
	}

	/**
	 * @return the team
	 */
	public Team getTeam() {
		return team;
	}

	/**
	 * @param team
	 *            the team to set
	 */
	public void setTeam(Team team) {
		this.team = team;
	}

	/**
	 * @return the vote
	 */
	public Arena getVote() {
		return vote;
	}

	/**
	 * @param vote
	 *            the vote to set
	 */
	public void setVote(Arena vote) {
		this.vote = vote;
	}

	public void updateStats(int kills, int deaths) {
		if (kills != 0)
			Stats.setKills(name, Stats.getKills(name) + kills);
		if (deaths != 0)
			Stats.setDeaths(name, Stats.getDeaths(name) + deaths);
	}

	/**
	 * @return the isWinner
	 */
	public boolean isWinner() {
		return isWinner;
	}

	/**
	 * @param isWinner
	 *            the isWinner to set
	 */
	public void setWinner(boolean isWinner) {
		this.isWinner = isWinner;
	}

	/**
	 * @return the isInfChatting
	 */
	public boolean isNCChatting() {
		return isInfChatting;
	}

	/**
	 * @param isInfChatting
	 *            the isInfChatting to set
	 */
	public void setNCChatting(boolean isInfChatting) {
		this.isInfChatting = isInfChatting;
	}

	public int getHighestKillStreak() {
		return Stats.getHighestKillStreak(name);
	}

	/**
	 * @return how many votes they have
	 */
	public int getAllowedVotes() {
		int votes = 1;

		for (Entry<String, Integer> node : Settings.getExtraVoteNodes().entrySet())
		{
			if (player.hasPermission("NavalConflicts.vote." + node.getKey()) && node.getValue() > votes)
				votes = node.getValue();
		}
		return votes;
	}

	/**
	 * @return how many votes they have
	 */
	public int getPointsModifier() {
		int points = 1;

		for (Entry<String, Integer> node : Settings.getPointsModifiers().entrySet())
		{
			if (player.hasPermission("Infected.points." + node.getKey()) && node.getValue() > points)
				points = node.getValue();
		}
		return points;
	}

	/**
	 * @return how many votes they have
	 */
	public int getScoreModifier() {
		int score = 1;

		for (Entry<String, Integer> node : Settings.getScoreModifiers().entrySet())
		{
			if (player.hasPermission("Infected.points." + node.getKey()) && node.getValue() > score)
				score = node.getValue();
		}
		return score;
	}

	public void openMenu(Player player, IconMenu menu) {
		menu.open(player);
	}
}
