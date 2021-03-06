package fr.heavencraft.HellCraft.commands.user;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import fr.heavencraft.HellCraft.HellCraft;
import fr.heavencraft.HellCraft.Utils;
import fr.heavencraft.HellCraft.HPS.HpsManager;
import fr.heavencraft.HellCraft.commands.HellCommand;
import fr.heavencraft.HellCraft.exceptions.HellException;

public class HPScommand extends HellCommand
{
	private static final String FORMAT_WP = "§4[§6%1$s§4] §6%2$s";

	public HPScommand()
	{
		super("hps");
	}

	@Override
	protected void onPlayerCommand(Player player, String[] args) throws HellException
	{
		if (args.length == 0)
		{
			player.sendMessage(String.format("§4[§6%1$s§4] §6%2$s",
					new Object[] { "HPS §aINFO", "Votre solde: §2" + HpsManager.getBalance(player.getName()) }));
			player.sendMessage(String.format("§4[§6%1$s§4] §6%2$s", new Object[] { "HPS §aINFO",
					"Faites /hps liste pour une liste des produits." }));
			return;
		}

		if ((args[0].equalsIgnoreCase("liste")) || (args[0].equalsIgnoreCase("l")))
		{
			player.sendMessage(String.format("§4[§6%1$s§4] §6%2$s", new Object[] { "HPS", "┌───────── Boutique HPS ─────────┐" }));
			player.sendMessage(String.format("§4[§6%1$s§4] §6%2$s",
					new Object[] { "HPS", " ─────── Avantages Ponctuels ─────── " }));
			player.sendMessage(String.format("§4[§6%1$s§4] §6%2$s", new Object[] { "HPS",
					" - Restaure la vie, §55 HPS§6: §c/hps heal" }));
			player.sendMessage(String.format("§4[§6%1$s§4] §6%2$s", new Object[] { "HPS",
					" - Retour a la dernière position, §55 HPS§6: §c/hps back" }));
			player.sendMessage(String.format("§4[§6%1$s§4] §6%2$s", new Object[] { "HPS",
					" - Equipement Diamant, §515 HPS§6: §c/hps diam" }));
			player.sendMessage(String.format("§4[§6%1$s§4] §6%2$s", new Object[] { "HPS",
					" ─────── Avantages permissions ─────── " }));
			player.sendMessage(String.format("§4[§6%1$s§4] §6%2$s", new Object[] { "HPS",
					" - Carte de réduction 33% boutique, §525 HPS§6: §c/hps reduc" }));
			player.sendMessage(String.format("§4[§6%1$s§4] §6%2$s", new Object[] { "HPS", " - VIP, §550HPS§6: §c/hps vip" }));

			player.sendMessage(String.format("§4[§6%1$s§4] §6%2$s", new Object[] { "HPS",
					"Note: pour avoir plus d'information sur un kit: §c/hps <kit> info" }));
			player.sendMessage("");
		}
		else if (args[0].equalsIgnoreCase("back"))
		{
			if (args.length >= 2)
			{
				player.sendMessage(String.format("§4[§6%1$s§4] §6%2$s", new Object[] { "HPS",
						"Le /hps back vous permet de retourner au dernier point avant votre décès." }));
				player.sendMessage(String.format("§4[§6%1$s§4] §6%2$s", new Object[] { "HPS",
						"La commande est risquée, soyez prudant." }));
				player.sendMessage(String.format("§4[§6%1$s§4] §6%2$s", new Object[] { "HPS", "Prix: 5HPS." }));
			}
			else
			{
				Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + player.getName() + " add essentials.back");
				Bukkit.dispatchCommand(player, "back");

				Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + player.getName() + " remove essentials.back");
				final Player tmpPlayer = player;

				Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(HellCraft.getInstance(), new Runnable()
				{
					public void run()
					{
						if (tmpPlayer.getLocation().getWorld().getName().equalsIgnoreCase("city"))
							try
							{
								HpsManager.removeBalance(tmpPlayer.getName(), 5);
								tmpPlayer.sendMessage(String.format("§4[§6%1$s§4] §6%2$s", new Object[] { "HPS §2Confirmation",
										" Vous avez acheté le /back, 5HPS débités." }));
							}
							catch (final HellException e)
							{
								tmpPlayer.sendMessage(String.format("§4[§6%1$s§4] §6%2$s", new Object[] { "HPS §2Notification",
										" Vous n'avez pas été débités, car le /back ne vous a pas téléporté sur le bon monde." }));
							}
					}
				}, 60L);
			}

		}
		else if (args[0].equalsIgnoreCase("heal"))
		{
			if (args.length >= 2)
			{
				player.sendMessage(String.format("§4[§6%1$s§4] §6%2$s", new Object[] { "HPS",
						"Le /hps heal restaure votre vie et votre faim." }));
				player.sendMessage(String.format("§4[§6%1$s§4] §6%2$s", new Object[] { "HPS", "Prix: 5HPS." }));
			}
			else
			{
				HpsManager.removeBalance(player.getName(), 5);
				player.setFoodLevel(20);
				player.setHealth(player.getMaxHealth());
				player.setFireTicks(0);
				player.sendMessage(String.format("§4[§6%1$s§4] §6%2$s", new Object[] { "HPS §2Confirmation",
						"Vous avez été soigné, 5HPS débités." }));
			}
		}
		else if (args[0].equalsIgnoreCase("diam"))
		{
			if (args.length >= 2)
			{
				player.sendMessage(String.format("§4[§6%1$s§4] §6%2$s", new Object[] { "HPS",
						"Le /hps diam vous offre un équipement de combat a base de diamant." }));
				player.sendMessage(String.format("§4[§6%1$s§4] §6%2$s", new Object[] { "HPS", "Prix: 33HPS." }));
				player.sendMessage(String.format("§4[§6%1$s§4] §6%2$s", new Object[] { "HPS",
						"Assurez vous d'avoir de la place dans votre inventaire." }));
			}
			else
			{
				HpsManager.removeBalance(player.getName(), 33);
				giveDiamondStuff(player);
				player.sendMessage(String.format("§4[§6%1$s§4] §6%2$s", new Object[] { "HPS §2Confirmation",
						"Vous avez recu votre equipement, 33HPS débités." }));
			}
		}
		else if (args[0].equalsIgnoreCase("vip"))
		{
			if (args.length >= 2)
			{
				player.sendMessage(String.format("§4[§6%1$s§4] §6%2$s", new Object[] { "HPS",
						"Le VIP est un ensemble de commandes et d'avantages stratégiques pour simplifier votre vie." }));
				player.sendMessage(String.format("§4[§6%1$s§4] §6%2$s", new Object[] { "HPS",
						"Le TPA (/tpa <joueur>) permet de se téléporter a un joueur." }));
				player.sendMessage(String.format("§4[§6%1$s§4] §6%2$s", new Object[] { "HPS",
						"Le PTIME (/ptime <day|night>) permet de mettre le jour ou la nuit." }));
				player.sendMessage(String.format("§4[§6%1$s§4] §6%2$s", new Object[] { "HPS",
						"Le compass (/compass) permet de connaitre la direction dans laquelle nous marchons." }));
				player.sendMessage(String.format("§4[§6%1$s§4] §6%2$s", new Object[] { "HPS",
						"Le getpos (/getpos) permet de savoir ces positions." }));
				player.sendMessage(String.format("§4[§6%1$s§4] §6%2$s", new Object[] { "HPS",
						"Une réduction 33% sur toute la boutique!" }));
				player.sendMessage(String.format("§4[§6%1$s§4] §6%2$s", new Object[] { "HPS", "Un équipement en diamant." }));
				player.sendMessage(String.format("§4[§6%1$s§4] §6%2$s", new Object[] { "HPS", "Prix: 50HPS." }));
			}
			else if (!player.hasPermission("essentials.compass"))
			{
				HpsManager.removeBalance(player.getName(), 50);
				giveDiamondStuff(player);
				Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + player.getName() + " add essentials.tpa");
				Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + player.getName() + " add essentials.ptime");
				Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + player.getName() + " add essentials.compass");
				Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + player.getName() + " add essentials.getpos");
				Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + player.getName()
						+ " add BossShop.PriceMultiplier.Money1");
				player.sendMessage(String.format("§4[§6%1$s§4] §6%2$s", new Object[] { "HPS §2Confirmation",
						" Vous avez acheté le kit VIP, 50 HPS débités." }));
			}
			else
			{
				player.sendMessage(String.format("§4[§6%1$s§4] §6%2$s", new Object[] { "HPS", " Vous ètes déjà VIP." }));
			}

		}
		else if (args[0].equalsIgnoreCase("reduc"))
		{
			if (args.length >= 2)
			{
				player.sendMessage(String.format("§4[§6%1$s§4] §6%2$s", new Object[] { "HPS",
						"Il s'agit d'une réduction 33% sur toute la boutique et sur tout les achats!" }));
				player.sendMessage(String.format("§4[§6%1$s§4] §6%2$s", new Object[] { "HPS", "Prix: 25HPS." }));
				player.sendMessage(String.format("§4[§6%1$s§4] §6%2$s", new Object[] { "HPS",
						"Ne s'accumule pas avec la réduction VIP." }));
			}
			else if (!player.hasPermission("BossShop.PriceMultiplier.Money1"))
			{
				HpsManager.removeBalance(player.getName(), 25);
				Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + player.getName()
						+ " add BossShop.PriceMultiplier.Money1");
				player.sendMessage(String.format("§4[§6%1$s§4] §6%2$s", new Object[] { "HPS §2Confirmation",
						" Vous avez acheté la carte de réduction, 25 HPS débités." }));
			}
			else
			{
				player.sendMessage(String.format("§4[§6%1$s§4] §6%2$s",
						new Object[] { "HPS", " Vous possedez déjà cet avantage." }));
			}

		}
		else
		{
			player.sendMessage(String.format("§4[§6%1$s§4] §6%2$s",
					new Object[] { "HPS", "Argument inconnu, faites /hps liste." }));
		}
	}

	private void giveDiamondStuff(Player p)
	{
		final ItemStack[] items = { new ItemStack(Material.DIAMOND_HELMET, 1), new ItemStack(Material.DIAMOND_CHESTPLATE, 1),
				new ItemStack(Material.DIAMOND_LEGGINGS, 1), new ItemStack(Material.DIAMOND_BOOTS, 1) };

		p.getInventory().addItem(items);

		final ItemStack Epee = new ItemStack(Material.DIAMOND_SWORD);
		Epee.addUnsafeEnchantment(Enchantment.KNOCKBACK, 2);
		Epee.addUnsafeEnchantment(Enchantment.FIRE_ASPECT, 1);
		Epee.addUnsafeEnchantment(Enchantment.DAMAGE_UNDEAD, 2);
		p.getInventory().addItem(new ItemStack[] { Epee });

		final ItemStack Pomme = new ItemStack(Material.GOLDEN_APPLE);
		Pomme.setDurability((short) 1);
		p.getInventory().addItem(new ItemStack[] { Pomme });
	}

	@Override
	protected void onConsoleCommand(CommandSender sender, String[] args) throws HellException
	{
		Utils.sendMessage(sender, "Cette commande ne peut pas être utilisée depuis la {console}.");
	}

	@Override
	protected void sendUsage(CommandSender sender)
	{
	}
}