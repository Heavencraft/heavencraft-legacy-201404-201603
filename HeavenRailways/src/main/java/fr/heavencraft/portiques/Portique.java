package fr.heavencraft.portiques;

import java.util.Iterator;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import fr.heavencraft.railways.HeavenRailway;

public class Portique
{
	private Location location;
	private Material material;
	private BlockFace openedFrom;
	private BukkitTask task = null;

	public Location getLocation()
	{
		return location;
	}

	public void setLocation(Location location)
	{
		this.location = location;
	}

	public Material getMaterial()
	{
		return material;
	}

	public void setMaterial(Material material)
	{
		this.material = material;
	}

	public Portique(Location loc, Material mat)
	{
		setMaterial(mat);
		setLocation(loc);
	}

	@SuppressWarnings("deprecation")
	public void openPortique(Location openedFromLocation)
	{
		Block gateBlock = getLocation().getBlock();

		setOpenedFrom(openedFromLocation.getBlock());

		setMaterial(gateBlock.getType());

		task = Bukkit
				.getServer()
				.getScheduler()
				.runTaskLater(HeavenRailway.getInstance(), new portiqueGateBlockTask(gateBlock.getType()),
						20 * 5);

		switch (getMaterial())
		{
			case TRAP_DOOR:
			case FENCE_GATE: // Open FenceGate/TrapDoor
				// Open the gate if it is closed
				gateBlock.setData((byte) (gateBlock.getState().getRawData() | 4));
				break;

			default:
				gateBlock.setType(Material.AIR);
				break;
		}
		PortiqueManager.portiquesOuverts.add(this);
	}

	@SuppressWarnings("deprecation")
	public void closePortique()
	{
		
		if(task == null)
			return;
		task.cancel();

		switch (getMaterial())
		{
			case TRAP_DOOR:
			case FENCE_GATE: // Open FenceGate/TrapDoor
				// Open the gate if it is closed
				getLocation().getBlock().setData(
						(byte) (getLocation().getBlock().getState().getRawData() | 4));
				break;

			default:
				location.getBlock().setType(getMaterial());
				break;
		}      
		PortiqueManager.portiquesOuverts.remove(this);
	}

	/**
	 * Checks if it is the portique
	 * 
	 * @param loc
	 * @return
	 */
	public boolean isPortique(Location loc)
	{
		if (loc.getWorld() == getLocation().getWorld() && loc.getBlockX() == getLocation().getBlockX()
				&& loc.getBlockY() == getLocation().getBlockY()
				&& loc.getBlockZ() == getLocation().getBlockZ())
			return true;
		return false;
	}

	/**
	 * Verifie si un joueur entre dans le sens inverse
	 * 
	 * @param from Le block depuis lequel le joueur entre
	 * @return false Si le joueur entre dans le sens inverse
	 */
	public boolean checkEnterDirection(Block from)
	{
		switch (openedFrom)
		{
			case NORTH:
				return from.getZ() < getLocation().getZ();
			case SOUTH:
				return from.getZ() > getLocation().getZ();
			case WEST:
				return from.getX() < getLocation().getX();
			case EAST:
				return from.getX() > getLocation().getX();
			default:
				return true;
		}
	}

	/**
	 * Definie le sens duquel a ete ouvert le portique
	 * 
	 * @param block Le block depuis lequel le joueur entre
	 */
	private void setOpenedFrom(Block block)
	{
		// Determine the type of the Block to find out the direction opened from
		if (block.getZ() < getLocation().getZ())
		{
			openedFrom = BlockFace.NORTH;
		}
		else if (block.getZ() > getLocation().getZ())
		{
			openedFrom = BlockFace.SOUTH;
		}
		else if (block.getX() < getLocation().getX())
		{
			openedFrom = BlockFace.WEST;
		}
		else if (block.getX() > getLocation().getX())
		{
			openedFrom = BlockFace.EAST;
		}
		else
		{
			openedFrom = BlockFace.SELF;
		}
	}

	class portiqueGateBlockTask extends BukkitRunnable
	{
		public portiqueGateBlockTask(Material type)
		{
		}

		@Override
		public void run()
		{
			closePortique();
		}
	}

}
