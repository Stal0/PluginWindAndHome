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
        //recupera os players no banco de dados
        try(Connection conn = DatabaseManager.getConnection()) {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM players WHERE player_uuid = ?");
            ps.setString(1, player.getUniqueId().toString());
            ResultSet rs = ps.executeQuery();
        //Caso o player não existir no banco de dados, insere o novo player no banco de dados ao entrar no servidor.
            if(!(rs.next())) {
                PreparedStatement insertPs = conn.prepareStatement("INSERT INTO players (player_uuid, player_name) VALUES (?, ?)");
                insertPs.setString(1, player.getUniqueId().toString());
                System.out.println(player.getUniqueId().toString());
                insertPs.setString(2, player.getName());
                System.out.println(player.getName());
                insertPs.executeUpdate();
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
