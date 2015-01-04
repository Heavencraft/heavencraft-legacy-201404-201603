package fr.heavencraft.heavenrp.scoreboards;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import fr.heavencraft.commands.HeavenCommand;
import fr.heavencraft.exceptions.HeavenException;
import fr.heavencraft.utils.ChatUtil;

public class Testcmd extends HeavenCommand{

	public Testcmd()
	{
		super("testcmd");
	}

	@Override
	protected void onPlayerCommand(Player player, String[] args) throws HeavenException {
		//ProvinceScoreboards.applyTeamColor(player);
		ChatUtil.sendMessage(player.getName(), "W U NO TRY SOMETHING ELSE?!");
		player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 255, 255));
		return;
	}

	@Override
	protected void onConsoleCommand(CommandSender sender, String[] args)
			throws HeavenException {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void sendUsage(CommandSender sender) {
		// TODO Auto-generated method stub
		
	}

}
