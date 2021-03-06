package fr.heavencraft.aventure.WorldGuardRegions;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

import com.sk89q.worldguard.protection.regions.ProtectedRegion;

/**
*
* @author mewin
*/
public abstract class RegionEvent extends PlayerEvent {

   private static final HandlerList handlerList = new HandlerList();
   
   private ProtectedRegion region;
   private MovementWay movement;
   private Location from;
   
   public RegionEvent(ProtectedRegion region, Player player, MovementWay movement, Location from)
   {
       super(player);
       this.region = region;
       this.movement = movement;
       this.from = from;
   }

   @Override
   public HandlerList getHandlers() {
       return handlerList;
   }
   
   public ProtectedRegion getRegion()
   {
       return region;
   }
   
   public static HandlerList getHandlerList()
   {
       return handlerList;
   }
   
   public MovementWay getMovementWay()
   {
       return this.movement;
   }

   public Location getFrom() { return this.from; }
}