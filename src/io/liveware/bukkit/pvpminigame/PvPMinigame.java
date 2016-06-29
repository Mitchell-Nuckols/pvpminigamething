package io.liveware.bukkit.pvpminigame;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by Mitchell on 6/28/2016.
 */
public class PvPMinigame extends JavaPlugin {

    public static GameManager manager = new GameManager();

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new GameListener(), this);

        getLogger().info("Plugin started");
    }

    @Override
    public void onDisable() {
        getLogger().info("Plugin shutdown");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(label.equalsIgnoreCase("join")) {
            if(!(sender instanceof Player)) {
                sender.sendMessage("This command can only be executed by a player!");
                return false;
            }

            try {
                manager.joinLobby((Player) sender, Integer.parseInt(args[0]));
                return true;
            }catch (Exception e) {
                sender.sendMessage("Couldn't join that lobby! (#" + args[0] + ")");
                e.printStackTrace();
                return false;
            }

        }else if(label.equalsIgnoreCase("create")) {
            int l = manager.createLobby();

            sender.sendMessage("Created lobby #" + l);

            return true;
        }else if(label.equalsIgnoreCase("leave")) {
            if(!(sender instanceof Player)) {
                sender.sendMessage("This command can only be executed by a player!");
                return false;
            }

            try {
                manager.leaveLobby((Player) sender);
                return true;
            }catch (Exception e) {
                sender.sendMessage("Couldn't leave lobby!");
                e.printStackTrace();
                return false;
            }
        }

        return false;
    }

}
