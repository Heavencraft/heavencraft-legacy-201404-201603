package fr.heavencraft.railways;

import java.util.HashMap;
import java.util.UUID;

public class PlayerStationManager {
	private static HashMap<UUID, String> _userStations = new HashMap<UUID, String>();

	public static HashMap<UUID, String> get_userStations() {
		return _userStations;
	}
	public static void set_userStations(HashMap<UUID, String> _userStations) {
		PlayerStationManager._userStations = _userStations;
	}
	public static void addUser(UUID uid, String st) {
		PlayerStationManager._userStations.put(uid, st);
	}
	public static void removeUser(UUID uid) {
		PlayerStationManager._userStations.remove(uid);
	}
	public static String getUserStation(UUID uid) {
		return PlayerStationManager._userStations.get(uid);
	}

}
