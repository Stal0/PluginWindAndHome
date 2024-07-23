package com.windhome.services;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.WindCharge;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;

import java.util.Collection;

public class WindChangeFactory {

    public static void AlterExplosionType(WindCharge windCharge, Plugin plugin) {
        Location loc = windCharge.getLocation();
        World world = loc.getWorld();

        // Adiciona som customizado
        world.playSound(loc, Sound.ENTITY_WIND_CHARGE_WIND_BURST, 5.0F, 1.0F);

        // Adiciona partículas customizadas
        double particleRadius = plugin.getConfig().getDouble("settings.explosionRadius"); // Raio de dispersão das partículas
        world.spawnParticle(Particle.EXPLOSION, loc, 50, particleRadius, particleRadius, particleRadius, 0.1);

        // Adiciona efeito de knockback às entidades próximas
        double knockbackRadius = plugin.getConfig().getDouble("settings.explosionRadius"); // Raio do knockback
        double knockbackStrength = plugin.getConfig().getDouble("settings.explosionStrength"); // Força do knockback horizontal
        double verticalKnockbackStrength = 0.5; // Força do knockback vertical

        Collection<Entity> nearbyEntities = world.getNearbyEntities(loc, knockbackRadius, knockbackRadius, knockbackRadius);

        for (Entity entity : nearbyEntities) {
            if (entity.getLocation() != null) {
                Vector direction = entity.getLocation().toVector().subtract(loc.toVector()).normalize();
                direction.setY(verticalKnockbackStrength); // Ajusta a força do knockback vertical

                // Adiciona verificações para garantir que o vetor de direção seja válido
                if (Double.isFinite(direction.getX()) && Double.isFinite(direction.getY()) && Double.isFinite(direction.getZ())) {
                    entity.setVelocity(direction.multiply(knockbackStrength));
                }
            } else {
                System.out.println("Localização da entidade é null ou fora do raio de knockback");
            }
        }
    }

}
