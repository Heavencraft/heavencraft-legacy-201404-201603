package fr.heavencraft.heavenhallow.player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import fr.heavencraft.HallowFiles;
import fr.heavencraft.HeavenHallow;
import fr.heavencraft.heavenhallow.levels.Level;

public class HallowPlayer {
	private String player;
	private Level level;
	
	public HallowPlayer(Player p)
	{
		this.player = p.getName();
		if(HallowFiles.getPlayers().getConfigurationSection("Players." + p.getName()) != null)
		{
			// Le joueur existe
			String playerStage = HallowFiles.getPlayers().getString("Players." + p.getPlayer().getName() + ".stage");
			
			if(playerStage.equalsIgnoreCase(Level.LOBBY.getStageName()))
				level = Level.LOBBY;
			else if(playerStage.equalsIgnoreCase(Level.THEBEGIN.getStageName()))
				level = Level.THEBEGIN;
			else if(playerStage.equalsIgnoreCase(Level.THEFOREST.getStageName()))
				level = Level.THEFOREST;
			else if(playerStage.equalsIgnoreCase(Level.THETIGER.getStageName()))
				level = Level.THETIGER;
			else if(playerStage.equalsIgnoreCase(Level.THEMOON.getStageName()))
				level = Level.THEMOON;
			else if(playerStage.equalsIgnoreCase(Level.THEFALL.getStageName()))
				level = Level.THEFALL;
			else
				level = Level.LOBBY;
			
			//Teleporter le joueur au début du stage.
			p.teleport(level.getSpawnLoc());
		}
		else
		{
			//Creer le joueur
			HallowFiles.getPlayers().set("Players." + p.getName() + ".stage", Level.THEBEGIN.getStageName());
			HallowFiles.getPlayers().set("Players." + p.getName() + ".finished", false);
			HallowFiles.savePlayers();
			level = Level.THEBEGIN;
			p.teleport(level.getSpawnLoc());
		}
		
	} 
	
	public Player getPlayer()
	{
		return Bukkit.getPlayer(player);
	}

	public Level getLevel() {
		return level;
	}

	public void setLevel(Level level) {
		HallowFiles.getPlayers().set("Players." + this.player + ".stage", level.getStageName());
		HallowFiles.savePlayers();
		this.level = level;
	}

	public boolean HasFinished() {
		PreparedStatement ps;
		try {
			ps = HeavenHallow.getConnection().prepareStatement("SELECT COUNT(`player`) AS count FROM classement WHERE `player` = ?");
			ps.setString(1, this.player);
			ResultSet rs = ps.executeQuery();
			while (rs.next())
				if(rs.getInt("count") >= 1)
					return true;
				else
					return false;
			return false;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		
	}	
}
