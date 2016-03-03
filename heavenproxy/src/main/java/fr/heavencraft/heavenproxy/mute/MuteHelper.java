package fr.heavencraft.heavenproxy.mute;

import fr.heavencraft.heavenproxy.Utils;
import fr.heavencraft.heavenproxy.async.QueriesHandler;
import fr.heavencraft.heavenproxy.database.users.UpdateUserMutedUntilQuery;
import fr.heavencraft.heavenproxy.database.users.User;
import fr.heavencraft.heavenproxy.exceptions.PlayerNotConnectedException;
import net.md_5.bungee.api.CommandSender;

public class MuteHelper
{
    private static final String MUTE_NOTIFICATION = "Vous avez été mute pour {%1$s} minutes par {%2$s}.";
    private static final String MUTE_NOTIFICATION_CHEN = "Vous avez été mute pour {%1$s} minutes par {le Prof. Chen}.";

    public static void mute(final User user, final int nbMinutes, final CommandSender sender)
    {
        QueriesHandler.addQuery(new UpdateUserMutedUntilQuery(user, nbMinutes)
        {
            @Override
            public void onSuccess()
            {
                final String playerName = user.getName();
                Utils.sendMessage(sender, "Le joueur {%1$s} a été mute pour {%2$s} minutes.", playerName, nbMinutes);

                try
                {
                    Utils.sendMessage(Utils.getPlayer(playerName), MUTE_NOTIFICATION, nbMinutes, sender.getName());
                }
                catch (final PlayerNotConnectedException ex)
                {
                }
            }
        });
    }

    public static void mute(final User user, final int nbMinutes)
    {
        QueriesHandler.addQuery(new UpdateUserMutedUntilQuery(user, nbMinutes)
        {
            @Override
            public void onSuccess()
            {
                try
                {
                    Utils.sendMessage(Utils.getPlayer(user.getName()), MUTE_NOTIFICATION_CHEN, nbMinutes);
                }
                catch (final PlayerNotConnectedException ex)
                {
                }
            }
        });
    }
}