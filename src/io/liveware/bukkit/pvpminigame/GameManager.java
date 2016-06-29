package io.liveware.bukkit.pvpminigame;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;

/**
 * Created by Mitchell on 6/28/2016.
 */
public class GameManager {

    private ArrayList<GameLobby> lobbies;
    private int id = 0;

    public GameManager() {
        lobbies = new ArrayList<GameLobby>();
    }

    public int createLobby() {
        GameLobby lobby = new GameLobby(id);
        lobbies.add(lobby);
        id++;

        return id - 1;
    }

    public void joinLobby(Player player, int lobbyId) {
        GameLobby l = getLobby(lobbyId);

        if(isPlayerInLobby(player)) {
            player.sendMessage("You can't join a lobby when you are already in one!");
        }else if(l.getMaxPlayers() == l.getPlayers().size()) {
            player.sendMessage("You can't join this lobby because it is full!");
        }else if(l.isStarted()) {
            player.sendMessage("You can't join that game because it has already started!");
        }else {
            l.addPlayer(player);
            player.sendMessage("Joined lobby #" + lobbyId);
        }
    }

    public void leaveLobby(Player player) {
        if(!isPlayerInLobby(player)) {
            player.sendMessage("Can't leave a lobby you aren't in!");
        }else {
            int iid = getLobby(player).getId();

            getLobby(player).removePlayer(player);
            player.sendMessage("Left lobby #" + iid);
        }
    }

    public boolean isPlayerInLobby(Player player) {
        for(GameLobby l : lobbies) {
            if(l.containsPlayer(player)) return true;
        }

        return false;
    }

    public GameLobby getLobby(Player player) {
        for(GameLobby l : lobbies) {
            if(l.containsPlayer(player)) return l;
        }

        return null;
    }

    public GameLobby getLobby(int iid) {
        for(GameLobby l : lobbies) {
            if(l.getId() == iid) return l;
        }

        return null;
    }

}
