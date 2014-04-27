package fr.heavencraft.heavenproxy.chat;

import java.util.HashMap;
import java.util.Map;

public class DisconnectReasonManager
{
	private static Map<String, String> _reasons = new HashMap<String, String>();

	public static void addReason(final String playerName, final String reason)
	{
		_reasons.put(playerName, reason);
	}

	public static String getReason(final String playerName)
	{
		return _reasons.remove(playerName);
	}
}