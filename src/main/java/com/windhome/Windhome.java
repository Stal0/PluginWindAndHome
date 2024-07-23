package com.windhome;

import com.windhome.events.WindChargerEvents;
import org.bukkit.Bukkit;

import org.bukkit.plugin.java.JavaPlugin;

public final class Windhome extends JavaPlugin   {



    @Override
    public void onEnable() {
        // Plugin startup logic

        getServer().getPluginManager().registerEvents(new WindChargerEvents(this), this);
        saveDefaultConfig();

    }

    public static void broadcastMessage(String message) {
        Bukkit.getServer().broadcastMessage(message);
    }


    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
