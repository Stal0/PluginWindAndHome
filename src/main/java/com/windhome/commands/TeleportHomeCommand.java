package com.windhome.commands;

import com.windhome.Windhome;
import com.windhome.db.DatabaseManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class TeleportHomeCommand implements CommandExecutor {

    //Recupera o plugin para ler a config.yml
    private Windhome plugin;

    public TeleportHomeCommand(Windhome plugin) {
        this.plugin = plugin;
    }


    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage("Esse comando só pode ser executado por jogadores!");
            return false;
        }

        Player player = (Player) commandSender;
        String homeName = strings.length == 0 ? "home" : strings[0];

        int cooldown = plugin.getConfig().getInt("teleport.cooldown");
        boolean particle = plugin.getConfig().getBoolean("teleport.particle");

        //Inicia a conexão com banco de dados para recuperar as informações da Home
        try (Connection conn = DatabaseManager.getConnection()) {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM homes WHERE home_name=? AND player_uuid = ?");
            ps.setString(1, homeName);
            ps.setString(2, player.getUniqueId().toString());
            ResultSet rs = ps.executeQuery();
            if (!(rs.next())) {
                commandSender.sendMessage("Home não encontrada!");
            } else {
                double locationX = rs.getDouble("location_x");
                double locationY = rs.getDouble("location_y");
                double locationZ = rs.getDouble("location_z");
                World world = Bukkit.getWorld(rs.getString("world_name"));

                //Lógica para aguardar o valor de cooldown para teleportar o player, incluindo se terá particulas
                player.sendMessage("Teletransporte em " + cooldown / 20  + " segundos...");
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        if (particle) {
                            player.spawnParticle(Particle.DRAGON_BREATH, player.getLocation(), 500);
                        }
                        player.teleport(new Location(world, locationX, locationY, locationZ));
                    }
                }.runTaskLater(plugin, cooldown);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
