package fr.heavencraft.rpg.donjon;

import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.block.SignChangeEvent;

import fr.heavencraft.exceptions.HeavenException;
import fr.heavencraft.rpg.ChatUtil;
import fr.heavencraft.rpg.RPGpermissions;
import fr.heavencraft.rpg.SignListener;

public class DungeonSignListener extends SignListener {
	private final static String DUNGEON_DOES_NOT_EXIST = "Ce donjon n'existe pas!";
	
	public DungeonSignListener()
	{
		super("Donjon", RPGpermissions.DONJON_SIGN);
	}
	
	@Override
	protected boolean onSignPlace(Player player, SignChangeEvent event) throws HeavenException {
		return true;
	}

	@Override
	protected void onSignClick(Player player, Sign sign) throws HeavenException {
		// Faire entrer le joueur dans le donjon écrit dans la ligne 1
		Dungeon dg = DungeonManager.getDungeonByName(sign.getLine(1));
		
		if(dg == null)
		{
			ChatUtil.sendMessage(player, DUNGEON_DOES_NOT_EXIST);
			return;
		}
		
		// Vérifier que le donjon est accesible
		if(!dg.is_Running())
			// Ajouter le joueur a la liste du matchmaking
			dg.addPlayer(player);
		//TODO autre logique?
		return;
	}

}
