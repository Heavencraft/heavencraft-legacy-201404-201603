package fr.heavencraft.heavenproxy.commands;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import fr.heavencraft.heavenproxy.Utils;
import fr.heavencraft.heavenproxy.exceptions.HeavenException;

public class ListCommand extends HeavenCommand
{
	private final static String LIST_MESSAGE = "Il y a {%1$d} joueurs connectés :";
	
	private String allServers;
	
	public ListCommand()
	{
		super("list", "", new String[] { "online", "who", "glist" });
		
		allServers = "";
		
		for (ServerInfo server : ProxyServer.getInstance().getServers().values())
			allServers += (allServers.isEmpty() ? "" : "|") + server.getName();
	}
	
	@Override
	public void onCommand(CommandSender sender, String[] args) throws HeavenException
	{
		if (args.length == 1)
			listServer(sender, args[0]);
		else
			listAll(sender);
	}
	
	private void listAll(CommandSender sender)
	{
		sendList(sender, ProxyServer.getInstance().getPlayers());
		
		Utils.sendMessage(sender, "Pour avoir la liste d'un monde en paticulier :");
		Utils.sendMessage(sender, "/{list} <%1$s>", allServers);
	}
	
	private void listServer(CommandSender sender, String serverName)
	{
		ServerInfo server = ProxyServer.getInstance().getServerInfo(serverName);
		
		if (server == null)
			listAll(sender);
		else
			sendList(sender, server.getPlayers());
	}
	
	private static void sendList(CommandSender sender, Collection<ProxiedPlayer> players)
	{
		if (players.isEmpty())
		{
			Utils.sendMessage(sender, "Il n'y a personne ici...");
			return;
		}
		
		List<String> names = new ArrayList<String>();
		

		
		for (ProxiedPlayer player : players)
			names.add(player.getName());
		
		Collections.sort(names, new Comparator<String>() {

			@Override
			public int compare(String p1, String p2) {
				// TODO Auto-generated method stub
				return p1.compareToIgnoreCase(p2);
			}
		});
		
		
		String list = "";
		
		for (String name : names)
			list += (list.isEmpty() ? "" : ", ") + name;
		
		Utils.sendMessage(sender, LIST_MESSAGE, players.size());
		Utils.sendMessage(sender, list);
	}
}