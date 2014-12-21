package fr.heavencraft;

import fr.heavencraft.Utils.DevUtils;
import fr.heavencraft.heavenNoel.PlayerListener;
import fr.heavencraft.heavenNoel.StartRaceSignListener;

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
		new PlayerListener();
		new StartRaceSignListener();
	}
	
	private static void initOther()
	{
		new ChatUtil();
		new DevUtils();
	}
	
}
