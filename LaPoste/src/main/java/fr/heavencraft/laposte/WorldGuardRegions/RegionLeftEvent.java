package fr.heavencraft.laposte.WorldGuardRegions;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.sk89q.worldguard.protection.regions.ProtectedRegion;

/**
 * event that is triggered after a player left a WorldGuard region
 * @author mewin
 */
public class RegionLeftEvent extends RegionEvent
{
    /**
     * creates a new RegionLeftEvent
     * @param region the region the player has left
     * @param player the player who triggered the event
     * @param movement the type of movement how the player left the region
     * @param from Location the player moved from
     */
    public RegionLeftEvent(ProtectedRegion region, Player player, MovementWay movement, Location from)
    {
        super(region, player, movement, from);
    }
}