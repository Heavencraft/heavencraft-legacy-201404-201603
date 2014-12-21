package fr.heavencraft.heavenNoel;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import fr.heavencraft.NoelFiles;

public class Racer {
	private String player;
	private boolean alreadyPerformed = false;
	private long starttime;
	
	public Racer(Player p)
	{
		this.player = p.getName();
		if(NoelFiles.getPlayers().getConfigurationSection("Players." + p.getName()) != null)
		{
			// Le joueur existe
			int time = NoelFiles.getPlayers().getInt("Players." + p.getPlayer().getName() + ".race");
			setAlreadyPerformed(true);
		}
		else
			setAlreadyPerformed(false);
	} 
	
	public Player getPlayer()
	{
		return Bukkit.getPlayer(player);
	}

	public void setPerformanceRecord(int time) {
		NoelFiles.getPlayers().set("Players." + this.player + ".race", time);
		NoelFiles.savePlayers();
		setAlreadyPerformed(true);
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
