package fr.tenkei.creaplugin.listeners;

import java.util.HashSet;

import net.minecraft.server.v1_7_R3.WorldServer;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.craftbukkit.v1_7_R3.CraftWorld;
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

import fr.tenkei.creaplugin.MyPlugin;
import fr.tenkei.creaplugin.exceptions.MyException;
import fr.tenkei.creaplugin.managers.WorldsManager;
import fr.tenkei.creaplugin.managers.entities.User;
import fr.tenkei.creaplugin.utils.Message;
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

	@SuppressWarnings("deprecation")
	@EventHandler
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
					else if (sign.getLine(0).endsWith(ChatColor.DARK_PURPLE + "[HOME]"))
					{
						_plugin.getManagers().getUserManager().getUser(event.getPlayer().getName())
								.addHommeNumbre(clickedBlock);
						event.setCancelled(true);
					}
					else if (sign.getLine(0).endsWith(ChatColor.DARK_PURPLE + "[NIGHT]"))
					{
						sellNight(event.getPlayer(), sign);
						event.setCancelled(true);
					}
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

			Player player = event.getPlayer();

			if (event.getAction() == Action.LEFT_CLICK_AIR)
			{
				User user = _plugin.getManagers().getUserManager().getUser(player.getName());
				if (player.getItemInHand().getType() == Material.COMPASS)
				{
					if (player.hasPermission(MyPlugin.builder) || !user.getStringVariable("boussole").isEmpty())
					{
						HashSet<Byte> bytes = new HashSet<Byte>();
						bytes.add((byte) 0);
						bytes.add((byte) 8);
						bytes.add((byte) 9);
						bytes.add((byte) 10);
						bytes.add((byte) 11);
						bytes.add((byte) 50);
						bytes.add((byte) 51);
						bytes.add((byte) 65);
						bytes.add((byte) 78);
						bytes.add((byte) 106);

						Block block = player.getTargetBlock(bytes, 100);
						teleportPlayerOnBlock(player, block, bytes);
						event.setCancelled(true);
					}
				}
			}

			if (event.isCancelled())
				return;

			if (event.getAction() != Action.RIGHT_CLICK_BLOCK)
				return;

			Block b = event.getClickedBlock();

			if (b.getTypeId() != 123)
				return;

			if (!event.hasItem() || event.getItem().getType() != Material.FLINT_AND_STEEL)
				return;

			WorldServer ws = ((CraftWorld) b.getWorld()).getHandle();

			boolean mem = ws.isStatic;
			if (!mem)
				ws.isStatic = true;

			b.setTypeIdAndData(Material.REDSTONE_LAMP_ON.getId(), (byte) 0, false);

			if (!mem)
				ws.isStatic = false;

			event.setCancelled(true);
		}
		catch (MyException ex)
		{
			Message.sendMessage(event.getPlayer(), ex.getMessage());
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

	// creer un manager quand j'aurai le temps (Gestion des panneaux dans une class dï¿½diï¿½)
	private void sellHead(Player player, Sign sign) throws MyException
	{
		User user = _plugin.getManagers().getUserManager().getUser(player.getName());

		int prix = Integer.parseInt(ChatColor.stripColor(sign.getLine(3)).replace(" Jetons", ""));

		if ((user.getInteractBlock() == null) || !Stuff.blocksEquals(user.getInteractBlock(), sign.getBlock()))
		{
			user.setInteractBlock(sign.getBlock());
			Message.sendMessage(Bukkit.getPlayer(player.getName()),
					"Cliquez une seconde fois pour confirmer l'achat de la tï¿½te " + sign.getLine(2) + " pour {" + prix
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

		Message.sendMessage(player, "Vous ï¿½tes maintenant l'heureux propriï¿½taire de la tï¿½te de " + sign.getLine(2)
				+ ", cela vous a coutï¿½ {" + prix + "} Jetons !");
		user.stateBalance();
	}

	// creer un manager quand j'aurai le temps (Gestion des panneaux dans une class dï¿½diï¿½)
	private void sellBloc(Player player, Sign sign) throws MyException
	{
		User user = _plugin.getManagers().getUserManager().getUser(player.getName());

		int prix = Integer.parseInt(ChatColor.stripColor(sign.getLine(3)).replace(" Jetons", ""));

		if ((user.getInteractBlock() == null) || !Stuff.blocksEquals(user.getInteractBlock(), sign.getBlock()))
		{
			user.setInteractBlock(sign.getBlock());
			Message.sendMessage(Bukkit.getPlayer(player.getName()),
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
			Message.sendError(Bukkit.getPlayer(player.getName()), "Problï¿½me de panneau. ( " + mat.name() + " )");
			return;
		}

		user.updateBalance(-prix);

		player.getInventory().addItem(new ItemStack(mat)); // Faire selon orientation panneau

		Message.sendMessage(player, "Vous ï¿½tes maintenant l'heureux propriï¿½taire d'un bloc de {" + mat.name()
				+ "}, cela vous a coutï¿½ {" + prix + "} Jetons !");
		user.stateBalance();

	}

	private void sellNight(Player player, Sign sign) throws MyException
	{

		User user = _plugin.getManagers().getUserManager().getUser(player.getName());

		if ((user.getInteractBlock() == null) || !Stuff.blocksEquals(user.getInteractBlock(), sign.getBlock()))
		{
			user.setInteractBlock(sign.getBlock());
			Message.sendMessage(Bukkit.getPlayer(player.getName()),
					"Cliquez une seconde fois pour confirmer l'achat de la nuit pour 200 Jetons.");
			return;
		}

		user.setInteractBlock(null);

		user.updateBalance(-200);

		player.getWorld().setTime(18000L);

		Message.broadcastEventMessage(player.getName() + "} vous souhaite une bonne nuit !");
		user.stateBalance();
	}

	@SuppressWarnings("deprecation")
	private void sellBeer(Player player, Sign sign) throws MyException
	{

		User user = _plugin.getManagers().getUserManager().getUser(player.getName());

		if ((user.getInteractBlock() == null) || !Stuff.blocksEquals(user.getInteractBlock(), sign.getBlock()))
		{
			user.setInteractBlock(sign.getBlock());
			Message.sendMessage(Bukkit.getPlayer(player.getName()), "Tu veux ta biï¿½re jeune Panda ? Tapes moi, allï¿½..");
			return;
		}

		user.setInteractBlock(null);

		user.updateBalance(-10);

		ItemStack is = new ItemStack(Material.POTION, 1); // La potion sera l'item ï¿½ donner, et ï¿½ modifier.
		PotionMeta meta = (PotionMeta) is.getItemMeta(); // On rï¿½cupï¿½re la mï¿½ta actuelle de l'item afin de la modifier.
		meta.addCustomEffect((new PotionEffect(PotionEffectType.CONFUSION, 200, 2)), true); // On ajoute un effet de
																							// confusion ï¿½ la potion.
		meta.setMainEffect(PotionEffectType.FIRE_RESISTANCE); // L'aspect de la potion, ici fire_resistance donc un
																// aspect plutï¿½t orangï¿½.
		is.setItemMeta(meta); // On applique les modifications sur l'item.
		player.getInventory().addItem(is); // On donne l'item au joueur.
		player.updateInventory(); // On met ï¿½ jour son inventaire.

		Message.broadcastEventMessage(player.getName() + "} se m'est raide ï¿½ la taverne !");
		user.stateBalance();
	}
}
