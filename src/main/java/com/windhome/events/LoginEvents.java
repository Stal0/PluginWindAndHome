package com.windhome.events;

import com.windhome.Windhome;
import com.windhome.db.DatabaseManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.plugin.Plugin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginEvents implements Listener {

    @EventHandler
    public void onLogin(PlayerLoginEvent event) {
        Player player = event.getPlayer();
        System.out.println(".");

        try(Connection conn = DatabaseManager.getConnection()) {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM players WHERE player_uuid = ?");
            ps.setString(1, player.getUniqueId().toString());
            ResultSet rs = ps.executeQuery();

            if(rs.next()) {
                Windhome.broadcastMessageToAll("Bem vindo de volta: " + player.getName());
            } else {
                PreparedStatement insertPs = conn.prepareStatement("INSERT INTO players (player_uuid, player_name) VALUES (?, ?)");
                insertPs.setString(1, player.getUniqueId().toString());
                System.out.println(player.getUniqueId().toString());
                insertPs.setString(2, player.getName());
                System.out.println(player.getName());
                insertPs.executeUpdate();
                Windhome.broadcastMessageToAll("Bem-vindo ao servidor pela primeira vez!");

            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
