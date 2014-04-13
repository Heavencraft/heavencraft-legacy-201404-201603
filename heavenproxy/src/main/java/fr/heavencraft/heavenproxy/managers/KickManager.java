package fr.heavencraft.heavenproxy.managers;

import java.util.HashMap;
import java.util.Map;

public class KickManager {
	
	private static Map<String, String> _reasons = new HashMap<String, String>();
	
	public static void addReason(String playerName, String reason)
	{
		_reasons.put(playerName, reason);
	}
	
	public static String getReason(String playerName)
	{
		return _reasons.remove(playerName);
	}
}
