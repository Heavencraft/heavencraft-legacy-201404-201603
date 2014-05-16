package fr.heavencraft.laposte.commands.user;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.heavencraft.laposte.LaPoste;
import fr.heavencraft.laposte.commands.LaPosteCommand;
import fr.heavencraft.laposte.handlers.Colis;
import fr.heavencraft.laposte.handlers.MenuColisRecus;
import fr.heavencraft.laposte.handlers.PostOfficeManager;
import fr.heavencraft.laposte.handlers.EnAttente.ColisEnAttente;

public class colisCommand extends LaPosteCommand{

	
	private final static String FORMAT_POSTE = "§4[§6La Poste§4] §6%1$s";
	
	public colisCommand() {
		super("colis");
		
		
	}
	
	//TODO: Le colis est stoqué, et seulement, lorsque le joueur quitte une poste, les colis sont envoyés a la bdd.

	@Override
	protected void onPlayerCommand(Player player, String[] args) throws Exception {

		if (args.length != 1) {
			player.sendMessage(String.format(FORMAT_POSTE, "/colis <destinataire>"));
			player.sendMessage(String.format(FORMAT_POSTE, "/colis recu"));
		}
		else if(args[0].equalsIgnoreCase("recu"))
		{
			MenuColisRecus menu = new MenuColisRecus();
			if(PostOfficeManager.isInOffice(player))
				menu.Ouvrir(player);
			else
				player.sendMessage(String.format(FORMAT_POSTE, "Vous devez être dans un bureau de Poste."));
			
		}
		else
		{
			if(PostOfficeManager.isInOffice(player))
			{
				Player destinataire = LaPoste.getInstance().getServer().getPlayer(args[0]);
				if(destinataire != null)
				{
					//le joueur est connecté.	
					if(destinataire != player)
					{
						//Ouvir l'inventaire "virtuel" pour que le joueur puisse y placer les items
						Colis colis = new Colis(player, destinataire);
						//Placer le colis en attente d'envoi.
						ColisEnAttente.addColis(player, colis);
						
						colis.openColisForCreation();
					}
					else
						player.sendMessage(String.format(FORMAT_POSTE, "Vous voulez vous envoyer un colis a vous même, monsieur..."));		
				}
				else
				{
					player.sendMessage(String.format(FORMAT_POSTE, "Ce joueur n'existe pas ou n'est pas connecté."));
				}				
			}
			else
				player.sendMessage(String.format(FORMAT_POSTE, "Vous devez être dans un bureau de Poste."));
			
			
			
		}
		
	}

	@Override
	protected void onConsoleCommand(CommandSender sender, String[] args)
			throws Exception {
		
	}

	@Override
	protected void sendUsage(CommandSender sender) {
		
	}

}
