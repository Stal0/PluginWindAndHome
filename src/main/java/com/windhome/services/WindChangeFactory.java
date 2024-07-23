package com.windhome.services;

import com.windhome.factories.ParticleFactory;
import com.windhome.particles.ParticleEffect;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.WindCharge;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;

import java.util.Collection;

public class WindChangeFactory {

    public static void AlterExplosionType(WindCharge windCharge, Plugin plugin) {
        Location loc = windCharge.getLocation();
        World world = loc.getWorld();

        //Inicia as variavéis que participam da lógica para adicionar o knockback da explosão
        double knockbackRadius = plugin.getConfig().getDouble("settings.explosionRadius");
        double knockbackStrength = plugin.getConfig().getDouble("settings.explosionStrength");
        double verticalKnockbackStrength = 0.5;

        Collection<Entity> nearbyEntities = world.getNearbyEntities(loc, knockbackRadius, knockbackRadius, knockbackRadius);

        //Lógica para verificar as entidades no raio da explosão, para então adicionar o efeito do knockback.
        for (Entity entity : nearbyEntities) {
            if (entity.getLocation() != null) {
                Vector direction = entity.getLocation().toVector().subtract(loc.toVector()).normalize();
                direction.setY(verticalKnockbackStrength);

                // Adiciona verificações para garantir que o vetor de direção seja válido
                if (Double.isFinite(direction.getX()) && Double.isFinite(direction.getY()) && Double.isFinite(direction.getZ())) {
                    entity.setVelocity(direction.multiply(knockbackStrength));
                }
            } else {
                System.out.println("Localização da entidade é null ou fora do raio de knockback");
            }
        }
    }

    public static void AlterSpeedWindCharge(WindCharge windCharge, Plugin plugin) {
        //Altera a velocidade do projétil WindCharge recuperando a informação na config.yml
        windCharge.setAcceleration(windCharge.getDirection().multiply(plugin.getConfig().getDouble("settings.projectileSpeed")));
    }

    public static void AlterParticle(WindCharge windCharge, Plugin plugin) {
        Location loc = windCharge.getLocation();
        World world = loc.getWorld();

        //Recupera o tamanho da explosão para definir na hora de gerar as partículas.
        double particleRadius = plugin.getConfig().getDouble("settings.explosionRadius");

        //Recupera toda a seção da config.yml que se remete as partículas, para selecionar a primeira que estiver como true;
        String selectedParticle = plugin.getConfig().getConfigurationSection("settings.particles")
                .getKeys(false)
                .stream()
                .filter(key -> plugin.getConfig().getBoolean("settings.particles." + key))
                .findFirst()
                .orElse("standard");

        ParticleEffect particleEffect;

        //Cria a instância de ParticleEffect que se remete na partícula recuperada da config.yml
        switch (selectedParticle) {
            case "ender":
                particleEffect = ParticleFactory.getParticleEffect("ender");
                break;
            case "smoke":
                particleEffect = ParticleFactory.getParticleEffect("smoke");
                break;
            default:
                particleEffect = ParticleFactory.getParticleEffect("standard");
                break;
        }

        //Spawna as partículas para simular a explosão, juntamente com um som customizado com base na partícula.
        particleEffect.spawn(loc, world, particleRadius);

    }

}
