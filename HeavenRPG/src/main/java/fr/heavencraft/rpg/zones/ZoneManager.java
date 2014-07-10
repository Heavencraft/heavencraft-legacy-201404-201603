package fr.heavencraft.rpg.zones;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Location;

import com.sk89q.worldedit.bukkit.selections.CuboidSelection;

import fr.heavencraft.rpg.RPGFiles;
import fr.heavencraft.rpg.ZoneUtils;

public class ZoneManager {
	public static class Zone {

		private String _UniqueName;
		private String _name;
		private int _zoneLevel;
		private CuboidSelection _cubo;
		
		/**
		 * Constructeur, pour charger une zone depuis la config
		 * @param name
		 */
		public Zone(String name)
		{
			this._name = name;
			this._UniqueName = name;
			this._zoneLevel = RPGFiles.getZones().getInt("Zones." + this._UniqueName + ".level");
		}
		
		/**
		 * Constructeur pour créer une zone dans la config.
		 * @param name
		 * @param level
		 * @param cubo
		 */
		public Zone(String name, int level, CuboidSelection cubo)
		{
			this._UniqueName = name;
			this.setName(name);
			this.setZoneLevel(level);
			this.setCubo(cubo);
		}

		public String getUniqueName() {
			return _UniqueName;
		}
		
		public String getName() {
			return _name;
		}
		
		public void setName(String _name) {
			RPGFiles.getZones().set("Zones." + this._UniqueName + ".name", _name);
			this._name = _name;
			RPGFiles.saveZones();
		}

		public int getZoneLevel() {
			return _zoneLevel;
		}
		public void setZoneLevel(int level) {
			RPGFiles.getZones().set("Zones." + this._UniqueName + ".level", level);
			this._zoneLevel = level;
			RPGFiles.saveZones();
		}

		public CuboidSelection getCubo() {
			return _cubo;
		}
		
		public void setCubo(CuboidSelection _cubo) {
			this._cubo = _cubo;
			RPGFiles.getZones().set("Zones." + this._UniqueName + ".l1",  ZoneUtils.serializeLoc(_cubo.getMinimumPoint()));
			RPGFiles.getZones().set("Zones." + this._UniqueName + ".l2", ZoneUtils.serializeLoc(_cubo.getMaximumPoint()));
			RPGFiles.saveZones();
		}	
	}
		
	private static ArrayList<Zone> zones = new ArrayList<Zone>();
	
	public static void loadAllZones()
	{
		zones.clear();
		if(RPGFiles.getZones().getConfigurationSection("Zones") != null)
			for(String a : RPGFiles.getZones().getConfigurationSection("Zones").getKeys(false))
			{
				Zone z = new Zone(a);
				z._zoneLevel = RPGFiles.getZones().getInt("Zones." + a + ".level");
				z._name = RPGFiles.getZones().getString("Zones." + a + ".name");			
				Location l1 = ZoneUtils.deserializeLoc( RPGFiles.getZones().getString("Zones." + a + ".loc1"));
				Location l2 = ZoneUtils.deserializeLoc( RPGFiles.getZones().getString("Zones." + a + ".loc2"));
				z._cubo = new CuboidSelection(l1.getWorld(), l1, l2);
				addZone(z);
				Bukkit.broadcastMessage(z.getName());
			}
	}
	
	/**
	 * Crée une nouvelle zone.
	 * @param name
	 * @param level
	 * @param s
	 */
	public static void createZone(String name, int level, CuboidSelection s)
	{
		Zone z = new Zone(name, level, s);
		if(zones.contains(z))
			return;
		zones.add(z);
		
	}
	/**
	 * Ajoute une zone.
	 * @param z
	 */
	public static void addZone(Zone z)
	{
		if(!zones.contains(z))
			zones.add(z);
	}
	
	/**
	 * Supprime une zone.
	 * @param z
	 */
	public static void removeZone(Zone z)
	{
		//TODO Supprimer des configs
		if(zones.contains(z))
			zones.remove(z);
	}
	
	/**
	 * Retourne la zone dans laquelle se trouve la position. (Peut etre null)
	 * @param l
	 * @return
	 */
	public static Zone getZone(Location l)
	{
		for(Zone z : zones)
		{
			if(z.getCubo().contains(l))
				return z;
		}
		return null;
	}
	/**
	 * Retourne la zone avec ce nom. (Peut etre null)
	 * @param name
	 * @return
	 */
	public static Zone getZone(String name)
	{
		for(Zone z : zones)
		{
			if(z.getName().equalsIgnoreCase(name))
				return z;
		}
		return null;
	}
}
