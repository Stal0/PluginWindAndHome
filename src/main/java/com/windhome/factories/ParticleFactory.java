package com.windhome.factories;

import com.windhome.particles.EnderParticle;
import com.windhome.particles.ParticleEffect;
import com.windhome.particles.SmokeParticle;
import com.windhome.particles.StandardParticle;

import java.util.HashMap;
import java.util.Map;

public class ParticleFactory {

    private static Map<String, ParticleEffect> particleMap = new HashMap<>();

    static {
        particleMap.put("ender", new EnderParticle());
        particleMap.put("standard", new StandardParticle());
        particleMap.put("smoke", new SmokeParticle());
    }

    public static ParticleEffect getParticleEffect(String particleType) {
        return particleMap.getOrDefault(particleType.toLowerCase(), new StandardParticle());
    }
}
