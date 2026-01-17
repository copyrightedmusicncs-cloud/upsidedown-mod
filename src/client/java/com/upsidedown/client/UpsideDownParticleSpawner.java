package com.upsidedown.client;

import com.upsidedown.UpsideDownMod;
import com.upsidedown.config.ModConfig;
import com.upsidedown.dimension.ModDimensions;
import com.upsidedown.particle.ModParticles;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;

/**
 * Spawns ambient particles in The Upside Down dimension.
 * 
 * Particles include:
 * - Spores: Float slowly upward with slight drift
 * - Ash: Falls slowly from above
 * - Floating debris: Drifts in the air
 */
@Environment(EnvType.CLIENT)
public class UpsideDownParticleSpawner {

    private static final int SPORE_COUNT_BASE = 5;
    private static final int ASH_COUNT_BASE = 3;
    private static final float SPAWN_RADIUS = 16.0f;
    private static final float SPAWN_HEIGHT = 10.0f;

    private static int tickCounter = 0;

    /**
     * Register the particle spawner tick event.
     */
    public static void register() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.world != null && client.player != null) {
                tick(client.world, client);
            }
        });

        UpsideDownMod.LOGGER.info("Upside Down particle spawner registered");
    }

    /**
     * Called every client tick to spawn ambient particles.
     */
    private static void tick(ClientWorld world, MinecraftClient client) {
        // Only spawn in The Upside Down
        if (!ModDimensions.isUpsideDown(world)) {
            return;
        }

        // Only spawn every few ticks to reduce performance impact
        tickCounter++;
        if (tickCounter < 3)
            return;
        tickCounter = 0;

        if (client.player == null)
            return;

        Vec3d playerPos = client.player.getPos();
        Random random = world.getRandom();
        float density = ModConfig.get().sporeParticleDensity;

        // Spawn spores (float upward)
        int sporeCount = (int) (SPORE_COUNT_BASE * density);
        for (int i = 0; i < sporeCount; i++) {
            Vec3d pos = randomPositionNear(playerPos, random, SPAWN_RADIUS, 0);

            // Use existing particle as placeholder until custom ones work
            world.addParticle(
                    ParticleTypes.MYCELIUM,
                    pos.x, pos.y, pos.z,
                    0, 0.02, 0 // Slow upward velocity
            );
        }

        // Spawn ash (fall downward)
        int ashCount = (int) (ASH_COUNT_BASE * density);
        for (int i = 0; i < ashCount; i++) {
            Vec3d pos = randomPositionNear(playerPos, random, SPAWN_RADIUS, SPAWN_HEIGHT);

            // Use existing particle as placeholder
            world.addParticle(
                    ParticleTypes.WHITE_ASH,
                    pos.x, pos.y, pos.z,
                    0, -0.02, 0 // Slow downward velocity
            );
        }

        // Occasional floating debris
        if (random.nextFloat() < 0.1f * density) {
            Vec3d pos = randomPositionNear(playerPos, random, SPAWN_RADIUS * 0.5f, 5);

            world.addParticle(
                    ParticleTypes.ASH,
                    pos.x, pos.y, pos.z,
                    (random.nextFloat() - 0.5f) * 0.02f,
                    0,
                    (random.nextFloat() - 0.5f) * 0.02f);
        }
    }

    /**
     * Get a random position near the player.
     */
    private static Vec3d randomPositionNear(Vec3d center, Random random, float radius, float heightOffset) {
        double x = center.x + (random.nextDouble() - 0.5) * 2 * radius;
        double y = center.y + heightOffset + (random.nextDouble() - 0.5) * 4;
        double z = center.z + (random.nextDouble() - 0.5) * 2 * radius;

        return new Vec3d(x, y, z);
    }
}
