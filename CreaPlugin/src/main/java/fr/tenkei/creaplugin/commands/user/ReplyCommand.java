package fr.tenkei.creaplugin.commands.user;

public class ReplyCommand {
	

}

	/*private final static String FROM = "§d[de %1$s]§r %2$s";
	private final static String TO = "§d[à %1$s]§r %2$s";
	
	public ReplyCommand(FavPlugin plugin) {
		super("reply", plugin);
	}

	/*@Override
	protected void onPlayerCommand(Player player, String[] args)
			throws LorganException {
		if (args.length < 1)
		{
			sendUsage(player);
			return;
		}
		
		String nameExSender = _plugin.getUsersManager().getUser(player.getName()).getStringVariable("lastSender");
		
		Player exSender = FavPlugin.getPlayer(nameExSender);
		
		if(exSender == null || nameExSender == null)
		{
			FavPlugin.sendMessage(player, "{La personne à laquelle vous voulez parler est déconnectée.");
			return;
		}
		
		String message = "";
		
		for (int i = 0; i != args.length; i++)
			message += args[i] + " ";
		
		FavPlugin.sendMessage(player, String.format(TO, exSender.getName(), message));
		FavPlugin.sendMessage(exSender, String.format(FROM, player.getName(), message));
		
		_plugin.getUsersManager().getUser(exSender.getName()).setStringVariable("lastSender", player.getName());
	}

	@Override
	protected void onConsoleCommand(CommandSender sender, String[] args)
			throws LorganException {
		
	}

	@Override
	protected void sendUsage(CommandSender sender) {
		FavPlugin.sendMessage(sender, "/{r} <message> : Pour repondre à la dernière personne qui vous a parlé.");
	}

	@Override
	protected void onPlayerCommand(Player player, String[] args)
			throws MyException {
		
	}

	@Override
	protected void onConsoleCommand(CommandSender sender, String[] args)
			throws MyException {
	
	}

	@Override
	protected void sendUsage(CommandSender sender) {
		
	}

}*/
