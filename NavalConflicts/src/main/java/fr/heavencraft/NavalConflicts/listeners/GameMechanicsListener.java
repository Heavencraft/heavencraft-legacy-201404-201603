package fr.heavencraft.NavalConflicts.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Egg;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

import fr.heavencraft.NavalConflicts.NavalConflicts;
import fr.heavencraft.NavalConflicts.Enums.DeathType;
import fr.heavencraft.NavalConflicts.GameMechanics.Deaths;
import fr.heavencraft.NavalConflicts.Handlers.Lobby;
import fr.heavencraft.NavalConflicts.Handlers.Lobby.GameState;
import fr.heavencraft.NavalConflicts.Handlers.Player.NCPlayer;
import fr.heavencraft.NavalConflicts.Handlers.Player.NCPlayerManager;
import fr.heavencraft.NavalConflicts.Handlers.Potions.PotionEffects;

public class GameMechanicsListener implements Listener{
	public GameMechanicsListener()
	{
		Bukkit.getPluginManager().registerEvents(this, NavalConflicts.getInstance());
	}

	@EventHandler
	public void onPlayerDamageByEntity(EntityDamageByEntityEvent  event) {

		//Uniquement effecuté si le dégat est justifié:

		if (event.getEntity() instanceof Player)
		{
			Player victim = (Player) event.getEntity();
			Player killer = null;			
			// Verifier si la victime est en jeu....
			if (Lobby.isInGame(victim))
			{
				// Par defaut, on admet que c'est un dégat corps a coprs
				DeathType death = DeathType.Melee;

				// L'origine du dégat provient d'un humain?
				if (event.getDamager() instanceof Player)
					killer = (Player) event.getDamager();
				// L'origine du dégat provient d'une flèche?
				else if (event.getDamager() instanceof Arrow)
				{
					victim = (Player) event.getEntity();
					Arrow arrow = (Arrow) event.getDamager();

					// Le tireur est il humain?
					if (arrow.getShooter() instanceof Player)
					{
						killer = (Player) arrow.getShooter();
						death = DeathType.Arrow;
					}
					// L'origine du dégat provient d'une Boule de neige?
				} else if (event.getDamager() instanceof Snowball)
				{
					victim = (Player) event.getEntity();
					Snowball sb = (Snowball) event.getDamager();

					// Le tireur est il humain?
					if (sb.getShooter() instanceof Player)
					{
						killer = (Player) sb.getShooter();
						death = DeathType.Gun;
					}
				} else if (event.getDamager() instanceof Egg)
				{
					victim = (Player) event.getEntity();
					Egg egg = (Egg) event.getDamager();

					// Le tireur est il humain??
					if (egg.getShooter() instanceof Player)
					{
						killer = (Player) egg.getShooter();
						death = DeathType.Gun;
					}
				}

				// S'assurer qu'a la fin, le tueur est humain
				if (killer instanceof Player && Lobby.isInGame(killer))
				{
					// If the Player got hurt by a player before the game
					// started, just cancel it.
					if (Lobby.getGameState() != GameState.Started)
					{
						event.setDamage(0);
						event.setCancelled(true);
					}

					// If the game has started lets start watching
					else
					{
						// Make sure they arn't on the same team
						if (!Lobby.oppositeTeams(killer, victim))
						{
							event.setDamage(0);
							event.setCancelled(true);
						} else
						{
							NCPlayer IPV = NCPlayerManager.getNCPlayer(victim);
							NCPlayer IPK = NCPlayerManager.getNCPlayer(killer);
							IPV.setLastDamager(killer);

							// If it was enough to kill the player
							if (victim.getHealth() - event.getDamage() <= 0)
							{
								event.setDamage(0);
								Deaths.playerDies(death, killer, victim);

							}
							// If the damage wasn't enough to kill them, lets
							// see what contact effects we need to apply
							else if (!IPK.getNCClass(IPK.getTeam()).getContactEffects().isEmpty())
								PotionEffects.addEffectOnContact(killer, victim); //TODO potionEffect
						}
					}
				} else
				{
					if (Lobby.getActiveArena().getSettings().hostileMobsTargetHumans())
					{
						// If it was enough to kill the player
						if (victim.getHealth() - event.getDamage() <= 0)
						{
							Deaths.playerDies(DeathType.Other, null, victim);

						}
					} else
					{
						event.setDamage(0);
						event.setCancelled(true);
					}
				}

			}

		}
	}


	// If the damage isn't done by an Entity attacking or by a projectile
	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerDamage(EntityDamageEvent e) {

		if (e.getEntity() instanceof Player)
		{
			Player victim = (Player) e.getEntity();

			// Make sure the victim is in Infected
			if (Lobby.isInGame(victim))
			{

				// Make sure the damage caused isn't from another entity as that
				// is handled in a different event listener
				if (e.getCause() != DamageCause.ENTITY_ATTACK && e.getCause() != DamageCause.PROJECTILE)
				{
					NCPlayer IPV = NCPlayerManager.getNCPlayer(victim);

					// If the Player got hurt during Voting, just cancel it.
					if (Lobby.getGameState() == GameState.Voting || Lobby.getGameState() == GameState.InLobby)
					{
						e.setDamage(0);
						e.setCancelled(true);
					}
					// If the game has started, lets do some stuff
					else if (Lobby.getGameState() == GameState.Started)
					{
						// Is the damage enough to kill the player?
						if (victim.getHealth() - e.getDamage() <= 0)
						{
							// If the stored last damager isn't null, we'll say
							// they're the killer
							if (IPV.getLastDamager() != null)
							{
								e.setDamage(0);
								Player killer = IPV.getLastDamager();

								// Not really sure how they died? Just say melee
								Deaths.playerDies(DeathType.Melee, killer, victim);
							}
							// Other wise, they just died by "Other"
							else
							{
								e.setDamage(0);
								Deaths.playerDies(DeathType.Other, null, victim);
							}
						}
					}
				}
			}
		}
	}



}
