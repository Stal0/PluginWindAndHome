package com.windhome.particles;


import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.World;

public class EnderParticle implements ParticleEffect {

    @Override
    public void spawn(Location location, World world, Double radius) {
        world.spawnParticle(Particle.PORTAL, location, 1000, radius, radius, radius);
        world.playSound(location, Sound.BLOCK_PORTAL_AMBIENT, 5.0F, 1.0F);
    }
}

