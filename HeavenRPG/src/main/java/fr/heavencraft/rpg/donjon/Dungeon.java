package fr.heavencraft.rpg.donjon;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import com.sk89q.worldedit.bukkit.selections.CuboidSelection;

import fr.heavencraft.rpg.RPGFiles;

public class Dungeon {
	
	private static String _name;
	private static boolean _Running = false;
	private static Location _lobby;
	private static int _requiredPlayerAmmount = 1;
	
	private static HashMap<UUID, Location> _inDungeon = new HashMap<UUID, Location>();
	private static List<DungeonRoom> _rooms = new ArrayList<DungeonRoom>();
	
	private static int _actualRoom = 0;
	
	static class DungeonRoom
	{
		static int _index;
		static Location _spawn;
		static List<Entity> _mobs = new ArrayList<Entity>();
		private CuboidSelection _region;
		
		public DungeonRoom(int indx, Location loc, CuboidSelection cubo)
		{
			set_index(indx);
			set_spawn(loc);
			set_region(cubo);
		}
		
		public int get_index()
		{
			Bukkit.broadcastMessage("Getting idx: " + _index);
			return _index;
		}
		
		public void set_index(int idx)
		{
			Bukkit.broadcastMessage("Setting idx: " + idx);
			_index = idx;
		}
		
		public void add_mob(Entity ety)
		{
			_mobs.add(ety);
		}
		
		public List<Entity> get_mobs()
		{
			return _mobs;
		}
		
		
		public Location get_spawn()
		{
			return _spawn;
		}
		
		public void set_spawn(Location loc)
		{
			_spawn = loc;
		}
		
		public CuboidSelection get_region() {
			return _region;
		}
		public void set_region(CuboidSelection _region) {
			this._region = _region;
		}
		
		
		
	}
	
	
	
	public Dungeon(String name, int reqUser)
	{
		set_name(name);
		set_requiredPlayerAmmount(reqUser);
		RPGFiles.getDungeons().set("Dungeons." + name + ".requiredPlayers", reqUser);
		RPGFiles.saveDungeons();
	}
	
	public Dungeon(String name, int reqUser, Location lobby)
	{
		set_name(name);
		set_requiredPlayerAmmount(reqUser);
		set_lobby(lobby);
	}
	
	
	
	/**
	 * Ajoute un joueur a la lobby/matchmaking du donjon
	 * @param p le joueur a ajouter
	 */
	public void addPlayer(Player p)
	{
		// Ajouter le joueur a la liste d'attente
		get_inDungeon().put(p.getUniqueId(), p.getLocation());
		// Téléporter le joueur dans la lobby
		p.teleport(get_lobby());
		// Véifier si le nombre de joueurs est suffisant
		if(get_inDungeon().size() == get_requiredPlayerAmmount())
			// Démarrer la lobby
			startDungeon();		
		//TODO SI NON Informer les autres joueurs de combient de joueurs manquent
	}
	
	public void addDungeonRoom(DungeonRoom dgr)
	{
		_rooms.add(dgr);
	}
	
	public void removeDungeonRoom(DungeonRoom dgr)
	{
		_rooms.remove(dgr);
	}
	
	public void updateDungeonRoomSpawn(DungeonRoom dgr, Location spawn)
	{
		dgr.set_spawn(spawn);
	}
	
	private static void startDungeon()
	{
		// Marquer le donjon comme en jeu
		set_Running(true);
		// Faire pointer le pointeur du room sur le premier room
		setActualRoom(1);
		//TODO Initialiser le room
		//TODO Téléporter les joueurs au spawn du premier room
		
	}
	
	private static void stopDungeon()
	{
		
		//TODO Faire pointer le pointeur du room sur le premier room
		
		// Téléporter les joueurs a leur point d'entrée
		for(UUID uid : get_inDungeon().keySet())
		{
			Player p = Bukkit.getPlayer(uid);
			if(p != null)
				p.teleport(get_inDungeon().get(uid));
		}			
		
		// Réninitialiser le pointeur
		setActualRoom(0);
		// Marquer le donjon comme en jeu
		set_Running(false);
	}
	
	public DungeonRoom getDungeonRoomByIndex(int index)
	{
		for(DungeonRoom dgr : get_rooms())
			if(dgr.get_index() == index)
				return dgr;
		return null;
	}
	
	public int getLastDungeonIndex()
	{
		int result = 0;
		for(DungeonRoom dgr : get_rooms())
			if(dgr.get_index() > result)
				result = dgr.get_index();
		return result;
	}
	

	/**
	 * Returns the name of the dungeon
	 * @return
	 */
	public String get_name() {
		return _name;
	}
	private static void set_name(String name) {
		_name = name;
	}
	
	public boolean is_Running() {
		return _Running;
	}

	private static void set_Running(boolean isRunning) {
		_Running = isRunning;
	}

	private static Location get_lobby() {
		return _lobby;
	}

	public void set_lobby(Location lobby) {
		_lobby = lobby;
	}

	public int get_requiredPlayerAmmount() {
		return _requiredPlayerAmmount;
	}

	private static void set_requiredPlayerAmmount(int requiredPlayerAmmount) {
		_requiredPlayerAmmount = requiredPlayerAmmount;
	}

	private static AbstractMap<UUID,Location> get_inDungeon() {
		return _inDungeon;
	}

	private static void set_inDungeon(HashMap<UUID, Location> inDungeon) {
		_inDungeon = inDungeon;
	}

	public List<DungeonRoom> get_rooms() {
		return _rooms;
	}

	private static void set_rooms(List<DungeonRoom> rooms) {
		_rooms = rooms;
	}

	public int getActualRoom() {
		return _actualRoom;
	}

	private static void setActualRoom(int actualRoom) {
		_actualRoom = actualRoom;
	}
	
	
	
}
