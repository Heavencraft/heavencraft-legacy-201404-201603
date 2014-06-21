package fr.heavencraft.heavenrp;

import fr.heavencraft.commands.AccepterCommand;
import fr.heavencraft.commands.CreacheatCommand;
import fr.heavencraft.commands.EndercheatCommand;
import fr.heavencraft.commands.FillCommand;
import fr.heavencraft.commands.GcCommand;
import fr.heavencraft.commands.HeadCommand;
import fr.heavencraft.commands.InventoryCommand;
import fr.heavencraft.commands.PoofCommand;
import fr.heavencraft.commands.RejoindreCommand;
import fr.heavencraft.commands.RoucoupsCommand;
import fr.heavencraft.commands.SpawnmobCommand;
import fr.heavencraft.commands.TpCommand;
import fr.heavencraft.commands.TphereCommand;
import fr.heavencraft.commands.TpposCommand;
import fr.heavencraft.commands.TpworldCommand;
import fr.heavencraft.heavenrp.commands.economy.BourseCommand;
import fr.heavencraft.heavenrp.commands.economy.EntrepriseCommand;
import fr.heavencraft.heavenrp.commands.economy.LivretproCommand;
import fr.heavencraft.heavenrp.commands.economy.PayerCommand;
import fr.heavencraft.heavenrp.commands.homes.BuyhomeCommand;
import fr.heavencraft.heavenrp.commands.homes.HomeCommand;
import fr.heavencraft.heavenrp.commands.homes.SethomeCommand;
import fr.heavencraft.heavenrp.commands.homes.TphomeCommand;
import fr.heavencraft.heavenrp.commands.horses.ChevalCommand;
import fr.heavencraft.heavenrp.commands.hps.HpsCommand;
import fr.heavencraft.heavenrp.commands.key.KeyCommand;
import fr.heavencraft.heavenrp.commands.teleport.SpawnCommand;
import fr.heavencraft.heavenrp.commands.teleport.TutoCommand;
import fr.heavencraft.heavenrp.commands.warps.WarpCommand;
import fr.heavencraft.heavenrp.economy.EconomyListener;
import fr.heavencraft.heavenrp.economy.LivretProSignListener;
import fr.heavencraft.heavenrp.economy.LivretSignListener;
import fr.heavencraft.heavenrp.general.PumpkinLampListener;
import fr.heavencraft.heavenrp.general.RecipeManager;
import fr.heavencraft.heavenrp.general.ServerListener;
import fr.heavencraft.heavenrp.general.WatchListener;
import fr.heavencraft.heavenrp.general.users.UserListener;
import fr.heavencraft.heavenrp.horses.HorsesListener;
import fr.heavencraft.heavenrp.key.DonjonSignListener;
import fr.heavencraft.heavenrp.provinces.ProvinceListener;
import fr.heavencraft.heavenrp.provinces.ProvinceSignListener;
import fr.heavencraft.heavenrp.warps.WarpSignListener;
import fr.heavencraft.heavenrp.worlds.WorldsListener;
import fr.heavencraft.heavenrp.worlds.WorldsManager;
import fr.heavencraft.listeners.AntiCheatListener;
import fr.heavencraft.listeners.AntiLagListener;
import fr.heavencraft.listeners.ColoredSignsListener;
import fr.heavencraft.listeners.NoChatListener;
import fr.heavencraft.listeners.RedstoneLampListener;
import fr.heavencraft.tasks.SaveTask;
import fr.lorgan17.heavenrp.commands.mod.EventCommand;
import fr.lorgan17.heavenrp.commands.mod.ModpackCommand;
import fr.lorgan17.heavenrp.commands.mod.Pvp4Command;
import fr.lorgan17.heavenrp.commands.mod.PvpCommand;
import fr.lorgan17.heavenrp.commands.user.EncheresCommand;
import fr.lorgan17.heavenrp.commands.user.LicenceCommand;
import fr.lorgan17.heavenrp.commands.user.MaireCommand;
import fr.lorgan17.heavenrp.commands.user.MairesCommand;
import fr.lorgan17.heavenrp.commands.user.ParcelleCommand;
import fr.lorgan17.heavenrp.listeners.JumpListener;
import fr.lorgan17.heavenrp.listeners.LampadaireListener;
import fr.lorgan17.heavenrp.listeners.LinkListener;
import fr.lorgan17.heavenrp.listeners.PVP4Manager;
import fr.lorgan17.heavenrp.listeners.PVPManager;
import fr.lorgan17.heavenrp.listeners.SnowballListener;
import fr.manu67100.heavenrp.laposte.WGHandler.WGRegionEventsListener;
import fr.manu67100.heavenrp.laposte.commands.admin.addOfficeCommand;
import fr.manu67100.heavenrp.laposte.commands.admin.listPosteCommand;
import fr.manu67100.heavenrp.laposte.commands.admin.reloadRegion;
import fr.manu67100.heavenrp.laposte.commands.admin.removeOfficeCommand;
import fr.manu67100.heavenrp.laposte.commands.user.colisCommand;
import fr.manu67100.heavenrp.laposte.handlers.PopupMenuAPI;
import fr.manu67100.heavenrp.laposte.handlers.PostOfficeManager;
import fr.manu67100.heavenrp.laposte.listeners.InventoryListener;
import fr.manu67100.heavenrp.laposte.listeners.PostOfficeListener;
import fr.manu67100.heavenrp.laposte.listeners.SignListener;

public class InitManager
{
	public static void init()
	{
		initCommands();
		initListeners();

		new SaveTask();
	}

	private static void initCommands()
	{
		/*
		 * from HeavenCore
		 */

		new AccepterCommand();
		new CreacheatCommand();
		new EndercheatCommand();
		new FillCommand();
		new GcCommand();
		new HeadCommand();
		new InventoryCommand();
		new PoofCommand();
		new RejoindreCommand();
		new RoucoupsCommand();
		new SpawnmobCommand();
		new TpCommand();
		new TphereCommand();
		new TpposCommand();
		new TpworldCommand();

		/*
		 * from HeavenRP
		 */

		// Economy
		new BourseCommand();
		new EntrepriseCommand();
		new LivretproCommand();
		new PayerCommand();

		// Homes
		new BuyhomeCommand();
		new HomeCommand();
		new SethomeCommand();
		new TphomeCommand();

		// Horses
		new ChevalCommand();

		// HPs
		new HpsCommand();

		// Key
		new KeyCommand();

		// Teleport
		new SpawnCommand();
		new TutoCommand();

		// Warp
		new WarpCommand();

		/*
		 * A trier
		 */

		// Commandes modérateurs
		new EventCommand();
		new ModpackCommand();
		new Pvp4Command();
		new PvpCommand();

		// Commandes joueurs
		new EncheresCommand();
		new LicenceCommand();
		new MaireCommand();
		new MairesCommand();
		new ParcelleCommand();
		
		// Commandes Poste
		new colisCommand();
		new reloadRegion();
		new addOfficeCommand();
		new removeOfficeCommand();
		new listPosteCommand();
	}

	private static void initListeners()
	{
		/*
		 * from HeavenCore
		 */

		new AntiCheatListener();
		new AntiLagListener();
		new ColoredSignsListener();
		new NoChatListener();
		new RedstoneLampListener();

		/*
		 * HeavenRP
		 */

		// Economy
		new EconomyListener();
		new LivretProSignListener();
		new LivretSignListener();

		// General
		new PumpkinLampListener();
		new RecipeManager();
		new ServerListener();
		new WatchListener();

		// Users
		new UserListener();

		// Horses
		new HorsesListener();

		// Key
		new DonjonSignListener();

		// Provinces
		new ProvinceListener();
		new ProvinceSignListener();

		// Warps
		new WarpSignListener();

		// Worlds
		WorldsManager.init();
		new WorldsListener();

		/*
		 * A trier
		 */

		// Listeners
		new JumpListener();
		new LampadaireListener();
		new LinkListener();
		new PVP4Manager();
		new PVPManager();
		new SnowballListener();

		// new AdminShop();
		
		// La Poste
		
		new PostOfficeListener();
		new WGRegionEventsListener();
		new SignListener();
		new InventoryListener();
		new PopupMenuAPI();
		PostOfficeManager.LoadOffices();
	}
}