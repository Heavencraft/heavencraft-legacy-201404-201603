package fr.heavencraft.heavenproxy.mute;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class MuteManager
{
	private static Map<String, Date> _muted = new HashMap<String, Date>();

	public static boolean isMuted(String player)
	{
		if (!_muted.containsKey(player))
			return false;

		Date mutedUntil = _muted.get(player);

		if (new Date().after(mutedUntil))
		{
			_muted.remove(player);
			return false;
		}
		else
			return true;
	}

	public static void mutePlayer(String player, int minutes)
	{
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MINUTE, minutes);

		_muted.put(player, cal.getTime());
	}
}