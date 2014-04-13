package fr.heavencraft.heavenrp.hps;

import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import fr.heavencraft.HeavenCommand;
import fr.heavencraft.Utils;
import fr.heavencraft.exceptions.HeavenException;
import fr.heavencraft.heavenrp.general.users.UsersManager;

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

			Utils.sendMessage(player, "{%1$d} HPs ont été retirés de votre compte", 5);
			Utils.sendMessage(player, "Vous venez de recevoir votre tête.");
		}
		else
		{
			int hps = Utils.toUint(args[0]);

			HpsManager.removeBalance(player.getName(), hps);
			UsersManager.getByName(player.getName()).updateBalance(hps * 20);

			Utils.sendMessage(player, "{%1$d} HPs ont été retirés de votre compte", hps);
			Utils.sendMessage(player, "Vous avez reçu {%1$d} pièces d'or.", hps * 20);
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
		Utils.sendMessage(sender, "/{hps} <nombre de HPs a convertir>");
		Utils.sendMessage(sender, "/{hps} tete : acheter votre tête pour 5 HPs.");
		Utils.sendMessage(sender, "Le taux est de {%1$d} pièces d'or par HP.", 20);
		try
		{
			Utils.sendMessage(sender, "Vous avez {%1$d} HPs sur votre compte.", HpsManager.getBalance(sender.getName()));
		}
		catch (HeavenException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
