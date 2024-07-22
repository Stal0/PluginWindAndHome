package com.windhome.entities;

import com.windhome.Windhome;
import org.bukkit.Location;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.entity.WindCharge;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;
import org.w3c.dom.events.EventException;

public class WindChargerSpecial {

    public static final String METADATA_KEY = "WindChargeUncommum";

    public static WindCharge spawnWindCharge(Player player, Plugin plugin) {
        Location location = player.getEyeLocation();
        Vector direction = location.getDirection(); // Ajustar o multiplicador para a velocidade

        WindCharge windCharge = player.getWorld().spawn(location, WindCharge.class);
        windCharge.setVelocity(direction.multiply(1));
        windCharge.setShooter(player);
        windCharge.setDirection(direction);
        player.sendMessage(" " + windCharge.getYield());
        windCharge.setYield(100F); // Ajuste a força da explosão
        player.sendMessage(" " + windCharge.getYield());
        windCharge.setMetadata(METADATA_KEY, new FixedMetadataValue(plugin, true));

        return windCharge;
        }

    public static boolean isWindCharge(WindCharge windCharge) {
        return windCharge.hasMetadata(METADATA_KEY);
    }

    }




