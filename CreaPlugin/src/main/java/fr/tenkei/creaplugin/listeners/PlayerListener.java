package fr.tenkei.creaplugin.listeners;

import java.util.HashSet;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityPortalEnterEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import fr.heavencraft.exceptions.HeavenException;
import fr.heavencraft.utils.ChatUtil;
import fr.tenkei.creaplugin.MyPlugin;
import fr.tenkei.creaplugin.managers.WorldsManager;
import fr.tenkei.creaplugin.users.User;
import fr.tenkei.creaplugin.users.UserProvider;
import fr.tenkei.creaplugin.utils.Stuff;

public class PlayerListener implements Listener
{

	private final MyPlugin _plugin;
	private final static byte PLAYER = 3;

	public PlayerListener(MyPlugin plugin)
	{
		_plugin = plugin;
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}

	@EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
	public void onPlayerMove(PlayerMoveEvent event)
	{
		if (event.getPlayer().getLocation().getBlockX() > MyPlugin.LIMITE_WORLDS
				|| event.getPlayer().getLocation().getBlockX() < -MyPlugin.LIMITE_WORLDS
				|| event.getPlayer().getLocation().getBlockZ() > MyPlugin.LIMITE_WORLDS
				|| event.getPlayer().getLocation().getBlockZ() < -MyPlugin.LIMITE_WORLDS)
			if (event.getPlayer().getWorld().equals(WorldsManager.getTheCreative())
					|| event.getPlayer().getWorld().equals(WorldsManager.getTheCreativeBiome()))
				event.getPlayer().teleport(event.getPlayer().getWorld().getSpawnLocation().add(0, 3, 0));
	}

	@EventHandler
	public void onEntityPortalEnter(EntityPortalEnterEvent event)
	{
		if (!(event.getEntity() instanceof Player))
			return;

		Player player = (Player) event.getEntity();
		_plugin.getManagers().getWarpsManager().teleport(player);

	}

	@EventHandler(ignoreCancelled = true)
	public void onPlayerInteract(PlayerInteractEvent event)
	{
		try
		{
			if (event.getAction() == Action.LEFT_CLICK_BLOCK || event.getAction() == Action.RIGHT_CLICK_BLOCK)
			{
				Block clickedBlock = event.getClickedBlock();
				BlockState blockState = clickedBlock.getState();
				if (blockState instanceof Sign)
				{
					Sign sign = (Sign) blockState;
					if (sign.getLine(0).endsWith(ChatColor.DARK_GREEN + "[AV]"))
					{
						_plugin.getManagers().getAVManager().achat(sign, event.getPlayer());
						event.setCancelled(true);
					}
					// else if (sign.getLine(0).endsWith(ChatColor.DARK_PURPLE + "[HOME]"))
					// {
					// UserManager.getUser(event.getPlayer().getName()).addHommeNumbre(clickedBlock);
					// event.setCancelled(true);
					// }
					else if (sign.getLine(0).endsWith(ChatColor.DARK_PURPLE + "[HEAD]"))
					{
						sellHead(event.getPlayer(), sign);
						event.setCancelled(true);
					}
					else if (sign.getLine(0).endsWith(ChatColor.DARK_PURPLE + "[BlocCheat]"))
					{
						sellBloc(event.getPlayer(), sign);
						event.setCancelled(true);
					}
					else if (sign.getLine(0).endsWith(ChatColor.DARK_PURPLE + "[BIERE]"))
					{
						sellBeer(event.getPlayer(), sign);
						event.setCancelled(true);
					}
				}
			}
		}
		catch (HeavenException ex)
		{
			ChatUtil.sendMessage(event.getPlayer(), ex.getMessage());
		}
	}

	@SuppressWarnings("deprecation")
	private void teleportPlayerOnBlock(Player player, Block block, HashSet<Byte> bytes)
	{
		if (block != null && block.getType() != Material.AIR)
		{
			while (!bytes.contains(block.getTypeId()) && block.getRelative(BlockFace.UP).getTypeId() != 0)
			{
				block = block.getRelative(BlockFace.UP);
				if (block == null)
					return;
				if (block.getY() <= 2)
					return;
				if (block.getY() >= 254)
					return;
			}
			Location blockLocation = block.getLocation();
			Location playerLocation = player.getLocation();
			Location newLocation = new Location(player.getWorld(), blockLocation.getX() + 0.5,
					blockLocation.getY() + 1.0, blockLocation.getZ() + 0.5, playerLocation.getYaw(),
					playerLocation.getPitch());
			player.teleport(newLocation);
		}
	}

	// creer un manager quand j'aurai le temps (Gestion des panneaux dans une class dédié)
	private void sellHead(Player player, Sign sign) throws HeavenException
	{
		User user = UserProvider.getUserByName(player.getName());

		int prix = Integer.parseInt(ChatColor.stripColor(sign.getLine(3)).replace(" Jetons", ""));

		if ((user.getInteractBlock() == null) || !Stuff.blocksEquals(user.getInteractBlock(), sign.getBlock()))
		{
			user.setInteractBlock(sign.getBlock());
			ChatUtil.sendMessage(Bukkit.getPlayer(player.getName()),
					"Cliquez une seconde fois pour confirmer l'achat de la téte " + sign.getLine(2) + " pour {" + prix
							+ "} Jetons.");
			return;
		}

		user.setInteractBlock(null);

		user.updateBalance(-prix);

		ItemStack head = new ItemStack(Material.SKULL_ITEM, 1, PLAYER);
		SkullMeta meta = (SkullMeta) head.getItemMeta();

		meta.setOwner(sign.getLine(2));

		head.setItemMeta(meta);
		player.getInventory().addItem(head);

		ChatUtil.sendMessage(player, "Vous étes maintenant l'heureux propriétaire de la téte de " + sign.getLine(2)
				+ ", cela vous a couté {" + prix + "} Jetons !");
		user.stateBalance();
	}

	// creer un manager quand j'aurai le temps (Gestion des panneaux dans une class dédié)
	private void sellBloc(Player player, Sign sign) throws HeavenException
	{
		User user = UserProvider.getUserByName(player.getName());

		int prix = Integer.parseInt(ChatColor.stripColor(sign.getLine(3)).replace(" Jetons", ""));

		if ((user.getInteractBlock() == null) || !Stuff.blocksEquals(user.getInteractBlock(), sign.getBlock()))
		{
			user.setInteractBlock(sign.getBlock());
			ChatUtil.sendMessage(Bukkit.getPlayer(player.getName()),
					"Cliquez une seconde fois pour confirmer l'achat du bloc pour {" + prix + "} Jetons.");
			return;
		}

		user.setInteractBlock(null);

		Material mat = sign.getBlock().getRelative(0, 0, -1).getType();

		if (mat == Material.WALL_SIGN)
		{
			mat = sign.getBlock().getRelative(1, 0, 0).getType();
			if (mat == Material.AIR)
				mat = sign.getBlock().getRelative(-1, 0, 0).getType();
		}
		else
		{
			if (mat == Material.AIR)
				mat = sign.getBlock().getRelative(0, 0, 1).getType();
		}

		if (mat == Material.AIR)
		{
			ChatUtil.sendMessage(Bukkit.getPlayer(player.getName()), "{Probléme de panneau. ( " + mat.name() + " )}");
			return;
		}

		user.updateBalance(-prix);

		player.getInventory().addItem(new ItemStack(mat)); // Faire selon orientation panneau

		ChatUtil.sendMessage(player, "Vous étes maintenant l'heureux propriétaire d'un bloc de {" + mat.name()
				+ "}, cela vous a couté {" + prix + "} Jetons !");
		user.stateBalance();

	}

	@SuppressWarnings("deprecation")
	private void sellBeer(Player player, Sign sign) throws HeavenException
	{

		User user = UserProvider.getUserByName(player.getName());

		if ((user.getInteractBlock() == null) || !Stuff.blocksEquals(user.getInteractBlock(), sign.getBlock()))
		{
			user.setInteractBlock(sign.getBlock());
			ChatUtil.sendMessage(Bukkit.getPlayer(player.getName()), "Tu veux ta biére jeune Panda ? Tapes moi, allé..");
			return;
		}

		user.setInteractBlock(null);

		user.updateBalance(-10);

		ItemStack is = new ItemStack(Material.POTION, 1); // La potion sera l'item é donner, et é modifier.
		PotionMeta meta = (PotionMeta) is.getItemMeta(); // On récupére la méta actuelle de l'item afin de la modifier.
		meta.addCustomEffect((new PotionEffect(PotionEffectType.CONFUSION, 200, 2)), true); // On ajoute un effet de
																							// confusion é la potion.
		meta.setMainEffect(PotionEffectType.FIRE_RESISTANCE); // L'aspect de la potion, ici fire_resistance donc un
																// aspect plutét orangé.
		is.setItemMeta(meta); // On applique les modifications sur l'item.
		player.getInventory().addItem(is); // On donne l'item au joueur.
		player.updateInventory(); // On met é jour son inventaire.

		ChatUtil.broadcastMessage(player.getName() + "} se m'est raide é la taverne !");
		user.stateBalance();
	}
}
