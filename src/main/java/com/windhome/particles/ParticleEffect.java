package com.windhome.particles;

import org.bukkit.Location;
import org.bukkit.World;

public interface ParticleEffect {
    void spawn(Location location, World world, Double radius);
}

