package fr.heavencraft.heavenrp;

import fr.heavencraft.heavenrp.antilag.AntiCheatListener;
import fr.heavencraft.heavenrp.antilag.AntiLagListener;
import fr.heavencraft.heavenrp.economy.BourseCommand;
import fr.heavencraft.heavenrp.economy.EconomyListener;
import fr.heavencraft.heavenrp.economy.LivretProSignListener;
import fr.heavencraft.heavenrp.economy.LivretSignListener;
import fr.heavencraft.heavenrp.economy.LivretproCommand;
import fr.heavencraft.heavenrp.economy.PayerCommand;
import fr.heavencraft.heavenrp.economy.enterprise.EntrepriseCommand;
import fr.heavencraft.heavenrp.general.ChatListener;
import fr.heavencraft.heavenrp.general.RecipeManager;
import fr.heavencraft.heavenrp.general.ServerListener;
import fr.heavencraft.heavenrp.general.WatchListener;
import fr.heavencraft.heavenrp.homes.BuyhomeCommand;
import fr.heavencraft.heavenrp.homes.HomeCommand;
import fr.heavencraft.heavenrp.homes.SethomeCommand;
import fr.heavencraft.heavenrp.homes.TphomeCommand;
import fr.heavencraft.heavenrp.horses.ChevalCommand;
import fr.heavencraft.heavenrp.horses.HorsesListener;
import fr.heavencraft.heavenrp.hps.HeadCommand;
import fr.heavencraft.heavenrp.hps.HpsCommand;
import fr.heavencraft.heavenrp.provinces.ProvinceListener;
import fr.heavencraft.heavenrp.provinces.ProvinceSignListener;
import fr.heavencraft.heavenrp.teleport.SpawnCommand;
import fr.heavencraft.heavenrp.teleport.TutoCommand;
import fr.heavencraft.heavenrp.warps.WarpCommand;
import fr.heavencraft.heavenrp.warps.WarpSignListener;
import fr.heavencraft.heavenrp.worlds.WorldsListener;
import fr.heavencraft.heavenrp.worlds.WorldsManager;
import fr.lorgan17.heavenrp.commands.admin.CreacheatCommand;
import fr.lorgan17.heavenrp.commands.admin.PoofCommand;
import fr.lorgan17.heavenrp.commands.admin.SoundCommand;
import fr.lorgan17.heavenrp.commands.admin.SpawnmobCommand;
import fr.lorgan17.heavenrp.commands.admin.TravauxCommand;
import fr.lorgan17.heavenrp.commands.admin.WorldCommand;
import fr.lorgan17.heavenrp.commands.mod.EndercheatCommand;
import fr.lorgan17.heavenrp.commands.mod.EventCommand;
import fr.lorgan17.heavenrp.commands.mod.InventoryCommand;
import fr.lorgan17.heavenrp.commands.mod.ModpackCommand;
import fr.lorgan17.heavenrp.commands.mod.Pvp4Command;
import fr.lorgan17.heavenrp.commands.mod.PvpCommand;
import fr.lorgan17.heavenrp.commands.mod.RoucoupsCommand;
import fr.lorgan17.heavenrp.commands.mod.TpCommand;
import fr.lorgan17.heavenrp.commands.mod.TphereCommand;
import fr.lorgan17.heavenrp.commands.mod.TpposCommand;
import fr.lorgan17.heavenrp.commands.user.EncheresCommand;
import fr.lorgan17.heavenrp.commands.user.LicenceCommand;
import fr.lorgan17.heavenrp.commands.user.MaireCommand;
import fr.lorgan17.heavenrp.commands.user.MairesCommand;
import fr.lorgan17.heavenrp.commands.user.ParcelleCommand;
import fr.lorgan17.heavenrp.commands.user.tpa.AccepterCommand;
import fr.lorgan17.heavenrp.commands.user.tpa.RejoindreCommand;

public class InitManager
{
	public static void init()
	{
		// Anti-lag
		new AntiCheatListener();
		new AntiLagListener();
		
		// Economy
		new BourseCommand();
		new EconomyListener();
		new LivretproCommand();
		new LivretProSignListener();
		new LivretSignListener();
		new PayerCommand();
		
		// Economy.Enterprise
		new EntrepriseCommand();
		
		// General
		new ChatListener();
		new RecipeManager();
		new ServerListener();
		new WatchListener();
		
		// Homes
		new BuyhomeCommand();
		new HomeCommand();
		new SethomeCommand();
		new TphomeCommand();
		
		// Horses
		new ChevalCommand();
		new HorsesListener();
		
		// HPs
		new HeadCommand();
		new HpsCommand();
		
		// Provinces
		new ProvinceListener();
		new ProvinceSignListener();
		
		// Stores
		
		// Teleport
		new SpawnCommand();
		new TutoCommand();
		
		// Warps
		new WarpCommand();
		new WarpSignListener();
		
		/*
		 * 
		 */
		
		// Tpa
		new RejoindreCommand();
		new AccepterCommand();
		
		// Commandes joueurs
		new EncheresCommand();
		new LicenceCommand();
		
		new MaireCommand();
		new MairesCommand();
		new ParcelleCommand();
		
		
		// Commandes modérateurs
		new EndercheatCommand();
		new EventCommand();
		new InventoryCommand();
		new ModpackCommand();
		new Pvp4Command();
		new PvpCommand();
		new RoucoupsCommand();
		new TpCommand();
		new TphereCommand();
		new TpposCommand();
		
		// Commandes administrateurs
		new CreacheatCommand();
		new PoofCommand();
		new SoundCommand();
		new SpawnmobCommand();
		new TravauxCommand();
		new WorldCommand();
		
		/*
		 * 
		 */
		
		// Worlds
		WorldsManager.init();
		new WorldsListener();
	}
}