package fr.lorgan17.heavenrp.commands.user.tpa;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.heavencraft.HeavenCommand;
import fr.heavencraft.Utils;
import fr.heavencraft.exceptions.HeavenException;
import fr.heavencraft.heavenrp.worlds.WorldsManager;
import fr.lorgan17.heavenrp.managers.TpaManager;

public class AccepterCommand extends HeavenCommand {

	public AccepterCommand()
	{
		super("accepter");
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
		
		Player toTeleport = Utils.getPlayer(args[0]);
		
		if (!toTeleport.getWorld().equals(WorldsManager.getResources()))
			throw new HeavenException("{%1$s} n'est pas actuellement dans le monde ressources.", toTeleport.getName());
		
		if (!TpaManager.acceptRequest(toTeleport.getName(), player.getName()))
			throw new HeavenException("{%1$s} n'a pas demandé à vous rejoindre.", toTeleport.getName());
		
		//toTeleport.teleport(player);
		Utils.teleportPlayer(toTeleport, player);
		Utils.sendMessage(toTeleport, " Vous avez été téléporté à {%1$s}.", player.getName());
		Utils.sendMessage(player, "{%1$s} a été téléporté.", toTeleport.getName());
	}

	@Override
	protected void onConsoleCommand(CommandSender sender, String[] args) throws HeavenException
	{
		Utils.sendMessage(sender, "Cette commande n'est pas utilisable depuis la console.");
	}

	@Override
	protected void sendUsage(CommandSender sender)
	{
		Utils.sendMessage(sender, "/{accepter} <joueur>");
	}
}
