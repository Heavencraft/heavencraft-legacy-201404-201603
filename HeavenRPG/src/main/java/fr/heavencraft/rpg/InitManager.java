package fr.heavencraft.rpg;

import fr.heavencraft.rpg.Parchemins.ParcheminCommand;
import fr.heavencraft.rpg.Parchemins.ParcheminProvider;
import fr.heavencraft.rpg.Parchemins.ParcheminsListener;
import fr.heavencraft.rpg.mobs.MobListener;
import fr.heavencraft.rpg.mobs.MobManager;
import fr.heavencraft.rpg.player.RPGPlayerListener;
import fr.heavencraft.rpg.zones.ZoneCommand;
import fr.heavencraft.rpg.zones.ZoneListener;
import fr.heavencraft.rpg.zones.ZoneManager;

public class InitManager {
	public static void init()
	{
		initCommands();
		initListeners();
		initOther();
	}
	
	private static void initCommands()
	{
		
		/*
		 * from HeavenRPG
		 */

		// Zone
		new ZoneCommand();
		new ParcheminCommand();
		ZoneManager.loadAllZones();
		ParcheminProvider.LoadParchemins();
	
	}

	private static void initListeners()
	{
		new RPGPlayerListener();
		new ZoneListener();
		new MobListener();
		new ParcheminsListener();
	}
	
	private static void initOther()
	{
		new MobManager();
		new ChatUtil();
	}
	
}
