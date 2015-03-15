package fr.heavencraft.railways;

import fr.heavencraft.Utils.ChatUtil;
import fr.heavencraft.Utils.DevUtils;

public class InitManager {
	public static void init()
	{
		initCommands();
		initListeners();
		initOther();
	}
	
	private static void initCommands()
	{
		new StationCommand();
	}

	private static void initListeners()
	{
		new RailwayPlayerListener();
		new RailwayFunctionHandler();	
	}
	
	private static void initOther()
	{
		new PlayerStationManager();
		new ChatUtil();
		new DevUtils();
	}
	
}
