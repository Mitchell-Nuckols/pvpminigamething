package io.liveware.bukkit.pvpminigame;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;

/**
 * Created by Mitchell on 6/28/2016.
 */
public class GameListener implements Listener {

    GameManager manager = PvPMinigame.manager;

    @EventHandler
    public void entityDamageByEntityEvent(EntityDamageByEntityEvent event) {
        if((event.getDamager() instanceof Player) && (event.getEntity() instanceof Player)) {
            Player attacker = (Player) event.getDamager();
            Player attacked = (Player) event.getEntity();

            if(manager.isPlayerInLobby(attacker) && manager.isPlayerInLobby(attacked)) {
                if(manager.getLobby(attacker).getId() == manager.getLobby(attacked).getId()) {
                    if(manager.getLobby(attacker).getTeam(attacker) == manager.getLobby(attacked).getTeam(attacked) || (manager.getLobby(attacker).getTeam(attacker) == GameTeam.SPECTATOR || manager.getLobby(attacked).getTeam(attacked) == GameTeam.SPECTATOR)) {
                        event.setCancelled(true);
                    }else {
                        manager.getLobby(attacker).broadcastMessage(attacker.getDisplayName() + " attacked " + attacked.getDisplayName() + " for " + event.getDamage());
                    }
                }else {
                    event.setCancelled(true);
                }
            }
        }else {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void entityKillEntityEvent(EntityDeathEvent event) {
        if(event.getEntity() instanceof Player) {
            Player deadGuy = (Player) event.getEntity();

            if(manager.isPlayerInLobby(deadGuy)) {
                GameLobby g = manager.getLobby(deadGuy);

                GameTeam deadGuyTeam = g.getTeam(deadGuy);

                g.broadcastMessage(deadGuy.getDisplayName() + " has DIED! +1 point to the " + (deadGuyTeam == GameTeam.BLUE ? ChatColor.RED + "RED" : ChatColor.BLUE + "BLUE") + ChatColor.RESET + " team!");
                g.addPoint(deadGuyTeam == GameTeam.BLUE ? GameTeam.RED : GameTeam.BLUE);

                g.removePlayer(deadGuy);
            }
        }
    }
}
