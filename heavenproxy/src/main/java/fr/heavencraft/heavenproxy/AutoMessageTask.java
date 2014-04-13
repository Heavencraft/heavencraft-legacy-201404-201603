package fr.heavencraft.heavenproxy;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import net.md_5.bungee.api.ProxyServer;

public class AutoMessageTask implements Runnable
{
	private final static int PERIOD = 5;
	private final static String PREFIX = "§b[Heavencraft]§r ";

	private Random rand = new Random();

	private String[] messages = new String[]
			{
		  // Messages sérieux
			"Des mini-jeux sont installés au Nexus !",
			"Les §9modérateurs§r ne sont pas là pour vous téléporter ou vous expliquer comment jouer !",
			"Les joueurs qui écrivent avec le moins de fautes d'orthographe sont ceux que l'on prend le plus au sérieux.",
			"Avant de vouloir faire une ville, posez la première pierre de votre maison !",
			"Faites §9/modo§r pour demander un modérateur en cas de problème.",
			"Des hits 24/24 et de l'animation le weekend sur notre radio ! Rendez-vous ici : heavencraft.fr/radio.php",
			"Faites §b/nexus§r pour retourner au Nexus.",
			"Envie de nous faire entendre votre petite voix ? Rejoignez-nous sur mumble : heavencraft.fr.",
			"Utilisez §b/rejoindre [pseudo]§r pour vous téléporter à un joueur sur la map ressources ou sur le Créatif.",
			"Vous avez un grand projet de construction mais pas assez de ressources ? Rendez-vous sur la map Créabiome !",
			"Rendez-vous sur le serveur Factions pour vous battre avec d'autres joueurs !",

			// Semi-RP
			"(SRP) Trouvez du travail, ou des travailleurs au cube du travail, à Epsilon, Stonebridge et Centralia. Topic : bit.ly/1mJCwYC",
			"(SRP) Vous êtes nouveau ? Consultez le tutoriel accessible via la commande §b/tuto§r.",
			"(SRP) Besoins d'une parcelle ? Demandez-en une à un maire. La liste des maires connectés est visible avec §b/maires§r.",
			"(SRP) Vous pouvez ajouter d'autres joueurs dans votre protection via §b/region addmember§r.",

			// Messages d'informations
			"En plus de deux ans et demi, plus de 25 000 joueurs se sont connectés à Heavencraft !",
			"Heavencraft a été créé le 28 Mars 2011 !",
			"Le serveur est entièrement financé grâce aux joueurs. Envie de nous aider ? Rendez-vous sur le site pour acheter des HPs.",

			// Messages fun
			"Vous aimez les cookies ? Ça tombe bien ! Moi aussi !",
			"Je mange des petits suisses (miam).",
			"§cMaxou§r is watching you !",
			"La Congolexicomatisation des lois du marché n'est qu'une légende.",
			"Envie de rencontrer §9Pinepy§r ? Rendez-vous au McDo !" 
			};
	/*
    - ''
    - /modo
    - ''
    - 'Votez et notez le serveur sur MCServ : http://www.heavencraft.eu/vote.php'
    - ''
    - ''
    - ''
    - '99% des questions pos?es sur le tchat ont leur r?ponse sur le forum.'
    - 'Heavencraft est d?sormais dot? d''une webradio : http://www.heavencraft.eu/radio.php'
    - 'Event Spleef avec classement et dotation chaque vendredi, 21h.'
    - 'Event PVP avec classement et dotation chaque Samedi, 21h.'
    - ''
    - ''
    - ''
    - 'Pour les commandes du d?butant : http://youtu.be/fw7mPNWEhNc'
    - 'Les fonctionnalit?s du serveur : http://youtu.be/AoojmrSTGHk'
    - 'Si vous laggez, pensez ? mettre votre jeu en anglais ;)'
*/
	public AutoMessageTask()
	{
		ProxyServer.getInstance().getScheduler()
				.schedule(HeavenProxy.getInstance(), this, PERIOD, PERIOD, TimeUnit.MINUTES);
	}

	@Override
	public void run()
	{
		int index = rand.nextInt(messages.length);

		ProxyServer.getInstance().broadcast(PREFIX + messages[index]);
	}
}