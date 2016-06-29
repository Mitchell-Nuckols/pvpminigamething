package io.liveware.bukkit.pvpminigame;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
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

            if(l != -1) sender.sendMessage("Created lobby #" + l);
            else sender.sendMessage("Could not create that lobby! (Max lobbies already created)");

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
        }else if(label.equalsIgnoreCase("drawcircle")) { // Because Voltz wanted it
            if(!(sender instanceof Player)) {
                sender.sendMessage("This command can only be executed by a player!");
                return false;
            }

            if(args.length == 0) return false;

            try {
                drawCircle(((Player) sender).getLocation(), Integer.parseInt(args[0]));
                return true;
            }catch (Exception e) {
                sender.sendMessage("Couldn't make a circle!");
                e.printStackTrace();
                return false;
            }
        }

        return false;
    }

    public static void drawCircle(Location location, int radius)
    {
        int x0 = location.getBlockX();
        int y0 = location.getBlockZ();
        int actualY = location.getBlockY();
        World world = location.getWorld();

        int x = radius;
        int y = 0;
        int err = 0;

        while (x >= y)
        {
            world.getBlockAt(x0 + x, actualY, y0 + y).setType(Material.STONE);
            world.getBlockAt(x0 + y, actualY, y0 + x).setType(Material.STONE);
            world.getBlockAt(x0 - y, actualY, y0 + x).setType(Material.STONE);
            world.getBlockAt(x0 - x, actualY, y0 + y).setType(Material.STONE);
            world.getBlockAt(x0 - x, actualY, y0 - y).setType(Material.STONE);
            world.getBlockAt(x0 - y, actualY, y0 - x).setType(Material.STONE);
            world.getBlockAt(x0 + y, actualY, y0 - x).setType(Material.STONE);
            world.getBlockAt(x0 + x, actualY, y0 - y).setType(Material.STONE);

            y += 1;
            err += 1 + 2*y;
            if (2*(err-x) + 1 > 0)
            {
                x -= 1;
                err += 1 - 2*x;
            }
        }
    }

}
