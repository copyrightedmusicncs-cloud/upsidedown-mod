package com.upsidedown.particle;

import com.upsidedown.UpsideDownMod;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.particle.SimpleParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

/**
 * Registers custom particles for The Upside Down atmosphere.
 */
public class ModParticles {

    // Spore particle - floats slowly upward, glows faintly
    public static final SimpleParticleType SPORE = FabricParticleTypes.simple();

    // Ash particle - falls slowly downward
    public static final SimpleParticleType ASH = FabricParticleTypes.simple();

    // Floating debris particle
    public static final SimpleParticleType FLOATING_DEBRIS = FabricParticleTypes.simple();

    /**
     * Register particles on server side.
     */
    public static void register() {
        UpsideDownMod.LOGGER.info("Registering particles for {}", UpsideDownMod.MOD_ID);

        Registry.register(Registries.PARTICLE_TYPE,
                Identifier.of(UpsideDownMod.MOD_ID, "spore"), SPORE);
        Registry.register(Registries.PARTICLE_TYPE,
                Identifier.of(UpsideDownMod.MOD_ID, "ash"), ASH);
        Registry.register(Registries.PARTICLE_TYPE,
                Identifier.of(UpsideDownMod.MOD_ID, "floating_debris"), FLOATING_DEBRIS);
    }

    /**
     * Register particle factories on client side.
     */
    public static void registerClient() {
        // Particle factories are registered in UpsideDownClient
        // using ParticleFactoryRegistry
    }
}
