package fr.heavencraft.rpg.zones;

import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Spider;
import org.bukkit.entity.Zombie;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;

import com.sk89q.worldedit.bukkit.selections.CuboidSelection;

import fr.heavencraft.Utils.DevUtils;
import fr.heavencraft.rpg.RPGFiles;
import fr.heavencraft.rpg.mobs.MobAttribute;

public class Zone {

	private String _UniqueName;
	private String _name;
	private int _zoneLevel;
	private CuboidSelection _cubo;

	private MobAttribute zombieAttribute;
	private MobAttribute creeperAttribute;
	private MobAttribute skeletonAttribute;
	private MobAttribute spiderAttribute;

	/**
	 * Constructeur, pour charger une zone depuis la config
	 * @param name
	 */
	public Zone(String name)
	{
		this.set_name(name);
		this._UniqueName = name;
		this.set_zoneLevel(RPGFiles.getZones().getInt("Zones." + this._UniqueName + ".level"));

		// Charger les attributs

		this.setZombieAttribute(new MobAttribute(this._UniqueName, 
				RPGFiles.getZones().getBoolean("Zones." + this._UniqueName + ".attributes.zombie.enabled"),
				"zombie",
				RPGFiles.getZones().getString("Zones." + this._UniqueName + ".attributes.zombie.customname"),
				RPGFiles.getZones().getDouble("Zones." + this._UniqueName + ".attributes.zombie.damage"),
				RPGFiles.getZones().getDouble("Zones." + this._UniqueName + ".attributes.zombie.life"),
				RPGFiles.getZones().getItemStack("Zones." + this._UniqueName + ".attributes.zombie.head"),
				RPGFiles.getZones().getItemStack("Zones." + this._UniqueName + ".attributes.zombie.chestplate"),
				RPGFiles.getZones().getItemStack("Zones." + this._UniqueName + ".attributes.zombie.leggings"),
				RPGFiles.getZones().getItemStack("Zones." + this._UniqueName + ".attributes.zombie.boots")));

		this.setCreeperAttribute(new MobAttribute(this._UniqueName,
				RPGFiles.getZones().getBoolean("Zones." + this._UniqueName + ".attributes.creeper.enabled"),
				"creeper",
				RPGFiles.getZones().getString("Zones." + this._UniqueName + ".attributes.creeper.customname"),
				RPGFiles.getZones().getDouble("Zones." + this._UniqueName + ".attributes.creeper.damage"),
				RPGFiles.getZones().getDouble("Zones." + this._UniqueName + ".attributes.creeper.life"),
				RPGFiles.getZones().getItemStack("Zones." + this._UniqueName + ".attributes.creeper.head"),
				RPGFiles.getZones().getItemStack("Zones." + this._UniqueName + ".attributes.creeper.chestplate"),
				RPGFiles.getZones().getItemStack("Zones." + this._UniqueName + ".attributes.creeper.leggings"),
				RPGFiles.getZones().getItemStack("Zones." + this._UniqueName + ".attributes.creeper.boots")));

		this.setSkeletonAttribute(new MobAttribute(this._UniqueName, 
				RPGFiles.getZones().getBoolean("Zones." + this._UniqueName + ".attributes.skeleton.enabled"),
				"skeleton",
				RPGFiles.getZones().getString("Zones." + this._UniqueName + ".attributes.skeleton.customname"),
				RPGFiles.getZones().getDouble("Zones." + this._UniqueName + ".attributes.skeleton.damage"),
				RPGFiles.getZones().getDouble("Zones." + this._UniqueName + ".attributes.skeleton.life"),
				RPGFiles.getZones().getItemStack("Zones." + this._UniqueName + ".attributes.skeleton.head"),
				RPGFiles.getZones().getItemStack("Zones." + this._UniqueName + ".attributes.skeleton.chestplate"),
				RPGFiles.getZones().getItemStack("Zones." + this._UniqueName + ".attributes.skeleton.leggings"),
				RPGFiles.getZones().getItemStack("Zones." + this._UniqueName + ".attributes.skeleton.boots")));

		this.setSpiderAttribute(new MobAttribute(this._UniqueName,
				RPGFiles.getZones().getBoolean("Zones." + this._UniqueName + ".attributes.spider.enabled"),
				"spider",
				RPGFiles.getZones().getString("Zones." + this._UniqueName + ".attributes.spider.customname"),
				RPGFiles.getZones().getDouble("Zones." + this._UniqueName + ".attributes.spider.damage"),
				RPGFiles.getZones().getDouble("Zones." + this._UniqueName + ".attributes.spider.life"), 
				RPGFiles.getZones().getItemStack("Zones." + this._UniqueName + ".attributes.spider.head"),
				RPGFiles.getZones().getItemStack("Zones." + this._UniqueName + ".attributes.spider.chestplate"),
				RPGFiles.getZones().getItemStack("Zones." + this._UniqueName + ".attributes.spider.leggings"),
				RPGFiles.getZones().getItemStack("Zones." + this._UniqueName + ".attributes.spider.boots")));

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

		genMobConfig("zombie");
		genMobConfig("creeper");
		genMobConfig("skeleton");
		genMobConfig("spider");
		RPGFiles.saveZones();
	}

	private void genMobConfig(String mob)
	{
		RPGFiles.getZones().set("Zones." + this._UniqueName + ".attributes." + mob + ".enabled", false);
		RPGFiles.getZones().set("Zones." + this._UniqueName + ".attributes." + mob + ".customname", "");
		RPGFiles.getZones().set("Zones." + this._UniqueName + ".attributes." + mob + ".damage", 0);
		RPGFiles.getZones().set("Zones." + this._UniqueName + ".attributes." + mob + ".life", 0);
		RPGFiles.getZones().set("Zones." + this._UniqueName + ".attributes." + mob + ".head", "");
		RPGFiles.getZones().set("Zones." + this._UniqueName + ".attributes." + mob + ".chestplate", "");
		RPGFiles.getZones().set("Zones." + this._UniqueName + ".attributes." + mob + ".leggings", "");
		RPGFiles.getZones().set("Zones." + this._UniqueName + ".attributes." + mob + ".boots", "");
	}


	public String getUniqueName() {
		return _UniqueName;
	}

	public String getName() {
		return get_name();
	}

	public void setName(String _name) {
		RPGFiles.getZones().set("Zones." + this._UniqueName + ".name", _name);
		this.set_name(_name);
		RPGFiles.saveZones();
	}

	public int getZoneLevel() {
		return get_zoneLevel();
	}
	public void setZoneLevel(int level) {
		RPGFiles.getZones().set("Zones." + this._UniqueName + ".level", level);
		this.set_zoneLevel(level);
		RPGFiles.saveZones();
	}

	public CuboidSelection getCubo() {
		return get_cubo();
	}

	public void setCubo(CuboidSelection _cubo) {
		this.set_cubo(_cubo);
		RPGFiles.getZones().set("Zones." + this._UniqueName + ".l1",  DevUtils.serializeLoc(_cubo.getMinimumPoint()));
		RPGFiles.getZones().set("Zones." + this._UniqueName + ".l2", DevUtils.serializeLoc(_cubo.getMaximumPoint()));
		RPGFiles.saveZones();
	}

	public int get_zoneLevel() {
		return _zoneLevel;
	}

	public void set_zoneLevel(int _zoneLevel) {
		this._zoneLevel = _zoneLevel;
	}

	public String get_name() {
		return _name;
	}

	public void set_name(String _name) {
		this._name = _name;
	}

	public CuboidSelection get_cubo() {
		return _cubo;
	}

	public void set_cubo(CuboidSelection _cubo) {
		this._cubo = _cubo;
	}



	/**
	 * Applique les atributs a un mob
	 * @param entity
	 */
	public void applyEquipment(LivingEntity entity)
	{
		switch(entity.getType())
		{
		case CREEPER:
			if(getCreeperAttribute() == null || getCreeperAttribute().getEnabled() == false)
				return;

			if(getCreeperAttribute().getLife() != 0)
				entity.setMaxHealth(getCreeperAttribute().getLife());
			entity.setHealth(entity.getMaxHealth());

			Creeper creep = (Creeper) entity;
			EntityEquipment eeCR = creep.getEquipment();
			try {eeCR.setHelmet(getCreeperAttribute().getHead());}
			catch (Exception e){Bukkit.broadcastMessage("ERROR: zone: " + get_name() + " attributeError: CREEPER " + "HELMET INVALID");}
			try {eeCR.setChestplate(getCreeperAttribute().getChest());}
			catch (Exception e){Bukkit.broadcastMessage("ERROR: zone: " + get_name() + " attributeError: CREEPER " + "CHEST INVALID");}
			try {eeCR.setLeggings(getCreeperAttribute().getLeggings());}
			catch (Exception e){Bukkit.broadcastMessage("ERROR: zone: " + get_name() + " attributeError: CREEPER " + "LEGGINGS INVALID");}
			try {eeCR.setBoots(getCreeperAttribute().getBoots());}
			catch (Exception e){Bukkit.broadcastMessage("ERROR: zone: " + get_name() + " attributeError: CREEPER " + "BOOTS INVALID");}


			break;
		case SKELETON:
			if(getSkeletonAttribute() == null || getSkeletonAttribute().getEnabled() == false)
				return;

			if(getSkeletonAttribute().getLife() != 0)
				entity.setMaxHealth(getSkeletonAttribute().getLife());
			entity.setHealth(entity.getMaxHealth());

			Skeleton skel = (Skeleton) entity;
			EntityEquipment eeSk = skel.getEquipment();
			try {eeSk.setHelmet(getSkeletonAttribute().getHead());}
			catch (Exception e){Bukkit.broadcastMessage("ERROR: zone: " + get_name() + " attributeError: SKELETON " + "HELMET INVALID");}
			try {eeSk.setChestplate(getSkeletonAttribute().getChest());}
			catch (Exception e){Bukkit.broadcastMessage("ERROR: zone: " + get_name() + " attributeError: SKELETON " + "CHEST INVALID");}
			try {eeSk.setLeggings(getSkeletonAttribute().getLeggings());}
			catch (Exception e){Bukkit.broadcastMessage("ERROR: zone: " + get_name() + " attributeError: SKELETON " + "LEGGINGS INVALID");}
			try {eeSk.setBoots(getSkeletonAttribute().getBoots());}
			catch (Exception e){Bukkit.broadcastMessage("ERROR: zone: " + get_name() + " attributeError: SKELETON " + "BOOTS INVALID");}

			break;
		case SPIDER:
			if(getSpiderAttribute() == null || getSpiderAttribute().getEnabled() == false)
				return;
			if(getSpiderAttribute().getLife() != 0)
				entity.setMaxHealth(getSpiderAttribute().getLife());
			entity.setHealth(entity.getMaxHealth());

			Spider spi = (Spider) entity;
			EntityEquipment eeSp = spi.getEquipment();
			try {eeSp.setHelmet(getSpiderAttribute().getHead());}
			catch (Exception e){Bukkit.broadcastMessage("ERROR: zone: " + get_name() + " attributeError: SPIDER " + "HELMET INVALID");}
			try {eeSp.setChestplate(getSpiderAttribute().getChest());}
			catch (Exception e){Bukkit.broadcastMessage("ERROR: zone: " + get_name() + " attributeError: SPIDER " + "CHEST INVALID");}
			try {eeSp.setLeggings(getSpiderAttribute().getLeggings());}
			catch (Exception e){Bukkit.broadcastMessage("ERROR: zone: " + get_name() + " attributeError: SPIDER " + "LEGGINGS INVALID");}
			try {eeSp.setBoots(getSpiderAttribute().getBoots());}
			catch (Exception e){Bukkit.broadcastMessage("ERROR: zone: " + get_name() + " attributeError: SPIDER " + "BOOTS INVALID");}

			break;
		case ZOMBIE:
			if(getZombieAttribute() == null || getZombieAttribute().getEnabled() == false)
				return;
			if(getZombieAttribute().getLife() != 0)
				entity.setMaxHealth(getZombieAttribute().getLife());
			entity.setHealth(entity.getMaxHealth());

			Zombie zomb = (Zombie) entity;
			EntityEquipment eeZo = zomb.getEquipment();
			try {eeZo.setHelmet(getZombieAttribute().getHead());}
			catch (Exception e){Bukkit.broadcastMessage("ERROR: zone: " + get_name() + " attributeError: ZOMBIE " + "HELMET INVALID");}
			try {eeZo.setChestplate(getZombieAttribute().getChest());}
			catch (Exception e){Bukkit.broadcastMessage("ERROR: zone: " + get_name() + " attributeError: ZOMBIE " + "CHEST INVALID");}
			try {eeZo.setLeggings(getZombieAttribute().getLeggings());}
			catch (Exception e){Bukkit.broadcastMessage("ERROR: zone: " + get_name() + " attributeError: ZOMBIE " + "LEGGINGS INVALID");}
			try {eeZo.setBoots(getZombieAttribute().getBoots());}
			catch (Exception e){Bukkit.broadcastMessage("ERROR: zone: " + get_name() + " attributeError: ZOMBIE " + "BOOTS INVALID");}

			break;
		default:
			break;

		}
	}

	public double getMobDamage(LivingEntity l)
	{
		switch(l.getType())
		{
		case CREEPER:
			if(getCreeperAttribute() == null || getCreeperAttribute().getEnabled() == false)
				return 0;
			return getCreeperAttribute().getDamage();
		case SKELETON:
			if(getSkeletonAttribute() == null || getSkeletonAttribute().getEnabled() == false)
				return 0;
			return getSkeletonAttribute().getDamage();
		case SPIDER:
			if(getSpiderAttribute() == null || getSpiderAttribute().getEnabled() == false)
				return 0;
			return getSpiderAttribute().getDamage();
		case ZOMBIE:
			if(getZombieAttribute() == null|| getZombieAttribute().getEnabled() == false)
				return 0;
			return getZombieAttribute().getDamage();
		default:
			return 0;
		}
	}

	public String getMobCustomName(LivingEntity l)
	{
		switch(l.getType())
		{
		case CREEPER:
			if(getCreeperAttribute() == null)
				return null;
			return getCreeperAttribute().getCustomName();
		case SKELETON:
			if(getSkeletonAttribute() == null)
				return null;
			return getSkeletonAttribute().getCustomName();
		case SPIDER:
			if(getSpiderAttribute() == null)
				return null;
			return getSpiderAttribute().getCustomName();
		case ZOMBIE:
			if(getZombieAttribute() == null)
				return null;
			return getZombieAttribute().getCustomName();
		default:
			return null;
		}
	}

	public MobAttribute getZombieAttribute() {
		return zombieAttribute;
	}



	private void setZombieAttribute(MobAttribute zombieAttribute) {
		this.zombieAttribute = zombieAttribute;
	}

	public MobAttribute getCreeperAttribute() {
		return creeperAttribute;
	}

	private void setCreeperAttribute(MobAttribute creeperAttribute) {
		this.creeperAttribute = creeperAttribute;
	}

	public MobAttribute getSkeletonAttribute() {
		return skeletonAttribute;
	}

	private void setSkeletonAttribute(MobAttribute skeletonAttribute) {
		this.skeletonAttribute = skeletonAttribute;
	}

	public MobAttribute getSpiderAttribute() {
		return spiderAttribute;
	}

	private void setSpiderAttribute(MobAttribute spiderAttribute) {
		this.spiderAttribute = spiderAttribute;
	}
}