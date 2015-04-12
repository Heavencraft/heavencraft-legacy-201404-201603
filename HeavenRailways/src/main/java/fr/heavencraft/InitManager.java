package fr.heavencraft;

import fr.heavencraft.Utils.ChatUtil;
import fr.heavencraft.Utils.DevUtils;
import fr.heavencraft.portiques.PortiqueManager;
import fr.heavencraft.portiques.PortiqueSignListener;
import fr.heavencraft.railways.PlayerStationManager;
import fr.heavencraft.railways.RailwayFunctionHandler;
import fr.heavencraft.railways.RailwayPlayerListener;
import fr.heavencraft.railways.StationCommand;

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
		new PortiqueManager();
		new PortiqueSignListener();
		
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
