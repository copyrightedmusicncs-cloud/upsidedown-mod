package com.upsidedown.client;

import com.upsidedown.UpsideDownMod;
import com.upsidedown.particle.ModParticles;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

/**
 * Client-side entry point for The Upside Down mod.
 * Handles rendering, particles, and visual effects.
 */
@Environment(EnvType.CLIENT)
public class UpsideDownClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        UpsideDownMod.LOGGER.info("Initializing The Upside Down client...");

        // Register particle factories
        ModParticles.registerClient();

        // Register sky renderer
        UpsideDownSkyRenderer.register();

        // Register particle spawner tick event
        UpsideDownParticleSpawner.register();

        UpsideDownMod.LOGGER.info("The Upside Down client initialized.");
    }
}
