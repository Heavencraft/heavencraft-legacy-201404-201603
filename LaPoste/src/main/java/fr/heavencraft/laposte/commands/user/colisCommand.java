package fr.heavencraft.laposte.commands.user;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.heavencraft.laposte.LaPoste;
import fr.heavencraft.laposte.commands.LaPosteCommand;
import fr.heavencraft.laposte.handlers.PostOfficeManager;

public class colisCommand extends LaPosteCommand{

	
	private final static String FORMAT_POSTE = "�4[�6La Poste�4] �6%1$s";
	
	public colisCommand() {
		super("colis");
		
		
	}

	@Override
	protected void onPlayerCommand(Player player, String[] args) throws Exception {

		if (args.length != 1) {
			player.sendMessage(String.format(FORMAT_POSTE, "/colis <destinataire>"));
		}
		else
		{
			if(PostOfficeManager.isInOffice(player))
			{
				Player destinataire = LaPoste.getInstance().getServer().getPlayer(args[0]);
				if(destinataire != null)
				{
					//le joueur est connect�.
					
					
					
					//TODO: Ouvir l'inventaire "virtuel" pour que le joueur puisse y placer les items
					//TODO: A la fermeture de l'inventaire, on envoie les items.
					// --> par base de donn�e, ajouter l'inventaire s�rialis�.
					
				}
				else
				{
					destinataire = LaPoste.getInstance().getServer().getOfflinePlayer(args[0]).getPlayer();
					if(destinataire != null)
					{
						//le joueur existe mais est d�connect�.
						
						
						//TODO: Ouvir l'inventaire "virtuel" pour que le joueur puisse y placer les items
						//TODO: A la fermeture de l'inventaire, on envoie les items.
						// --> par base de donn�e, ajouter l'inventaire s�rialis�.
					}
					else
						player.sendMessage(String.format(FORMAT_POSTE, "Ce joueur n'existe pas."));
				}				
			}
			else
				player.sendMessage(String.format(FORMAT_POSTE, "Vous devez �tre dans un bureau de Poste."));
			
			
			
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
