package com.windhome;

import com.windhome.commands.SetHomeCommand;
import com.windhome.commands.TeleportHomeCommand;
import com.windhome.db.DatabaseManager;
import com.windhome.events.LoginEvents;
import com.windhome.events.WindChargerEvents;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLException;

public final class Windhome extends JavaPlugin {

    private DatabaseManager dbm;

    @Override
    public void onEnable() {
        // Plugin startup logic
        getServer().getPluginManager().registerEvents(new WindChargerEvents(this), this);
        getServer().getPluginManager().registerEvents(new LoginEvents(), this);
        saveDefaultConfig();
        this.getCommand("sethome").setExecutor(new SetHomeCommand());
        this.getCommand("home").setExecutor(new TeleportHomeCommand(this));

        dbm = connectToDatabase();
        try {
            dbm.connect();
        } catch (SQLException e) {
            e.printStackTrace();
            getServer().getPluginManager().disablePlugin(this);
        }
    }

    public static void broadcastMessageToAll(String message) {
        Bukkit.broadcastMessage(message);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        dbm.disconnect();

    }

    public DatabaseManager getDbm() {
        return dbm;
    }

    private DatabaseManager connectToDatabase() {
        String host = getConfig().getString("db.host");
        int port = getConfig().getInt("db.port");
        String database = getConfig().getString("db.database");
        String user = getConfig().getString("db.username");
        String password = getConfig().getString("db.password");

        return new DatabaseManager(host, port, database, user, password);
    }
}
