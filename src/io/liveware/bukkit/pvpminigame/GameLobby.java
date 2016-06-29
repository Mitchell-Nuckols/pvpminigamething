package io.liveware.bukkit.pvpminigame;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import java.util.*;

/**
 * Created by Mitchell on 6/28/2016.
 */
public class GameLobby {
    private int id;

    private HashMap<UUID, GameTeam> players = new HashMap<UUID, GameTeam>();
    private HashMap<GameTeam, Integer> points = new HashMap<GameTeam, Integer>();

    private int maxPlayers = 2;
    private int minPlayers = 1;

    private boolean started;

    public GameLobby(int id) {
        this.id = id;
        points.put(GameTeam.BLUE, 0);
        points.put(GameTeam.RED, 0);
    }

    public void startGame() {
        started = true;
        broadcastMessage("The game has started!");

        for(UUID u : players.keySet()) {
            Player player = Bukkit.getPlayer(u);
            player.getInventory().clear();

            if(teamSize(GameTeam.BLUE) > teamSize(GameTeam.RED)) {
                players.put(u, GameTeam.RED);
            }else if(teamSize(GameTeam.BLUE) < teamSize(GameTeam.RED)) {
                players.put(u, GameTeam.BLUE);
            }else {
                players.put(u, GameTeam.RED);
            }

            GameHelper.giveArmor(player, getTeam(player));

            player.sendMessage("You joined team " + (getTeam(player) == GameTeam.RED ? ChatColor.RED + "RED" : ChatColor.BLUE + "BLUE"));
            player.setDisplayName("[" + ChatColor.GOLD + "#" + this.id + ChatColor.RESET + "]" + (getTeam(player) == GameTeam.RED ? ChatColor.RED : ChatColor.BLUE) + player.getDisplayName() + ChatColor.RESET);
        }
    }

    public void stopGame() {
        started = false;
        broadcastMessage("The game has ended!");

        if(getPoints(GameTeam.RED) > getPoints(GameTeam.BLUE)) {
            broadcastMessage(ChatColor.RED + "RED" + ChatColor.RESET + " team won!");
            broadcastMessage(GameTeam.RED, "YOU WON!");
            broadcastMessage(GameTeam.BLUE, "YOU LOST!");
        }else if(getPoints(GameTeam.RED) < getPoints(GameTeam.BLUE)) {
            broadcastMessage(ChatColor.BLUE + "BLUE" + ChatColor.RESET + " team won!");
            broadcastMessage(GameTeam.BLUE, "YOU WON!");
            broadcastMessage(GameTeam.RED, "YOU LOST!");
        }

        broadcastMessage("The score was " + ChatColor.RED + "RED" + ChatColor.RESET + ":" + ChatColor.GOLD + getPoints(GameTeam.RED) + ChatColor.RESET + " | " + ChatColor.BLUE + "BLUE" + ChatColor.RESET + ":" + ChatColor.GOLD + getPoints(GameTeam.BLUE));

        for(UUID u : players.keySet()) {
            Bukkit.getPlayer(u).getInventory().clear();
            removePlayer(Bukkit.getPlayer(u));
        }

        points.clear();
        points.put(GameTeam.BLUE, 0);
        points.put(GameTeam.RED, 0);
    }

    public int getId() {return this.id;}
    public Set<UUID> getPlayers() {return this.players.keySet();}
    public int getMaxPlayers() {return this.maxPlayers;}
    public boolean isStarted() {return this.started;}
    public int getPoints(GameTeam team) {return this.points.get(team);}

    public void setMaxPlayers(int maxPlayers) {this.maxPlayers = maxPlayers;}
    public void setStarted(boolean started) {this.started = started;}
    public void setPoints(GameTeam team, int amount) {this.points.put(team, amount);}
    public void addPoint(GameTeam team) {this.points.put(team, this.points.get(team) + 1);}

    public void addPlayer(Player player) {
        players.put(player.getUniqueId(), GameTeam.SPECTATOR);
        broadcastMessage(player.getName() + " has joined the game!");

        if(players.size() > minPlayers && !started) {
            startGame();
        }
    }

    public void removePlayer(Player player) {

        GameTeam deadGuyTeam = getTeam(player); // I know this is a bit janky and I should fix it, but fuck you

        players.remove(player.getUniqueId());
        if((players.size() < minPlayers || players.size() == 1) && started) {
            if(getPoints(GameTeam.RED) > getPoints(GameTeam.BLUE)) {
                player.sendMessage(ChatColor.RED + "RED" + ChatColor.RESET + " team won!");
                player.sendMessage(GameTeam.RED == deadGuyTeam ? "YOU WON!" : "YOU LOST!");
            }else if(getPoints(GameTeam.RED) < getPoints(GameTeam.BLUE)) {
                player.sendMessage(ChatColor.BLUE + "BLUE" + ChatColor.RESET + " team won!");
                player.sendMessage(GameTeam.BLUE == deadGuyTeam ? "YOU WON!" : "YOU LOST!");
            }

            player.sendMessage("The score was " + ChatColor.RED + "RED" + ChatColor.RESET + ":" + ChatColor.GOLD + getPoints(GameTeam.RED) + ChatColor.RESET + " | " + ChatColor.BLUE + "BLUE" + ChatColor.RESET + ":" + ChatColor.GOLD + getPoints(GameTeam.BLUE));

            stopGame();

        }
        player.setDisplayName(player.getName());
        broadcastMessage(player.getName() + " has left the game!");
    }

    public boolean containsPlayer(Player player) {
        return players.keySet().contains(player.getUniqueId());
    }

    public boolean containsPlayer(Player player, GameTeam team) {
        return (players.keySet().contains(player.getUniqueId()) && players.get(player.getUniqueId()) == team);
    }

    public GameTeam getTeam(Player player) {
        return players.get(player.getUniqueId());
    }

    public void broadcastMessage(String msg) {
        for(UUID u : players.keySet()) {
            Bukkit.getPlayer(u).sendMessage(msg);
        }
    }

    public void broadcastMessage(GameTeam team, String msg) {
        for(UUID u : players.keySet()) {
            if(containsPlayer(Bukkit.getPlayer(u), team)) Bukkit.getPlayer(u).sendMessage(msg);
        }
    }

    public void setTeam(Player player, GameTeam team) {
        players.put(player.getUniqueId(), team);
    }

    public int teamSize(GameTeam team) {
        int size = 0;

        for(GameTeam g : players.values()) {
            if(g == team) size++;
        }

        return size;
    }
}
