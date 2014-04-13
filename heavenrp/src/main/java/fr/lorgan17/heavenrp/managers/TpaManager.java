package fr.lorgan17.heavenrp.managers;

import java.util.HashMap;
import java.util.Map;

public class TpaManager
{
	private static Map<String, String> _requests = new HashMap<String, String>();

	public static void addRequest(String toTeleport, String destination)
	{
		_requests.put(toTeleport, destination);
	}

	public static boolean acceptRequest(String toTeleport, String destination)
	{
		String destination2 = (String) _requests.get(toTeleport);

		if ((destination2 == null) || (!destination2.equalsIgnoreCase(destination)))
		{
			return false;
		}

		_requests.remove(toTeleport);
		return true;
	}
}