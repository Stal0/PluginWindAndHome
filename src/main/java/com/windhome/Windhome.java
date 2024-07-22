package com.windhome;

import com.windhome.commands.GiveWindCharger;
import com.windhome.items.WindChargerCreate;
import com.windhome.events.WindChargerEvents;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class Windhome extends JavaPlugin   {

    public Windhome windhome = this;

    @Override
    public void onEnable() {
        // Plugin startup logic
        WindChargerCreate.init();
        getServer().getPluginManager().registerEvents(new WindChargerEvents(this), this);
        this.getCommand("givewindcharger").setExecutor(new GiveWindCharger());

    }

    public static void broadcastMessage(String message) {
        Bukkit.getServer().broadcastMessage(message);
    }


    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
