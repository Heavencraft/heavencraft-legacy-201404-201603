package fr.heavencraft.heavenrp.economy.enterprise;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.heavencraft.HeavenCommand;
import fr.heavencraft.Permissions;
import fr.heavencraft.Utils;
import fr.heavencraft.exceptions.HeavenException;
import fr.heavencraft.heavenrp.economy.enterprise.EnterprisesManager.Enterprise;
import fr.heavencraft.heavenrp.general.users.UsersManager;

public class EntrepriseCommand extends HeavenCommand
{
	public EntrepriseCommand()
	{
		super("entreprise");
	}

	@Override
	protected void onPlayerCommand(Player player, String[] args) throws HeavenException
	{
		onConsoleCommand(player, args);
	}

	@Override
	protected void onConsoleCommand(CommandSender sender, String[] args) throws HeavenException
	{
		if (args.length == 0)
		{
			sendUsage(sender);
			return;
		}
		
		switch (args.length)
		{
			case 2:
				// /entreprise <nom de l'entreprise> info
				if (args[1].equalsIgnoreCase("info"))
					info(sender, args[0]);

				// /entreprise <nom de l'entreprise> supprimer
				else if (args[1].equalsIgnoreCase("supprimer"))
					delete(sender, args[0]);
				break;
			case 3:
				// /entreprise <nom de l'entreprise> créer <nom du joueur>
				if (args[1].equalsIgnoreCase("créer"))
					create(sender, args[0], Utils.getExactName(args[2]));
				
				// /entreprise <nom de l'entreprise> +propriétaire <nom du joueur>
				else if (args[1].equalsIgnoreCase("+propriétaire"))
					addOwner(sender, args[0], Utils.getExactName(args[2]));

				// /entreprise <nom de l'entreprise> -propriétaire <nom du joueur>
				else if (args[1].equalsIgnoreCase("-propriétaire"))
					removeOwner(sender, args[0], Utils.getExactName(args[2]));
				
				// /entreprise <nom de l'entreprise> +membre <nom du joueur>
				else if (args[1].equalsIgnoreCase("+membre"))
					addMember(sender, args[0], Utils.getExactName(args[2]));

				// /entreprise <nom de l'entreprise> -membre <nom du joueur>
				else if (args[1].equalsIgnoreCase("-membre"))
					removeMember(sender, args[0], Utils.getExactName(args[2]));
				break;
		}
	}

	@Override
	protected void sendUsage(CommandSender sender)
	{
		if (sender.hasPermission(Permissions.ENTERPRISE_COMMAND))
		{
			Utils.sendMessage(sender, "/{entreprise} <nom de l'entreprise> créer <nom du propriétaire>");
			Utils.sendMessage(sender, "/{entreprise} <nom de l'entreprise> supprimer");
		}
		
		Utils.sendMessage(sender, "/{entreprise} <nom de l'entreprise> info");
		Utils.sendMessage(sender, "/{entreprise} <nom de l'entreprise> +propriétaire <nom du membre>");
		Utils.sendMessage(sender, "/{entreprise} <nom de l'entreprise> -propriétaire <nom du membre>");
		Utils.sendMessage(sender, "/{entreprise} <nom de l'entreprise> +membre <nom du membre>");
		Utils.sendMessage(sender, "/{entreprise} <nom de l'entreprise> -membre <nom du membre>");
	}
	
	private static void create(CommandSender sender, String name, String owner) throws HeavenException
	{
		if (!sender.hasPermission(Permissions.ENTERPRISE_COMMAND))
			return;
		
		EnterprisesManager.createEnterprise(name);
		Enterprise enterprise = EnterprisesManager.getEnterpriseByName(name);
		
		enterprise.addMember(UsersManager.getByName(owner), true);
		
		Utils.sendMessage(sender, "L'entreprise {%1$s} a été crée.", name);
	}
	
	private static void delete(CommandSender sender, String name) throws HeavenException
	{
		if (!sender.hasPermission(Permissions.ENTERPRISE_COMMAND))
			return;
		
		EnterprisesManager.deleteEnterprise(name);
		Utils.sendMessage(sender, "L'entreprise {%1$s} vient d'être supprimée.", name);
	}
	
	private static void info(CommandSender sender, String name) throws HeavenException
	{
		Enterprise enterprise = EnterprisesManager.getEnterpriseByName(name);
		String owners = "", members = "";

		for (String owner : enterprise.getMembers(true))
			owners += (owners == "" ? "" : ", ") + owner;
		
		for (String member : enterprise.getMembers(false))
			members += (members == "" ? "" : ", ") + member;
		
		Utils.sendMessage(sender, "Entreprise {%1$s}", enterprise.getName());
		Utils.sendMessage(sender, "Propriétaires : %1$s", owners);
		Utils.sendMessage(sender, "Membres : %1$s", members);
	}
	
	private static void addOwner(CommandSender sender, String name, String owner) throws HeavenException
	{
		Enterprise enterprise = EnterprisesManager.getEnterpriseByName(name);
		
		if (!enterprise.isMember(sender.getName(), true))
			throw new NotEnterpriseOwnerException(name);

		enterprise.addMember(UsersManager.getByName(owner), true);
		Utils.sendMessage(sender, "{%1$s} est désormais propriétaire de l'entreprise {%2$s}.", owner, name);
	}
	
	private static void removeOwner(CommandSender sender, String name, String owner) throws HeavenException
	{
		Enterprise enterprise = EnterprisesManager.getEnterpriseByName(name);
		
		if (!enterprise.isMember(sender.getName(), true))
			throw new NotEnterpriseOwnerException(name);
		
		enterprise.removeMember(UsersManager.getByName(owner));
		Utils.sendMessage(sender, "{%1$s} n'est désormais plus propriétaire de l'entreprise {%2$s}.", owner, name);
	}
	
	private static void addMember(CommandSender sender, String name, String member) throws HeavenException
	{
		Enterprise enterprise = EnterprisesManager.getEnterpriseByName(name);
		
		if (!enterprise.isMember(sender.getName(), true))
			throw new NotEnterpriseOwnerException(name);

		enterprise.addMember(UsersManager.getByName(member), false);
		Utils.sendMessage(sender, "{%1$s} est désormais membre de l'entreprise {%2$s}.", member, name);
	}
	
	private static void removeMember(CommandSender sender, String name, String member) throws HeavenException
	{
		Enterprise enterprise = EnterprisesManager.getEnterpriseByName(name);
		
		if (!enterprise.isMember(sender.getName(), true))
			throw new NotEnterpriseOwnerException(name);
		
		enterprise.removeMember(UsersManager.getByName(member));
		Utils.sendMessage(sender, "{%1$s} n'est désormais plus membre de l'entreprise {%2$s}.", member, name);
	}
}