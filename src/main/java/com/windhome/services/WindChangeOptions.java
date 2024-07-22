package com.windhome.services;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class WindChangeOptions {

    public static void AlterVelocity(Player player, Entity entity, Double value) {
        Vector direction = player.getLocation().getDirection();
        entity.setVelocity(direction.multiply(value));
    }
}
