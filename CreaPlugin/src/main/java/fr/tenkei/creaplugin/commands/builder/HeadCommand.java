package fr.tenkei.creaplugin.commands.builder;

import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import fr.tenkei.creaplugin.MyPlugin;
import fr.tenkei.creaplugin.commands.Command;
import fr.tenkei.creaplugin.exceptions.MyException;
import fr.tenkei.creaplugin.utils.Message;



public class HeadCommand extends Command{

private final static byte PLAYER = 3;
	
	public HeadCommand(MyPlugin plugin)
	{
		super("head", plugin);
	}

	@Override
	protected void onConsoleCommand(CommandSender sender, String[] args) throws MyException
	{
	}

	@Override
	protected void sendUsage(CommandSender sender)
	{
		Message.sendMessage(sender, "{/head} <joueur>");
	}

	@Override
	protected void onPlayerCommand(Player player, String[] args)
			throws MyException {
		if(!player.hasPermission(MyPlugin.builder))
			return;
		
		if (args.length != 1)
		{
			sendUsage(player);
			return;
		}
		
		ItemStack head = new ItemStack(Material.SKULL_ITEM, 1, PLAYER);
		SkullMeta meta = (SkullMeta) head.getItemMeta();
		
		meta.setOwner(args[0]);
		
		head.setItemMeta(meta);
		player.getInventory().addItem(head);
		
		Message.sendMessage(player, "Vous venez de recevoir la tête de {" + args[0] + "}");
	}

	
}
