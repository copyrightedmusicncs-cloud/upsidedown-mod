package com.upsidedown.item;

import com.upsidedown.UpsideDownMod;
import com.upsidedown.dimension.ModDimensions;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.RegistryKey;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.TeleportTarget;
import net.minecraft.world.World;

/**
 * Portal Catalyst Item - Teleports players between the Overworld and The Upside
 * Down.
 * 
 * Right-click to instantly teleport to the same coordinates in the parallel
 * dimension.
 * Used for testing and creative mode dimension travel.
 */
public class PortalCatalystItem extends Item {

    public PortalCatalystItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult use(World world, PlayerEntity user, Hand hand) {
        if (world.isClient()) {
            // Client-side: spawn particles
            spawnTeleportParticles(world, user.getBlockPos());
            return ActionResult.SUCCESS;
        }

        // Server-side: perform teleportation
        if (user instanceof ServerPlayerEntity serverPlayer) {
            teleportPlayer(serverPlayer);
            return ActionResult.CONSUME;
        }

        return ActionResult.PASS;
    }

    private void teleportPlayer(ServerPlayerEntity player) {
        ServerWorld currentWorld = player.getServerWorld();
        RegistryKey<World> targetDimensionKey;

        // Determine target dimension
        if (ModDimensions.isUpsideDown(currentWorld)) {
            targetDimensionKey = World.OVERWORLD;
        } else {
            targetDimensionKey = ModDimensions.UPSIDE_DOWN_KEY;
        }

        // Get target world
        ServerWorld targetWorld = player.getServer().getWorld(targetDimensionKey);
        if (targetWorld == null) {
            player.sendMessage(Text.literal("§cFailed to access target dimension!"), true);
            UpsideDownMod.LOGGER.error("Could not get target world for dimension: {}", targetDimensionKey);
            return;
        }

        // Create teleport target (same coordinates, same rotation)
        TeleportTarget target = new TeleportTarget(
                targetWorld,
                player.getPos(),
                player.getVelocity(),
                player.getYaw(),
                player.getPitch(),
                TeleportTarget.NO_OP);

        // Play teleport sound before
        player.getWorld().playSound(null, player.getBlockPos(),
                SoundEvents.ENTITY_ENDERMAN_TELEPORT, SoundCategory.PLAYERS, 1.0f, 0.5f);

        // Teleport the player
        player.teleportTo(target);

        // Play sound after
        targetWorld.playSound(null, player.getBlockPos(),
                SoundEvents.ENTITY_ENDERMAN_TELEPORT, SoundCategory.PLAYERS, 1.0f, 0.5f);

        // Send message
        String dimensionName = ModDimensions.isUpsideDown(targetWorld) ? "The Upside Down" : "The Overworld";
        player.sendMessage(Text.literal("§5Transported to " + dimensionName), true);

        UpsideDownMod.LOGGER.info("Player {} teleported to {}", player.getName().getString(), dimensionName);
    }

    private void spawnTeleportParticles(World world, BlockPos pos) {
        for (int i = 0; i < 20; i++) {
            double offsetX = (world.random.nextDouble() - 0.5) * 2;
            double offsetY = world.random.nextDouble() * 2;
            double offsetZ = (world.random.nextDouble() - 0.5) * 2;

            world.addParticle(ParticleTypes.PORTAL,
                    pos.getX() + 0.5 + offsetX,
                    pos.getY() + offsetY,
                    pos.getZ() + 0.5 + offsetZ,
                    0, 0, 0);
        }
    }
}
