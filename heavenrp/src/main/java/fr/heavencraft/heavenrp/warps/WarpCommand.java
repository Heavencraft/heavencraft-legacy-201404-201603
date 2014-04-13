package fr.heavencraft.heavenrp.warps;

import java.util.List;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.heavencraft.HeavenCommand;
import fr.heavencraft.Permissions;
import fr.heavencraft.Utils;
import fr.heavencraft.exceptions.HeavenException;

public class WarpCommand extends HeavenCommand
{
	public WarpCommand()
	{
		super("warp", Permissions.WARP_COMMAND);
	}

	@Override
	protected void onPlayerCommand(Player player, String[] args) throws HeavenException
	{
		switch (args.length)
		{
			case 1:
				if (args[0].equalsIgnoreCase("list"))
				{
					List<String> warps = WarpsManager.listWarps();
					String list = "";

					for (String warp : warps)
						list = (list == "" ? "" : ", ") + "{" + warp + "}";

					Utils.sendMessage(player, "Liste des warps :");
					Utils.sendMessage(player, list);
				}
				break;
			case 2:
				if (args[0].equalsIgnoreCase("define"))
				{
					WarpsManager.createWarp(args[1], player.getLocation());
					Utils.sendMessage(player, "Le warp {%1$s} a bien été défini.", args[1]);
				}
				else if (args[0].equalsIgnoreCase("remove"))
				{
					WarpsManager.getWarp(args[1]).remove();
					Utils.sendMessage(player, "Le warp {%1$s} a bien été supprimé.", args[1]);
				}
				else if (args[0].equalsIgnoreCase("tp"))
				{
					player.teleport(WarpsManager.getWarp(args[1]).getLocation());
					Utils.sendMessage(player, "Vous avez été téléporté à {%1$s}.", args[1]);
				}
				break;
			default:
				sendUsage(player);
				return;
		}
	}

	@Override
	protected void onConsoleCommand(CommandSender sender, String[] args) throws HeavenException
	{
		Utils.sendMessage(sender, "Cette commande n'est pas utilisable depuis la console.");
	}

	@Override
	protected void sendUsage(CommandSender sender)
	{
		Utils.sendMessage(sender, "/{warp} define <nom>");
		Utils.sendMessage(sender, "/{warp} list");
		Utils.sendMessage(sender, "/{warp} tp <nom>");
		Utils.sendMessage(sender, "/{warp} remove <nom>");
	}
}