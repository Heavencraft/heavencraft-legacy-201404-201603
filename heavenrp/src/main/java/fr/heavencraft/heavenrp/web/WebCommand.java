package fr.heavencraft.heavenrp.web;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import fr.heavencraft.commands.HeavenCommand;
import fr.heavencraft.exceptions.HeavenException;
import fr.heavencraft.heavenrp.RPPermissions;
import fr.heavencraft.heavenrp.general.users.User;
import fr.heavencraft.heavenrp.general.users.UserProvider;
import fr.heavencraft.utils.ChatUtil;
import fr.heavencraft.utils.DevUtil;

public class WebCommand extends HeavenCommand
{
	public WebCommand(String name, String permission)
	{
		super("web", RPPermissions.WEB_COMMAND);
	}

	@Override
	protected void onPlayerCommand(Player player, String[] args) throws HeavenException
	{
		ChatUtil.sendMessage(player, "Cette commande n'est utilisable que depuis la {console}.");
	}

	@Override
	protected void onConsoleCommand(CommandSender sender, String[] args) throws HeavenException
	{
		if (args.length == 0)
			return;

		switch (args[0])
		{
		// /web po <joueur> <nombre de po>
			case "po": {
				if (args.length != 3)
					return;

				User user = UserProvider.getUserByName(args[1]);
				int delta = DevUtil.toUint(args[2]);

				user.updateBalance(delta);
				ChatUtil.sendMessage(user.getName(), "Vous venez d'acheter {%1$s} pièces d'or sur la boutique.");
			}
			
			case "head": {
				if (args.length != 2)
					return;
				User user = UserProvider.getUserByName(args[1]);
				
				ItemStack head = new ItemStack(Material.SKULL_ITEM, 1,(short) SkullType.PLAYER.ordinal());
				SkullMeta skullMeta = (SkullMeta) head.getItemMeta();
				skullMeta.setOwner(user.getName());
				skullMeta.setDisplayName(ChatColor.RESET + args[2]);
				head.setItemMeta(skullMeta);
				Bukkit.getPlayer(user.getName()).getInventory().addItem(head);
				ChatUtil.sendMessage(user.getName(), "Vous venez d'acheter votre tête sur la boutique.");
			}
		}
	}

	@Override
	protected void sendUsage(CommandSender sender)
	{
	}
}