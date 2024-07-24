package com.windhome.commands;

import com.windhome.db.DatabaseManager;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SetHomeCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage("Este comando só pode ser executado por jogadores!");
            return false;
        }

        //Recupera as informações do player e adiciona "home" se os args do comando forem vázios.
        Player player = (Player) commandSender;
        String homeName = strings.length == 0 ? "home" : strings[0];
        Location location = player.getLocation();

        //Recupera as informações no banco de dados com base no player
        try (Connection conn = DatabaseManager.getConnection()) {
            PreparedStatement preparedStatement = conn.prepareStatement("SELECT * FROM homes WHERE home_name = ? AND player_uuid = ?");
            preparedStatement.setString(1, homeName);
            preparedStatement.setString(2, player.getUniqueId().toString());
            ResultSet rs = preparedStatement.executeQuery();


            if (!rs.next()) {
                // Inserir nova home se não houver uma existente com o nome informado
                PreparedStatement insertPs = conn.prepareStatement("INSERT INTO homes (home_name, player_uuid, location_x, location_y, location_z, world_name) VALUES (?, ?, ?, ?, ?, ?)");
                insertPs.setString(1, homeName);
                insertPs.setString(2, player.getUniqueId().toString());
                insertPs.setDouble(3, location.getX());
                insertPs.setDouble(4, location.getY());
                insertPs.setDouble(5, location.getZ());
                insertPs.setString(6, location.getWorld().getName());
                insertPs.executeUpdate();
                commandSender.sendMessage("Home " + homeName + " criada com sucesso!");
            } else {
                // Atualizar a home existente
                PreparedStatement updatePs = conn.prepareStatement("UPDATE homes SET location_x = ?, location_y = ?, location_z = ?, world_name= ? WHERE home_name = ? AND player_uuid = ?");
                updatePs.setDouble(1, location.getX());
                updatePs.setDouble(2, location.getY());
                updatePs.setDouble(3, location.getZ());
                updatePs.setString(4, homeName);
                updatePs.setString(5, player.getUniqueId().toString());
                updatePs.setString(6, location.getWorld().getName());
                updatePs.executeUpdate();
                commandSender.sendMessage("Home " + homeName + " atualizada com sucesso!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            commandSender.sendMessage("Ocorreu um erro ao salvar a home.");
        }

        return true;
    }
}
