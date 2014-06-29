package fr.manu67100.heavenrp.laposte.commands.user;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import fr.heavencraft.commands.HeavenCommand;
import fr.heavencraft.exceptions.HeavenException;
import fr.heavencraft.heavenrp.HeavenRP;
import fr.heavencraft.heavenrp.general.users.User;
import fr.heavencraft.heavenrp.general.users.UserProvider;
import fr.heavencraft.utils.ChatUtil;
import fr.manu67100.heavenrp.laposte.handlers.JoueursEnEditionDeColis;
import fr.manu67100.heavenrp.laposte.handlers.PostOfficeManager;

public class colisCommand extends HeavenCommand{

	
	private final static String FORMAT_POSTE = "§4[§6La Poste§4] §6%1$s";
	
	public colisCommand() {
		super("colis");	
	}

	@Override
	protected void onPlayerCommand(Player player, String[] args) throws HeavenException {

		if (args.length != 1) {
			player.sendMessage(String.format(FORMAT_POSTE, "/colis <destinataire> Pour envoyer un colis."));
		}
		else
		{
			if(PostOfficeManager.isInOffice(player))
			{
				Player destinataire = HeavenRP.getInstance().getServer().getPlayer(args[0]);
				if(destinataire != null)
				{
					//le joueur est connect�.	
					if(destinataire != player)
					{		
						User user = UserProvider.getUserByName(player.getName());
						if (user.getBalance() -45 > 0)
						{
							//On ajoute le joueur a la liste des joueurs en edition.
							JoueursEnEditionDeColis.addPlayer(player, destinataire);					
							Inventory contenu =  Bukkit.createInventory(null, 9, "Heaven Colis");
							player.openInventory(contenu);
							return;
						}
						else
						{
							player.sendMessage(String.format(FORMAT_POSTE, "Vous n'avez pas assez d'argent."));
							return;
						}
	
						
									
					}
					else
						player.sendMessage(String.format(FORMAT_POSTE, "Vous voulez vous envoyer un colis a vous même, monsieur..."));		
				}
				else
					player.sendMessage(String.format(FORMAT_POSTE, "Ce joueur n'existe pas ou n'est pas connect�."));					
			}
			else
				player.sendMessage(String.format(FORMAT_POSTE, "Vous devez ètre dans un bureau de Poste."));
		}
		
	}

	@Override
	protected void onConsoleCommand(CommandSender sender, String[] args)
			throws HeavenException {
		ChatUtil.sendMessage(sender, "Cette commande ne peut pas être utilisée depuis la {console}.");
	}

	@Override
	protected void sendUsage(CommandSender sender) {
		ChatUtil.sendMessage(sender, "/colis <destinataire> Pour envoyer un colis.");
	}

}
