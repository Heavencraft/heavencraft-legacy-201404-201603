package fr.heavencraft.heavenrp.hps;

import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import fr.heavencraft.HeavenCommand;
import fr.heavencraft.Utils;
import fr.heavencraft.exceptions.HeavenException;

public class HeadCommand extends HeavenCommand
{
	private final static byte PLAYER = 3;

	public HeadCommand()
	{
		super("head", "heavenrp.administrator.head");
	}

	@Override
	protected void onPlayerCommand(Player player, String[] args) throws HeavenException
	{
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

		Utils.sendMessage(player, "Vous venez de recevoir la tête de {" + args[0] + "}");
	}

	@Override
	protected void onConsoleCommand(CommandSender sender, String[] args) throws HeavenException
	{
		// TODO Auto-generated method stub

	}

	@Override
	protected void sendUsage(CommandSender sender)
	{
	}
}