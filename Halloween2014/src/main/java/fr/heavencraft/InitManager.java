package fr.heavencraft;

import fr.heavencraft.Utils.DevUtils;
import fr.heavencraft.heavenhallow.levels.LevelSignListener;
import fr.heavencraft.heavenhallow.player.HallowlayerListener;

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
		 * from HeavenHallow
		 */

		
	}

	private static void initListeners()
	{
		new HallowlayerListener();
		new LevelSignListener();
	}
	
	private static void initOther()
	{
		new ChatUtil();
		new DevUtils();
	}
	
}
