package fr.heavencraft.commands;

import static fr.heavencraft.utils.ChatUtil.sendMessage;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.heavencraft.Permissions;
import fr.heavencraft.exceptions.HeavenException;
import fr.heavencraft.utils.PlayerUtil;

public class RejoindreCommand extends HeavenCommand
{
	private static Map<String, String> requests = new HashMap<String, String>();

	public RejoindreCommand()
	{
		super("rejoindre", Permissions.REJOINDRE);
	}

	@Override
	protected void onPlayerCommand(Player player, String[] args) throws HeavenException
	{
		if (args.length != 1)
		{
			sendUsage(player);
			return;
		}

		Player destination = PlayerUtil.getPlayer(args[0]);

		if (!destination.hasPermission(Permissions.REJOINDRE))
			throw new HeavenException("{%1$s} n'est pas actuellement dans le monde ressources.", destination.getName());

		addRequest(player.getName(), destination.getName());

		sendMessage(destination, "{%1$s} souhaite vous rejoindre. Tapez /accepter {%1$s} pour accepter.",
				player.getName());

		sendMessage(player, "Votre demande de téléportation a été transmise à {%1$s}", destination.getName());
	}

	@Override
	protected void onConsoleCommand(CommandSender sender, String[] args) throws HeavenException
	{
		sendMessage(sender, "Cette commande n'est pas utilisable depuis la console.");
	}

	@Override
	protected void sendUsage(CommandSender sender)
	{
		sendMessage(sender, "/{rejoindre} <joueur>");
		sendMessage(sender, "/{accepter} <joueur>");
	}

	public static void addRequest(String toTeleport, String destination)
	{
		requests.put(toTeleport, destination);
	}

	public static boolean acceptRequest(String toTeleport, String destination)
	{
		String destination2 = requests.get(toTeleport);

		if (destination.equalsIgnoreCase(destination2))
		{
			requests.remove(toTeleport);
			return true;
		}

		return false;
	}
}