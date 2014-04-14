package fr.heavencraft.NavalConflicts.Handlers;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

import fr.heavencraft.NavalConflicts.GameMechanics.Settings;
import fr.heavencraft.NavalConflicts.Handlers.Player.Team;
import fr.heavencraft.NavalConflicts.Handlers.Potions.PotionHandler;
import fr.heavencraft.NavalConflicts.Tools.Files;

public class NCClassManager {

	private static ArrayList<NCClass> allClasses = new ArrayList<NCClass>();
	private static NCClass defaultClass;

	public static void addClass(String name, Team team, ItemStack helmet,
			ItemStack chestplate, ItemStack leggings, ItemStack boots,
			ArrayList<ItemStack> items, ArrayList<PotionEffect> effects,
			ArrayList<PotionEffect> transfereffects,
			HashMap<Integer, ItemStack> killstreaks, String disguise,
			ItemStack icon) {
		NCClass IC = new NCClass(name, team, helmet, chestplate, leggings,
				boots, items, effects, transfereffects, killstreaks, disguise,
				icon);
		allClasses.add(IC);

	}

	/**
	 * 
	 * 
	 * @param team
	 * @return All the classes loaded for that team
	 */
	public static ArrayList<NCClass> getClasses(Team team) {

			return allClasses;
	}

	/**
	 * 
	 * @param team
	 * @param IC
	 *            - The NCClass
	 * @return If the class is loaded
	 */
	public static boolean isRegistered( NCClass IC) {
			return allClasses.contains(IC);
	}

	/**
	 * Adds a NCClass to the classes team
	 * 
	 * @param IC
	 *            - The NCClass
	 */
	public static void addClass(NCClass IC) {
		allClasses.add(IC);
	}

	/**
	 * 
	 * @param team
	 * @param className
	 * @return The NCClass
	 */
	public static NCClass getClass(Team team, String className) {
		
			for (NCClass IC : allClasses) {
				if (IC.getName().equalsIgnoreCase(className))
					return IC;
			}

		return null;
	}

	/**
	 * Unloads the class
	 * 
	 * @param IC
	 *            - The NCClass
	 */
	public static void removeClass(NCClass IC) {
		
			allClasses.remove(IC);
	}

	/**
	 * Unloads the class
	 * 
	 * @param team
	 * @param className
	 */
	public static void removeClass(String className) {
		for (NCClass IC : allClasses) {
			if (IC.getName().equalsIgnoreCase(className))
				allClasses.remove(IC);
		}
	}

	/**
	 * 
	 * @param team
	 * @return The default class for that team
	 */
	public static NCClass getDefaultClass() {
		return defaultClass;
	}

	/**
	 * Load the default classes from the config.yml
	 */
	public static void loadDefaultClasses() {
		defaultClass = getClass(
				Team.Blue,
				Files.getConfig().getString(
						"Settings.Global.Default Classes.Human"));
		if (defaultClass == null) {
			defaultClass = getClasses(Team.Blue).get(0);
			Files.getConfig().set("Settings.Global.Default Classes.Human",
					defaultClass.getName());
			Files.saveConfig();
			if (Settings.logClassesEnabled())
				System.out.println("Invalid default human class. Changed to: "
						+ defaultClass.getName());
		}
	}

	/**
	 * Load all the classes from the Classes.yml
	 */
	public static void loadConfigClasses() {
		allClasses.clear();
		for (String s : Files.getClasses().getConfigurationSection("Classes.Human").getKeys(true)) {
			String name = "0";
			String helmet = "0";
			String chestplate = "0";
			String leggings = "0";
			String boots = "0";
			String icon = "0";
			ArrayList<ItemStack> items = new ArrayList<ItemStack>();
			String disguise = null;
			ArrayList<PotionEffect> effects = new ArrayList<PotionEffect>();
			ArrayList<PotionEffect> transfereffects = new ArrayList<PotionEffect>();
			HashMap<Integer, ItemStack> killstreaks = new HashMap<Integer, ItemStack>();
			if (!s.contains(".")) {
				name = s;
				helmet = Files.getClasses().getString(
						"Classes.Human." + s + ".Helmet");
				chestplate = Files.getClasses().getString(
						"Classes.Human." + s + ".Chestplate");
				leggings = Files.getClasses().getString(
						"Classes.Human." + s + ".Leggings");
				boots = Files.getClasses().getString(
						"Classes.Human." + s + ".Boots");
				icon = Files.getClasses().getString(
						"Classes.Human." + s + ".Icon");
				items = ItemHandler.getItemStackList(Files.getClasses()
						.getStringList("Classes.Human." + s + ".Items"));
				disguise = Files.getClasses().getString(
						"Classes.Human." + s + ".Disguise");
				effects = PotionHandler
						.getPotions(Files.getClasses().getStringList(
								"Classes.Human." + s + ".Potion Effects"));
				transfereffects = PotionHandler.getPotions(Files.getClasses()
						.getStringList(
								"Classes.Human." + s
										+ ".Transfer Potion Effects"));
				killstreaks = ItemHandler.getItemHashMap(Files.getClasses(),
						"Classes.Human." + s + ".KillStreaks");

				NCClass IC = new NCClass(name, Team.Blue,
						ItemHandler.getItemStack(helmet),
						ItemHandler.getItemStack(chestplate),
						ItemHandler.getItemStack(leggings),
						ItemHandler.getItemStack(boots), items, effects,
						transfereffects, killstreaks, disguise,
						ItemHandler.getItemStack(icon));

				if (Settings.logClassesEnabled())
					System.out.println("Loaded Human Class: " + IC.getName());
				if (!isRegistered(IC))
					addClass(IC);
			}
		}
		for (String s : Files.getClasses()
				.getConfigurationSection("Classes.Zombie").getKeys(true)) {
			String name = "0";
			String helmet = "0";
			String chestplate = "0";
			String leggings = "0";
			String boots = "0";
			String icon = "0";
			ArrayList<ItemStack> items = new ArrayList<ItemStack>();
			String disguise = null;
			ArrayList<PotionEffect> effects = new ArrayList<PotionEffect>();
			ArrayList<PotionEffect> transfereffects = new ArrayList<PotionEffect>();
			HashMap<Integer, ItemStack> killstreaks = new HashMap<Integer, ItemStack>();
			if (!s.contains(".")) {
				name = s;
				helmet = Files.getClasses().getString(
						"Classes.Zombie." + s + ".Helmet");
				chestplate = Files.getClasses().getString(
						"Classes.Zombie." + s + ".Chestplate");
				leggings = Files.getClasses().getString(
						"Classes.Zombie." + s + ".Leggings");
				boots = Files.getClasses().getString(
						"Classes.Zombie." + s + ".Boots");
				icon = Files.getClasses().getString(
						"Classes.Zombie." + s + ".Icon");
				items = ItemHandler.getItemStackList(Files.getClasses()
						.getStringList("Classes.Zombie." + s + ".Items"));
				disguise = Files.getClasses().getString(
						"Classes.Zombie." + s + ".Disguise");
				effects = PotionHandler.getPotions(Files.getClasses()
						.getStringList(
								"Classes.Zombie." + s + ".Potion Effects"));
				transfereffects = PotionHandler.getPotions(Files.getClasses()
						.getStringList(
								"Classes.Zombie." + s
										+ ".Transfer Potion Effects"));
				killstreaks = ItemHandler.getItemHashMap(Files.getClasses(),
						"Classes.Zombie." + s + ".KillStreaks");

				NCClass IC = new NCClass(name, Team.Red,
						ItemHandler.getItemStack(helmet),
						ItemHandler.getItemStack(chestplate),
						ItemHandler.getItemStack(leggings),
						ItemHandler.getItemStack(boots), items, effects,
						transfereffects, killstreaks, disguise,
						ItemHandler.getItemStack(icon));

				if (Settings.logClassesEnabled())
					System.out.println("Loaded Zombie Class: " + IC.getName());
				if (!isRegistered(IC))
					addClass(IC);
			}
		}
		loadDefaultClasses();
	}

	public static boolean isClass(String className) {
		for (NCClass Class : getClasses(Team.Blue))
			if (Class.getName().equalsIgnoreCase(className))
				return true;
		for (NCClass Class : getClasses(Team.Red))
			if (Class.getName().equalsIgnoreCase(className))
				return true;

		return false;
	}

	public static NCClass getClass(String className) {
		for (NCClass Class : getClasses(Team.Blue))
			if (Class.getName().equalsIgnoreCase(className))
				return Class;
		for (NCClass Class : getClasses(Team.Red))
			if (Class.getName().equalsIgnoreCase(className))
				return Class;

		return null;
	}

}
