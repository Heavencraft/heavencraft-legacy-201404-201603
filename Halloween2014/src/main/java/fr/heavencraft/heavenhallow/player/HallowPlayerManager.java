package fr.heavencraft.heavenhallow.player;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import fr.heavencraft.ChatUtil;
import fr.heavencraft.HeavenHallow;
import fr.heavencraft.Utils.ParticleEffect;
import fr.heavencraft.heavenhallow.levels.Level;

public class HallowPlayerManager {
	private static ArrayList<HallowPlayer> players = new ArrayList<HallowPlayer>();

	private final static String TIGER_BONBON_1 = "Insecte tu es insignifiant, et pourtant tu as le courage de courber l'échine devant moi; soit j'accepte ton offrande.";
	private final static String TIGER_BONBON_2 = "Mais sache que je ne te garantis rien de plus qu'un voyage vers l'enfer.";
	private final static String TIGER_BONBON_3 = "Ton sort m'importe peu, mais accroche-toi, la lune n'est qu'à un bond.";

	private final static String TIGER_NO_BONBON_1 = "Tu oses tout d'abord te présenter devant moi, qui plus est sans aucune offrande et me faire une demande ?";
	private final static String TIGER_NO_BONBON_2 = "Insecte, mes crocs ne sont même pas digne de te briser !";
	private final static String TIGER_NO_BONBON_3 = "Mais soit; je t'offre une mort courte et aussi insignifiante que ta misérable vie.";

	
	private final static String AVENTURE_IS_OVER = "Ce monde s'effondre, le portail est instable...";
	
	public static void handlePlayerStage(final Level nextStage, final Player p)
	{
		final HallowPlayer hp = getHallowPlayer(p.getName());

		if(hp == null)
		{
			createHallowPlayer(new HallowPlayer(p));
			return;
		}

		if(nextStage == Level.THEMOON)
		{
			if(hasBonbonAmmount(p,10))
			{
				ChatUtil.sendMessage(p, TIGER_BONBON_1);

				new BukkitRunnable() {
					@Override
					public void run() { ChatUtil.sendMessage(p, TIGER_BONBON_2); }}.runTaskLater(HeavenHallow.getInstance(), 80);

					new BukkitRunnable() {
						@Override
						public void run() {
							removeBonbonAmmount(p, 10);

							ChatUtil.sendMessage(p, TIGER_BONBON_3); 
							ParticleEffect.PORTAL.display(p.getPlayer().getLocation().add(0, 1, 0), 10, 0, 0, 0, 10, 1000);
							p.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 60, 255));
							p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 120, 255));
							p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 120, 255));
						}}.runTaskLater(HeavenHallow.getInstance(), 140);

						new BukkitRunnable() {
							@Override
							public void run() { 
								hp.setLevel(nextStage);
								hp.getPlayer().teleport(nextStage.getSpawnLoc());
							}}.runTaskLater(HeavenHallow.getInstance(), 200);


			}
			else
			{
				//Pas assez de bonbons, il meurs
				ChatUtil.sendMessage(p, TIGER_NO_BONBON_1);

				new BukkitRunnable() {
					@Override
					public void run() { ChatUtil.sendMessage(p, TIGER_NO_BONBON_2); }}.runTaskLater(HeavenHallow.getInstance(), 80);


					new BukkitRunnable() {
						@Override
						public void run() {					
							p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 120, 255));
							p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 120, 255));
							p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 120, 255));
						}}.runTaskLater(HeavenHallow.getInstance(), 100);


						new BukkitRunnable() {
							@Override
							public void run() { ChatUtil.sendMessage(p, TIGER_NO_BONBON_3); }
						}.runTaskLater(HeavenHallow.getInstance(), 140);

						new BukkitRunnable() {
							@Override
							public void run() { ParticleEffect.PORTAL.display(p.getPlayer().getLocation().add(0, 1, 0), 10, 0, 0, 0, 10, 1000); }
						}.runTaskLater(HeavenHallow.getInstance(), 180);

						new BukkitRunnable() {
							@Override
							public void run() { HallowPlayerManager.handlePlayerRespawn(p); }}.runTaskLater(HeavenHallow.getInstance(), 200);
			}
		}
		else if(nextStage == Level.LOBBY)
		{
			// Faire le décompte des bonbons
			int ammnt = countBonbon(p);
			try {
				PreparedStatement ps = HeavenHallow.getConnection().prepareStatement(
						"INSERT INTO `classement` (`player`, `ammount`) VALUES (?, ?)");
				ps.setString(1, p.getName());
				ps.setInt(2, ammnt);
				ps.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 60, 255));
			p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 60, 255));
			hp.setLevel(nextStage);
			hp.getPlayer().teleport(nextStage.getSpawnLoc());
		}
		else
		{
			if(hp.HasFinished())
			{
				ChatUtil.sendMessage(p, AVENTURE_IS_OVER);
			}
			else
			{
				p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 60, 255));
				p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 60, 255));
				hp.setLevel(nextStage);
				hp.getPlayer().teleport(nextStage.getSpawnLoc());
			}
			
		}

	}

	public static boolean hasBonbonAmmount(Player p, int ammount)
	{
		return p.getInventory().containsAtLeast(new ItemStack(Material.DOUBLE_PLANT), ammount);
	}

	public static void removeBonbonAmmount(Player p, int ammount)
	{
		PlayerInventory inv = p.getInventory();
		ItemStack bonbons = new ItemStack(Material.DOUBLE_PLANT,ammount);
		inv.remove(bonbons);
	}
	public static int countBonbon(Player p)
	{
		PlayerInventory inv = p.getInventory();
		ItemStack[] content = inv.getContents();
		int has = 0;
		for (ItemStack item : content)
			if((item != null) && (item.getType()== Material.DOUBLE_PLANT) && (item.getAmount() > 0))
				has += item.getAmount();
		return has;
	}
	public static void handlePlayerRespawn(Player p)
	{
		HallowPlayer hp = getHallowPlayer(p.getName());
		hp.getPlayer().setGameMode(GameMode.SURVIVAL);
		hp.getPlayer().setFlying(false);
		hp.getPlayer().setFireTicks(1);
		hp.getPlayer().teleport(hp.getLevel().getSpawnLoc());
		hp.getPlayer().setHealth(20);
		hp.getPlayer().setFoodLevel(20);
		hp.getPlayer().getActivePotionEffects().clear();
	}


	public static void createHallowPlayer(HallowPlayer p)
	{
		if(!players.contains(p))
			players.add(p);
	}

	public static HallowPlayer createHallowPlayer(Player p)
	{
		HallowPlayer rp = new HallowPlayer(p);
		if(!players.contains(rp))
			players.add(rp);
		return rp;
	}

	public static void removeHallRowPlayer(String playerName) {
		for (HallowPlayer player : players)
		{
			if (player.getPlayer().getName().equalsIgnoreCase(playerName))
				players.remove(player);
		}
	}
	
	public static void removeHallowPlayer(HallowPlayer IP) {
		players.remove(IP);
	}

	/**
	 * Get NCPlayer
	 * 
	 * @param playername
	 */
	public static HallowPlayer getHallowPlayer(String playerName) {
		for (HallowPlayer IP : players)
		{
			if(IP.getPlayer() != null)
			{
				Bukkit.getLogger().log(java.util.logging.Level.WARNING, "Liste: " + IP.getPlayer().getName());
				if (IP.getPlayer().getName().equalsIgnoreCase(playerName))
					return IP;
			}
				
		}
		Bukkit.getLogger().log(java.util.logging.Level.WARNING, "Creation a la volee d un utilisateur hallow: " + playerName);
		return null;
	}

	/**
	 * Create NCPlayer
	 * 
	 * @param Player
	 */
	public static HallowPlayer getHalowPlayer(Player p) {
		for (HallowPlayer IP : players)
		{
			if (IP.getPlayer() == p)
				return IP;
		}
		Bukkit.getLogger().log(java.util.logging.Level.WARNING, "Creation a la volee d un utilisateur hallow: " + p.getDisplayName());
		return createHallowPlayer(p);
	}
}
