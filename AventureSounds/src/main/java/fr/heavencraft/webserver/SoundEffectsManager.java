package fr.heavencraft.webserver;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class SoundEffectsManager {
    public static void playToPlayer(Player p, String data) {
        if (WebsocketSessionManager.getSessionManager().getSessionByName(p.getName()) != null) {
        	
        	String raw = "amb" + ":" + "1.0" + ":"+ data;
            WebsocketServer.s.sendData(WebsocketSessionManager.getSessionManager().getSessionByName(p.getName()), raw);
        }
    }
 
    public static void playToAll(String data) {
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (WebsocketSessionManager.getSessionManager().getSessionByName(p.getName()) != null) {
                WebsocketServer.s.sendData(WebsocketSessionManager.getSessionManager().getSessionByName(p.getName()), data);
            }
        }
    }
    
    
    public static void stopAmbToPlayer(Player p) {
        if (WebsocketSessionManager.getSessionManager().getSessionByName(p.getName()) != null) {      	
            WebsocketServer.s.sendData(WebsocketSessionManager.getSessionManager().getSessionByName(p.getName()), "stopamb");
        }
    }
}