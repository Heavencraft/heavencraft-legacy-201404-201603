package fr.heavencraft.heavenrp.stores;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.block.Chest;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import fr.heavencraft.Utils;
import fr.heavencraft.exceptions.HeavenException;
import fr.heavencraft.heavenrp.HeavenRP;
import fr.heavencraft.heavenrp.economy.bankaccount.BankAccountsManager;
import fr.heavencraft.heavenrp.economy.bankaccount.BankAccountsManager.BankAccount;
import fr.heavencraft.heavenrp.economy.bankaccount.BankAccountsManager.BankAccountType;
import fr.heavencraft.heavenrp.general.users.UsersManager;
import fr.heavencraft.heavenrp.general.users.UsersManager.User;

public class StoresManager
{
	private HeavenRP _plugin;

	private HashSet<Stock> _stocks = null;
	private HashSet<Store> _stores = null;
	
	private BlockFace[] _FACES;
	
	private File getStocksFile()
	{
		return new File(_plugin.getDataFolder(), File.separator + "shops" + File.separator + "stocks.txt");
	}
	
	private File getStoresFile()
	{
		return new File(_plugin.getDataFolder(), File.separator + "shops" + File.separator + "stores.txt");
	}
	
	public StoresManager(HeavenRP plugin)
	{
		_plugin = plugin;
		_stocks = new HashSet<Stock>();
		_stores = new HashSet<Store>();
		_FACES = new BlockFace[] {
				BlockFace.NORTH, BlockFace.SOUTH,
				BlockFace.WEST, BlockFace.EAST,
				BlockFace.UP, BlockFace.DOWN };
	}
	
	public void init()
	{
		try
		{
			File file = getStocksFile();
			BufferedReader reader;
			String line = "";
			
			if (file.exists())
			{
				reader = new BufferedReader(new FileReader(getStocksFile()));
				
				while ((line = reader.readLine()) != null)
				{
					if (line.trim().isEmpty())
						continue;
					
					Stock stock = new Stock(line);
					if (stock.isValid())
						_stocks.add(stock);
				}
				
				reader.close();
			}
			
			file = getStoresFile();
			line = "";
			if (file.exists())
			{
				reader = new BufferedReader(new FileReader(getStoresFile()));
				
				while ((line = reader.readLine()) != null)
				{
					if (line.trim().isEmpty())
						continue;
					
					Store store = new Store(_plugin, line);
					if (store.isValid())
						_stores.add(store);
				}
				
				reader.close();
			}
		}
		catch (Exception e)
		{
			System.out.println("Can't load shops data :");
			e.printStackTrace();
		}
	}
	
	public void saveStocks()
	{
		try
		{
			BufferedWriter writer = new BufferedWriter(new FileWriter(getStocksFile()));
			for (Stock stock : _stocks)
			{
				writer.write(stock.getSaveString() + "\n");
			}
			writer.close();
		}
		catch (Exception e)
		{
			System.out.println("Can't save stocks data :");
			e.printStackTrace();
		}
	}
	
	public void saveStores()
	{
		try
		{
			BufferedWriter writer = new BufferedWriter(new FileWriter(getStoresFile()));
			for (Store store : _stores)
			{
				writer.write(store.getSaveString() + "\n");
			}
			writer.close();
		}
		catch (Exception e)
		{
			System.out.println("Can't save stores data :");
			e.printStackTrace();
		}
	}

	public Stock getStock(String playerName, String storeName)
	{
		for (Stock stock : _stocks)
		{
			if (stock.getOwnerName().equalsIgnoreCase(playerName))
			{
				if (stock.getStoreName().equalsIgnoreCase(storeName))
				{
					return stock;
				}
			}
		}
		return null;
	}
	
	private Store getStore(Block block)
	{
		for (Store store : _stores)
		{
			if (Utils.blocksEquals(block, store.getSign().getBlock()))
				return store;
		}
		return null;
	}
	
	private Chest getRelativeChest(Block block)
	{
		for (BlockFace face : _FACES)
		{
			Block relativeBlock = block.getRelative(face);
			if (relativeBlock.getTypeId() != Material.CHEST.getId())
				continue;
			if (relativeBlock.getState() instanceof Chest)
				return (Chest) relativeBlock.getState();
		}
		return null;
	}
	
	public void onCreateStock(SignChangeEvent event)
	{
		Player player = event.getPlayer();
		String playerName = player.getName();
		Sign sign = (Sign) event.getBlock().getState();
		
		String chestName = event.getLine(1).toLowerCase().trim();
		if (chestName.length() < 3)
		{
			event.setLine(2, ChatColor.DARK_RED + "Nom invalide");
			event.setLine(3, "");
			return;
		}
		
		Chest chest = getRelativeChest(sign.getBlock());
		if (chest == null)
		{
			event.setLine(2, ChatColor.DARK_RED + "Coffre");
			event.setLine(3, ChatColor.DARK_RED + "introuvable");
			return;
		}
		
		for (Stock stock : _stocks)
		{
			if (stock.getOwnerName().equalsIgnoreCase(playerName) && stock.getStoreName().equalsIgnoreCase(chestName))
			{
				event.setLine(2, ChatColor.DARK_RED + "Nom déjà");
				event.setLine(3, ChatColor.DARK_RED + "utilisé");
				return;
			}
			if (Utils.blocksEquals(chest.getBlock(), stock.getChest().getBlock()))
			{
				event.setLine(2, ChatColor.DARK_RED + "Coffre déjà");
				event.setLine(3, ChatColor.DARK_RED + "utilisé");
				return;
			}
			if (Utils.blocksEquals(sign.getBlock(), stock.getLinkedSign().getBlock()))
			{
				event.setLine(2, ChatColor.DARK_RED + "Pancarte déjà");
				event.setLine(3, ChatColor.DARK_RED + "utilisée");
				return;
			}
		}
		
		Stock stock = new Stock(player.getName(), chestName, chest, sign);
		_stocks.add(stock);
		saveStocks();
		
		event.setLine(0, ChatColor.GREEN + "[Coffre]");
		event.setLine(1, chestName);
		event.setLine(2, "");
		event.setLine(3, "");
	}
	
	private ItemStack getItemFromInventory(Inventory inventory)
	{
		ItemStack items[] = inventory.getContents();
		ItemStack item = null;
		
		for (int i = 0; i < inventory.getSize(); i++)
		{
			if (items[i] != null)
			{
				item = items[i];
				break;
			}
		}
		if (item == null)
			return null;

		if (item.getType() == null)
			return null;
		
		if (item.getTypeId() == 0)
			return null;
		
		return item;
	}
	
	public void onCreateStore(SignChangeEvent event, boolean buying) throws HeavenException
	{
		Player player = event.getPlayer();
		String playerName = player.getName();
		Sign sign = (Sign) event.getBlock().getState();
		
		String chestName = event.getLine(1).toLowerCase().trim();
		String priceStr = event.getLine(2).trim();
		
		if (chestName.length() < 3)
		{
			event.setLine(2, ChatColor.DARK_RED + "Nom invalide");
			event.setLine(3, "");
			return;
		}
		
		String[] priceData = priceStr.split("/");
		
		if (priceData.length != 2)
		{
			event.setLine(2, ChatColor.DARK_RED + "Prix invalide");
			return;
		}
		
		if (!Utils.isInteger(priceData[0]) ||
				!Utils.isInteger(priceData[1]))
		{
			event.setLine(2, ChatColor.DARK_RED + "Prix invalide");
			return;
		}
		
		int price = Integer.parseInt(priceData[0]);
		int quantity = Integer.parseInt(priceData[1]);
		
		if (price < 1 || price > 9999 || quantity < 1 || quantity > 65)
		{
			event.setLine(2, ChatColor.DARK_RED + "Valeurs");
			event.setLine(3, ChatColor.DARK_RED + "incorrectes");
			return;
		}
		
		User user = UsersManager.getByName(player.getName());
		if (!user.hasDealerLicense())
		{
			event.setLine(2, ChatColor.DARK_RED + "Impossible !");
			sendMessage(player, "Vous n'avez pas payé votre licence de marchand !");
			return;
		}

		Stock linkedStock = getStock(playerName, chestName);
		if (linkedStock == null)
		{
			event.setLine(2, ChatColor.DARK_RED + "Nom de coffre");
			event.setLine(3, ChatColor.DARK_RED + "inexistant");
			return;
		}
		
		ItemStack item = getItemFromInventory(linkedStock.getChest().getInventory());
		if (item == null)
		{
			event.setLine(2, ChatColor.DARK_RED + "Coffre vide");
			event.setLine(3, ChatColor.DARK_RED + "");
			return;
		}

		Material material = item.getType();
		int materialData = -1;
		if (item.getData() != null)
			materialData = item.getDurability();

		if (material.getMaxStackSize() < quantity)
		{
			event.setLine(2, ChatColor.DARK_RED + "Quantité");
			event.setLine(3, ChatColor.DARK_RED + "trop grande");
			return;
		}
		
		Store newStore = new Store(player.getName(), chestName, sign, linkedStock, price, quantity, material, materialData, buying);
		_stores.add(newStore);
		saveStores();
		event.setLine(0, ChatColor.GREEN + (buying ? "[Achat]" : "[Magasin]"));
		if (materialData <= 0)
			event.setLine(1, ChatColor.BLUE + material.name());
		else
			event.setLine(1, ChatColor.BLUE + material.name() + ":" + materialData);
		event.setLine(2, ChatColor.BLUE.toString() + price + "PO pour " + quantity);
	}

	private void onStockDestroyed(Stock stock, Player player)
	{
		HashSet<Store> trashList = new HashSet<Store>();
		for (Store store : _stores)
		{
			if (store.getOwnerName().equalsIgnoreCase(stock.getOwnerName()) && store.getStoreName().equalsIgnoreCase(stock.getStoreName()))
			{
				trashList.add(store);
			}
		}
		if (trashList.isEmpty())
			return;
		
		if (player != null)
		{
			if (trashList.size() > 1)
				sendMessage(player, "{" + trashList.size() + "} magasins ont donc été supprimés.");
			else
				sendMessage(player, "Un magasin a donc été supprimé.");
		}
		for (Store store : trashList)
		{
			Sign sign = store.getSign();
			sign.setLine(0, "[Magasin]");
			sign.setLine(1, "Fermé");
			sign.setLine(2, "");
			sign.setLine(3, "");
			sign.update(true);
			
			_stores.remove(store);
		}
		
		saveStores();
	}
	
	public void onBlockDestroyed(Block block)
	{
		BlockState blockState = block.getState();
		if (blockState instanceof Chest)
		{
			for (Stock stock : _stocks)
			{
				if (Utils.blocksEquals(block, stock.getChest().getBlock()))
				{
					Player player = _plugin.getServer().getPlayer(stock.getOwnerName());
					if (player != null)
					{
						sendMessage(player, "Le coffre {" + stock.getStoreName() + "} a été détruit.");
					}
					stock.getLinkedSign().setLine(0, "[Coffre]");
					stock.getLinkedSign().setLine(1, "Inexistant");
					stock.getLinkedSign().update(true);
					
					onStockDestroyed(stock, player);
					_stocks.remove(stock);
					saveStocks();
					return;
				}
			}
		}
		if (blockState instanceof Sign)
		{
			for (Stock stock : _stocks)
			{
				if (Utils.blocksEquals(block, stock.getLinkedSign().getBlock()))
				{
					Player player = _plugin.getServer().getPlayer(stock.getOwnerName());
					if (player != null)
					{
						sendMessage(player, "Le panneau du coffre {" + stock.getStoreName() + "} a été détruit.");
					}
					
					onStockDestroyed(stock, player);
					_stocks.remove(stock);
					saveStocks();
					return;
				}
			}

			for (Store store : _stores)
			{
				if (Utils.blocksEquals(block, store.getSign().getBlock()))
				{
					Player player = _plugin.getServer().getPlayer(store.getOwnerName());
					if (player != null)
					{
						sendMessage(player, "Un magasin du coffre {" + store.getStoreName() + "} a été détruit.");
					}
					_stores.remove(store);
					saveStores();
					return;
				}
			}
		}
	}

	public void useStore(Player player, Block block, Sign sign) throws HeavenException
	{
		Store store = getStore(block);
		if (store == null)
			return;

		if (store.isBuyer())
			useBuyStore(player, store, block, sign);
		else
			useSellStore(player, store, block, sign);
	}

	@SuppressWarnings("deprecation")
	private void useSellStore(Player player, Store store, Block block, Sign sign) throws HeavenException
	{
		User user = UsersManager.getByName(player.getName());
		
		if (player.getInventory().firstEmpty() == -1)
		{
			sendMessage(player, "Vous n'avez pas de place dans votre inventaire !");
			return;
		}

		if (store.getLinkedStock().getItemQuantity(store.getMaterial().getId(), store.getMaterialData()) < store.getQuantity())
		{
			sendMessage(player, "Ce magasin est en rupture de stock.");
			return;
		}
		
		Player ownerPlayer = _plugin.getServer().getPlayer(store.getOwnerName());
		User ownerUser = UsersManager.getByName(store.getOwnerName());
		BankAccount ownerBank = BankAccountsManager.getBankAccount(store.getOwnerName(), BankAccountType.USER);
		
		if (!ownerUser.hasDealerLicense())
		{
			sendMessage(player, "Le propriétaire de ce magasin n'a pas payé sa licence.");
			return;
		}
		
		if (user.getBalance() < store.getPrice())
		{
			sendMessage(player, "Vous n'avez pas assez de pièces d'or.");
			return;
		}
		
		Block confirmBlock = getShopBlock(user);
		
		if (confirmBlock == null || !Utils.blocksEquals(block, confirmBlock))
		{
			setShopBlock(user, block);
			sendMessage(player, "Cliquez une seconde fois pour confirmer l'achat.");
			return;
		}

		setShopBlock(user, null);
		
		ItemStack items = null;
		if (store.getMaterialData() != -1)
			items = new ItemStack(store.getMaterial(), store.getQuantity(), (short)store.getMaterialData());
		else
			items = new ItemStack(store.getMaterial(), store.getQuantity());
		
		if (!store.getLinkedStock().removeStack(items))
		{
			sendMessage(player, "Erreur lors de l'achat. Merci de contacter un administrateur.");
			return;
		}
		
		user.updateBalance(-store.getPrice());
		int userMoney = user.getBalance();
		
		ownerBank.updateBalance(store.getPrice());
		int ownerUserMoney = ownerBank.getBalance();

		player.getInventory().addItem(items);
		player.updateInventory();
		
		if (ownerPlayer != null)
		{
			sendMessage(ownerPlayer, "{" + player.getName() + "} vient d'acheter dans votre magasin {" + store.getStoreName() + "}.");
			sendMessage(ownerPlayer, "Vous avez maintenant {" + ownerUserMoney + "} pièces d'or en banque.");
		}
		
		sendMessage(player, "Vous avez bien acheté {" + store.getQuantity() + " " + store.getMaterial().name() + "}.");
		sendMessage(player, "Vous avez maintenant {" +userMoney + "} pièces d'or.");
	}

	@SuppressWarnings("deprecation")
	private void useBuyStore(Player player, Store store, Block block, Sign sign) throws HeavenException
	{
		User user = UsersManager.getByName(player.getName());
		User ownerUser = UsersManager.getByName(store.getOwnerName());
		BankAccount ownerBank = BankAccountsManager.getBankAccount(store.getOwnerName(), BankAccountType.USER);

		if (!ownerUser.hasDealerLicense())
		{
			sendMessage(player, "Le propriétaire de ce magasin n'a pas payé sa licence.");
			return;
		}
		
		if (ownerBank.getBalance() < store.getPrice())
		{
			sendMessage(player, "Le propriétaire de ce magasin n'a pas assez d'or en banque.");
			return;
		}
		
		Chest chest = store.getLinkedStock().getChest();
		if (chest.getInventory().firstEmpty() == -1)
		{
			sendMessage(player, "Le coffre de ce magasin est plein.");
			return;
		}

		if (Stock.getItemQuantity(player, store) < store.getQuantity())
		{
			sendMessage(player, "Vous n'avez pas les objets requis dans votre inventaire.");
			return;
		}
		
		Block confirmBlock = getShopBlock(user);
		
		if (confirmBlock == null || !Utils.blocksEquals(block, confirmBlock))
		{
			setShopBlock(user, block);
			sendMessage(player, "Cliquez une seconde fois pour confirmer la vente.");
			return;
		}

		setShopBlock(user, null);
		
		ItemStack items = null;
		if (store.getMaterialData() != -1)
			items = new ItemStack(store.getMaterial(), store.getQuantity(), (short)store.getMaterialData());
		else
			items = new ItemStack(store.getMaterial(), store.getQuantity());
		
		if (!Stock.removeStack(player, items))
		{
			sendMessage(player, "Erreur lors de la vente. Merci de contacter un administrateur.");
			return;
		}
		player.updateInventory();

		ownerBank.updateBalance(-store.getPrice());
		int ownerUserMoney = ownerBank.getBalance();
		
		user.updateBalance(store.getPrice());
		int userMoney = user.getBalance();
		
		store.getLinkedStock().getChest().getInventory().addItem(items);

		Player ownerPlayer = _plugin.getServer().getPlayer(store.getOwnerName());
		if (ownerPlayer != null)
		{
			sendMessage(ownerPlayer, "{" + player.getName() + "} vient de vendre dans votre magasin {" + store.getStoreName() + "}.");
			sendMessage(ownerPlayer, "Vous avez maintenant {" + ownerUserMoney + "} pièces d'or en banque.");
		}
		
		sendMessage(player, "Vous avez bien vendu {" + store.getQuantity() + " " + store.getMaterial().name() + "}.");
		sendMessage(player, "Vous avez maintenant {" + userMoney + "} pièces d'or.");
	}
	
	private void sendMessage(Player player, String message)
	{
		message = message.replace("{", ChatColor.RED.toString());
		message = message.replace("}", ChatColor.GOLD.toString());
		player.sendMessage(ChatColor.GOLD + message);
	}

	private Map<String, Block> _blocks = new HashMap<String, Block>();
	
	private Block getShopBlock(User user)
	{
		return _blocks.get(user.getName());
	}
	
	private void setShopBlock(User user, Block block)
	{
		_blocks.remove(user.getName());
		
		if (_blocks != null)
			_blocks.put(user.getName(), block);
	}
}
