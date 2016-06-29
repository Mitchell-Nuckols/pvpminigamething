package io.liveware.bukkit.pvpminigame;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;

/**
 * Created by Mitchell on 6/29/2016.
 */
public class GameHelper {

    public static void giveArmor(Player player, GameTeam team) { // WHY IS THIS SO UNAPPEALING TO ME (I think this can be done a lot more efficiently)
        ItemStack[] redArmor = new ItemStack[4];
        redArmor[3] = new ItemStack(Material.LEATHER_HELMET, 1);
        redArmor[2] = new ItemStack(Material.LEATHER_CHESTPLATE, 1);
        redArmor[1] = new ItemStack(Material.LEATHER_LEGGINGS, 1);
        redArmor[0] = new ItemStack(Material.LEATHER_BOOTS, 1);
        ItemStack[] blueArmor = new ItemStack[4];
        blueArmor[3] = new ItemStack(Material.LEATHER_HELMET, 1);
        blueArmor[2] = new ItemStack(Material.LEATHER_CHESTPLATE, 1);
        blueArmor[1] = new ItemStack(Material.LEATHER_LEGGINGS, 1);
        blueArmor[0] = new ItemStack(Material.LEATHER_BOOTS, 1);

        LeatherArmorMeta leatherArmorMeta = (LeatherArmorMeta) redArmor[0].getItemMeta();
        leatherArmorMeta.setColor(Color.RED);
        redArmor[0].setItemMeta(leatherArmorMeta);
        leatherArmorMeta = (LeatherArmorMeta) redArmor[1].getItemMeta();
        leatherArmorMeta.setColor(Color.RED);
        redArmor[1].setItemMeta(leatherArmorMeta);
        leatherArmorMeta = (LeatherArmorMeta) redArmor[2].getItemMeta();
        leatherArmorMeta.setColor(Color.RED);
        redArmor[2].setItemMeta(leatherArmorMeta);
        leatherArmorMeta = (LeatherArmorMeta) redArmor[3].getItemMeta();
        leatherArmorMeta.setColor(Color.RED);
        redArmor[3].setItemMeta(leatherArmorMeta);

        leatherArmorMeta = (LeatherArmorMeta) blueArmor[0].getItemMeta();
        leatherArmorMeta.setColor(Color.BLUE);
        blueArmor[0].setItemMeta(leatherArmorMeta);
        leatherArmorMeta = (LeatherArmorMeta) blueArmor[1].getItemMeta();
        leatherArmorMeta.setColor(Color.BLUE);
        blueArmor[1].setItemMeta(leatherArmorMeta);
        leatherArmorMeta = (LeatherArmorMeta) blueArmor[2].getItemMeta();
        leatherArmorMeta.setColor(Color.BLUE);
        blueArmor[2].setItemMeta(leatherArmorMeta);
        leatherArmorMeta = (LeatherArmorMeta) blueArmor[3].getItemMeta();
        leatherArmorMeta.setColor(Color.BLUE);
        blueArmor[3].setItemMeta(leatherArmorMeta);

        player.getInventory().setArmorContents((team == GameTeam.BLUE ? blueArmor : redArmor));
    }

}
