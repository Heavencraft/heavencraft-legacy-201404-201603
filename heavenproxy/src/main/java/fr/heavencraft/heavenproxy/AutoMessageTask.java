package fr.heavencraft.heavenproxy;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;

public class AutoMessageTask implements Runnable
{
	private static final int PERIOD = 300;
	private static final String PREFIX = "§b[Heavencraft]§r ";

	private final Random rand = new Random();

	private final String[] messages = new String[] {
			// Messages sérieux
			"N'oubliez pas de /voter pour le serveur !",
			"Des mini-jeux sont installés au Nexus !",
			"Les §9modérateurs§r ne sont pas là pour vous téléporter ou vous expliquer comment jouer !",
			"Les joueurs qui écrivent avec le moins de fautes d'orthographe sont ceux que l'on prend le plus au sérieux.",
			"Avant de vouloir faire une ville, posez la première pierre de votre maison !",
			"Faites §9/modo§r pour demander un modérateur en cas de problème.",
			"Des hits 24/24 et de l'animation le weekend sur notre radio ! Rendez-vous ici : heavencraft.fr/radio.php",
			"Faites §b/nexus§r pour retourner au Nexus.",
			"Envie de nous faire entendre votre petite voix ? Rejoignez-nous sur mumble : heavencraft.fr.",
			"Utilisez §b/rejoindre [pseudo]§r pour vous téléporter à un joueur sur la map ressources ou dans le Créatif.",

			// Semi-RP
			"(SRP) Trouvez du travail, ou des travailleurs au cube du travail, à Stonebridge et Centralia. Topic : bit.ly/1mJCwYC",
			"(SRP) Vous êtes nouveau ? Consultez le tutoriel accessible via la commande §b/tuto§r.",
			"(SRP) Besoins d'une parcelle ? Demandez-en une à un maire. La liste des maires connectés est visible avec §b/maires§r.",
			"(SRP) Vous pouvez ajouter d'autres joueurs dans votre protection via §b/region addmember§r.",
			"(SRP) Venez vous battre dans la ville PVP !",

			// Autres serveurs
			"(Créa) Vous avez un grand projet de construction mais pas assez de ressources ? Rendez-vous sur la map Créabiome !",

			// Messages d'informations
			"Heavencraft a été créé le 28 Mars 2011 !",
			"En 3 ans, plus de 26000 joueurs se sont connectés à Heavencraft !",
			"Le serveur est entièrement financé grâce aux joueurs. Envie de nous aider ? Rendez-vous sur le site pour acheter des HPs.",

			// Messages fun
			"Vous aimez les cookies ? Ça tombe bien ! Moi aussi !", "Je mange des petits suisses (miam).",
			"§cBig Brother§r is watching you !", "Non §9Pinepy§r ! C'est un warp, pas un wrap !" };

	public AutoMessageTask()
	{
		ProxyServer.getInstance().getScheduler()
				.schedule(HeavenProxy.getInstance(), this, PERIOD, PERIOD, TimeUnit.SECONDS);
	}

	@Override
	public void run()
	{
		int index = rand.nextInt(messages.length);

		ProxyServer.getInstance().broadcast(TextComponent.fromLegacyText(PREFIX + messages[index]));
	}
}