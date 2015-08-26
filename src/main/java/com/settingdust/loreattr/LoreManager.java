package com.settingdust.loreattr;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class LoreManager {
    private Pattern restrictionRegex;
    private Pattern levelRegex;

    public LoreManager(LoreAttributes plugin) {
        this.restrictionRegex = Pattern.compile("(" + LoreAttributes.config.getString("lore.restriction.keyword").toLowerCase() + ": )(\\w*)");
        this.levelRegex = Pattern.compile("level (\\d+)");
    }

    public void disable() {
    }

    public void handleArmorRestriction(Player player) {
        if (!canUse(player, player.getInventory().getBoots())) {
            if (player.getInventory().firstEmpty() >= 0)
                player.getInventory().addItem(player.getInventory().getBoots());
            else {
                player.getWorld().dropItem(player.getLocation(), player.getInventory().getBoots());
            }
            player.getInventory().setBoots(new ItemStack(Material.AIR));
        }

        if (!canUse(player, player.getInventory().getChestplate())) {
            if (player.getInventory().firstEmpty() >= 0)
                player.getInventory().addItem(player.getInventory().getChestplate());
            else {
                player.getWorld().dropItem(player.getLocation(), player.getInventory().getChestplate());
            }
            player.getInventory().setChestplate(new ItemStack(Material.AIR));
        }

        if (!canUse(player, player.getInventory().getHelmet())) {
            if (player.getInventory().firstEmpty() >= 0)
                player.getInventory().addItem(player.getInventory().getHelmet());
            else {
                player.getWorld().dropItem(player.getLocation(), player.getInventory().getHelmet());
            }
            player.getInventory().setHelmet(new ItemStack(Material.AIR));
        }

        if (!canUse(player, player.getInventory().getLeggings())) {
            if (player.getInventory().firstEmpty() >= 0)
                player.getInventory().addItem(player.getInventory().getLeggings());
            else {
                player.getWorld().dropItem(player.getLocation(), player.getInventory().getLeggings());
            }
            player.getInventory().setLeggings(new ItemStack(Material.AIR));
        }
    }

    public boolean canUse(Player player, ItemStack item) {
        if ((item != null) &&
                (item.hasItemMeta()) &&
                (item.getItemMeta().hasLore())) {
            List<String> lore = item.getItemMeta().getLore();
            String allLore = lore.toString().toLowerCase();
            Matcher valueMatcher = this.levelRegex.matcher(allLore);
            if (valueMatcher.find()) {
                if (player.getLevel() < Integer.valueOf(valueMatcher.group(1))) {
                    player.sendMessage("Item was not able to be equipped.");
                    return false;
                }
            }
            valueMatcher = this.restrictionRegex.matcher(allLore);
            if (valueMatcher.find()) {
                if (player.hasPermission("loreattributes." + valueMatcher.group(2))) {
                    return true;
                }
                if (LoreAttributes.config.getBoolean("lore.restriction.display-message")) {
                    player.sendMessage(LoreAttributes.config.getString("lore.restriction.message").replace("%itemname%", item.getType().toString()));
                }
                return false;
            }

        }

        return true;
    }

}
