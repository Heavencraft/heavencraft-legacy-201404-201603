package fr.heavencraft.heavenrp.commands.hps;

import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import fr.heavencraft.commands.HeavenCommand;
import fr.heavencraft.exceptions.HeavenException;
import fr.heavencraft.heavenrp.general.users.UserProvider;
import fr.heavencraft.heavenrp.hps.HpsManager;
import fr.heavencraft.utils.ChatUtil;
import fr.heavencraft.utils.DevUtil;

public class HpsCommand extends HeavenCommand
{
	private final static byte PLAYER = 3;

	public HpsCommand()
	{
		super("hps");
	}

	@Override
	protected void onPlayerCommand(Player player, String[] args) throws HeavenException
	{
		if (args.length != 1)
		{
			sendUsage(player);
			return;
		}

		if (args[0].equalsIgnoreCase("tete"))
		{

			HpsManager.removeBalance(player.getName(), 5);

			ItemStack head = new ItemStack(Material.SKULL_ITEM, 1, PLAYER);
			SkullMeta meta = (SkullMeta) head.getItemMeta();

			meta.setOwner(player.getName());

			head.setItemMeta(meta);
			player.getInventory().addItem(head);

			ChatUtil.sendMessage(player, "{%1$d} HPs ont été retirés de votre compte", 5);
			ChatUtil.sendMessage(player, "Vous venez de recevoir votre tête.");
		}
		else
		{
			int hps = DevUtil.toUint(args[0]);

			HpsManager.removeBalance(player.getName(), hps);
			UserProvider.getUserByName(player.getName()).updateBalance(hps * 20);

			ChatUtil.sendMessage(player, "{%1$d} HPs ont été retirés de votre compte", hps);
			ChatUtil.sendMessage(player, "Vous avez reçu {%1$d} pièces d'or.", hps * 20);
		}
	}

	@Override
	protected void onConsoleCommand(CommandSender sender, String[] args) throws HeavenException
	{
		// TODO Auto-generated method stub

	}

	@Override
	protected void sendUsage(CommandSender sender)
	{
		ChatUtil.sendMessage(sender, "/{hps} <nombre de HPs a convertir>");
		ChatUtil.sendMessage(sender, "/{hps} tete : acheter votre tête pour 5 HPs.");
		ChatUtil.sendMessage(sender, "Le taux est de {%1$d} pièces d'or par HP.", 20);
		try
		{
			ChatUtil.sendMessage(sender, "Vous avez {%1$d} HPs sur votre compte.",
					HpsManager.getBalance(sender.getName()));
		}
		catch (HeavenException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
