package fr.heavencraft.rpg.mobs;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import fr.heavencraft.rpg.RPGFiles;

public class MobAttribute {

	/**
	 * 
	 * @param z_name Nom de la zone
	 * @param isEnabled Si les attributs sont activés
	 * @param mob le mob: zombie/skeleton
	 * @param cstName Nom
	 * @param dmg Dégats
	 * @param lif Vie
	 * @param he Tête
	 * @param ch Corps
	 * @param leg Pentalon
	 * @param bo Chaussures
	 */
	public MobAttribute(String z_name, boolean isEnabled ,String mob, String cstName, double dmg, double lif, ItemStack he, ItemStack ch, ItemStack leg, ItemStack bo)
	{
		zoneName = z_name;
		enabled = isEnabled;
		mobType = mob;
		customName = cstName;
		damage = dmg;
		life = lif;
		head = he;
		chest = ch;
		leggings = leg;
		boots = bo;
	}
	private boolean enabled;

	private String zoneName;

	private String mobType;

	private String customName;

	private double damage;

	private double life;

	private ItemStack head;

	private ItemStack chest;

	private ItemStack leggings;

	private ItemStack boots;

	public double getDamage() {
		return damage;
	}

	public void setDamage(double damage) {
		this.damage = damage;
		RPGFiles.getZones().set("Zones." + zoneName + ".attributes." + mobType + ".damage", damage);
		RPGFiles.saveZones();

	}

	public double getLife() {
		return this.life;
	}

	public  void setLife(double life) {
		this.life = life;
		RPGFiles.getZones().set("Zones." + zoneName + ".attributes." + mobType + ".life", life);
		RPGFiles.saveZones();

	}

	public  ItemStack getHead() {
		return this.head;
	}

	public  void setHead(ItemStack head) {
		if(head == null)
			this.head = new ItemStack(Material.AIR, 1);
		else
			this.head = head;

		RPGFiles.getZones().set("Zones." + zoneName + ".attributes." + mobType + ".head", this.head);
		RPGFiles.saveZones();
	}

	public  ItemStack getChest() {
		return this.chest;
	}

	public  void setChest(ItemStack chest) {
		if(chest == null)
			this.chest = new ItemStack(Material.AIR, 1);
		else
			this.chest = chest;

		RPGFiles.getZones().set("Zones." + zoneName + ".attributes." + mobType + ".chestplate", this.chest);
		RPGFiles.saveZones();
	}

	public  ItemStack getLeggings() {
		return this.leggings;
	}

	public  void setLeggings(ItemStack leggings) {
		if(leggings == null)
			this.leggings = new ItemStack(Material.AIR, 1);
		else
			this.leggings = leggings;
		
		RPGFiles.getZones().set("Zones." + zoneName + ".attributes." + mobType + ".leggings", this.leggings);
		RPGFiles.saveZones();
	}

	public  ItemStack getBoots() {
		return this.boots;
	}

	public  void setBoots(ItemStack boots) {
		if(boots == null)
			this.boots = new ItemStack(Material.AIR, 1);
		else
			this.boots = boots;

		RPGFiles.getZones().set("Zones." + zoneName + ".attributes." + mobType + ".boots", this.boots);
		RPGFiles.saveZones();
	}

	public  String getCustomName() {
		return this.customName;
	}

	public  void setCustomName(String customName) {
		this.customName = customName;
		RPGFiles.getZones().set("Zones." + zoneName + ".attributes." + mobType + ".customname", this.customName);
		RPGFiles.saveZones();
	}

	public boolean getEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
		RPGFiles.getZones().set("Zones." + zoneName + ".attributes." + mobType + ".enabled", enabled);
		RPGFiles.saveZones();
	}

}
