package fr.heavencraft.commands;

import static fr.heavencraft.utils.ChatUtil.sendMessage;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.heavencraft.Permissions;
import fr.heavencraft.async.actions.ActionsHandler;
import fr.heavencraft.async.actions.TeleportPlayerAction;
import fr.heavencraft.exceptions.HeavenException;
import fr.heavencraft.utils.PlayerUtil;

public class AccepterCommand extends HeavenCommand
{
	public AccepterCommand()
	{
		super("accepter", Permissions.REJOINDRE);
	}

	@Override
	protected void onPlayerCommand(Player player, String[] args) throws HeavenException
	{
		if (args.length != 1)
		{
			sendUsage(player);
			return;
		}
		Player toTeleport = PlayerUtil.getPlayer(args[0]);

		if (!player.hasPermission(Permissions.REJOINDRE))
			throw new HeavenException("Vous n'êtes pas actuellement dans le monde ressources.");

		if (!toTeleport.hasPermission(Permissions.REJOINDRE))
			throw new HeavenException("{%1$s} n'est pas actuellement dans le monde ressources.",
					toTeleport.getName());

		if (!RejoindreCommand.acceptRequest(toTeleport.getName(), player.getName()))
			throw new HeavenException("{%1$s} n'a pas demandé à vous rejoindre.", toTeleport.getName());

		ActionsHandler.addAction(new TeleportPlayerAction(toTeleport, player)
		{
			@Override
			public void onSuccess()
			{
				sendMessage(toTeleport, " Vous avez été téléporté à {%1$s}.", player.getName());
				sendMessage(player, "{%1$s} a été téléporté.", toTeleport.getName());
			}
		});
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
}