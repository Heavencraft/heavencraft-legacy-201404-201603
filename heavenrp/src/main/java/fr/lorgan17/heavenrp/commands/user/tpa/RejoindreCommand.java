package fr.lorgan17.heavenrp.commands.user.tpa;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.heavencraft.HeavenCommand;
import fr.heavencraft.Utils;
import fr.heavencraft.exceptions.HeavenException;
import fr.heavencraft.heavenrp.worlds.WorldsManager;
import fr.lorgan17.heavenrp.managers.TpaManager;

public class RejoindreCommand extends HeavenCommand {

	public RejoindreCommand()
	{
		super("rejoindre");
	}

	@Override
	protected void onPlayerCommand(Player player, String[] args) throws HeavenException
	{
		if (args.length != 1)
		{
			sendUsage(player);
			return;
		}
		
		if (!player.getWorld().equals(WorldsManager.getResources()))
			throw new HeavenException("Cette commande n'est pas utilisable en dehors du monde ressources.");
		
		Player destination = Utils.getPlayer(args[0]);
		
		if (!destination.getWorld().equals(WorldsManager.getResources()))
			throw new HeavenException("{%1$s} n'est pas actuellement dans le monde ressources.", destination.getName());
		
		TpaManager.addRequest(player.getName(), destination.getName());
		Utils.sendMessage(destination, "{%1$s} souhaite vous rejoindre. Tapez /accepter {%1$s} pour accepter.", player.getName());
		
		Utils.sendMessage(player, "Votre demande de téléportation a été transmise à {%1$s}", destination.getName());
	}

	@Override
	protected void onConsoleCommand(CommandSender sender, String[] args) throws HeavenException
	{
		Utils.sendMessage(sender, "Cette commande n'est pas utilisable depuis la console.");
	}

	@Override
	protected void sendUsage(CommandSender sender)
	{
		Utils.sendMessage(sender, "/{rejoindre} <joueur>");
	}
}
