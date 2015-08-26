package com.settingdust.loreattr;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

public class LoreAttributes extends JavaPlugin {
    public static LoreManager loreManager;
    public static FileConfiguration config = null;

    public void onEnable() {
        config = getConfig();
        config.options().copyDefaults(true);

        saveConfig();

        if (loreManager == null) {
            loreManager = new LoreManager(this);
        }

        Bukkit.getServer().getPluginManager().registerEvents(new LoreEvents(), this);
    }

    public void onDisable() {
        HandlerList.unregisterAll(this);
    }

}