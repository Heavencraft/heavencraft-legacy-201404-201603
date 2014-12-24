package fr.heavencraft.heavenNoel;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import fr.heavencraft.NoelFiles;

public class Racer {
	private String player;
	private boolean alreadyPerformed = false;
	private long starttime;
	private long bestperf;
	
	public Racer(Player p)
	{
		this.player = p.getName();
		if(NoelFiles.getPlayers().getConfigurationSection("Players." + p.getName()) != null)
		{
			// Le joueur existe
			bestperf = NoelFiles.getPlayers().getLong("Players." + p.getPlayer().getName() + ".race");
			setAlreadyPerformed(true);
		}
		else
			setAlreadyPerformed(false);
	} 
	
	public Player getPlayer()
	{
		return Bukkit.getPlayer(player);
	}

	public boolean savePerformanceRecord(long time) {
		if(!alreadyPerformed) {
			NoelFiles.getPlayers().set("Players." + this.player + ".race", time);
			NoelFiles.savePlayers();
			setAlreadyPerformed(true);
			return true;
		}
		else if(time<bestperf)
		{
			NoelFiles.getPlayers().set("Players." + this.player + ".race", time);
			NoelFiles.savePlayers();
			setAlreadyPerformed(true);
			return true;
		}
		return false;
	}

	public boolean isAlreadyPerformed() {
		return alreadyPerformed;
	}

	private void setAlreadyPerformed(boolean alreadyPerformed) {
		this.alreadyPerformed = alreadyPerformed;
	}

	public long getStarttime() {
		return starttime;
	}

	public void setStarttime(long starttime) {
		this.starttime = starttime;
	}
}
