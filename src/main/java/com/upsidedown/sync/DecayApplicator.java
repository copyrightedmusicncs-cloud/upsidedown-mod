package com.upsidedown.sync;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.VineBlock;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;

/**
 * Applies procedural decay effects (vines, cobwebs) to structures in The Upside
 * Down.
 */
public class DecayApplicator {

    private static final float VINE_CHANCE = 0.4f; // 40% chance for vines on walls
    private static final float COBWEB_CHANCE = 0.3f; // 30% chance for cobwebs in corners
    private static final float CEILING_WEB_CHANCE = 0.15f; // 15% chance for ceiling cobwebs

    /**
     * Apply decay effects around a structure block.
     */
    public static void applyDecay(ServerWorld world, BlockPos structureBlock) {
        Random random = world.getRandom();

        // Try to place vines on adjacent walls
        for (Direction direction : Direction.Type.HORIZONTAL) {
            BlockPos adjacent = structureBlock.offset(direction);

            if (canPlaceVine(world, adjacent, direction) && random.nextFloat() < VINE_CHANCE) {
                // Place vine facing into the structure block
                Direction facing = direction.getOpposite();
                BlockState vineState = Blocks.VINE.getDefaultState()
                        .with(VineBlock.getFacingProperty(facing), true);

                world.setBlockState(adjacent, vineState, 2);
            }
        }

        // Check for corners and add cobwebs
        if (isCornerPosition(world, structureBlock) && random.nextFloat() < COBWEB_CHANCE) {
            BlockPos above = structureBlock.up();
            if (world.isAir(above)) {
                world.setBlockState(above, Blocks.COBWEB.getDefaultState(), 2);
            }
        }

        // Check for ceiling and add hanging cobwebs
        BlockPos below = structureBlock.down();
        if (world.isAir(below) && random.nextFloat() < CEILING_WEB_CHANCE) {
            world.setBlockState(below, Blocks.COBWEB.getDefaultState(), 2);
        }
    }

    /**
     * Check if a vine can be placed at a position.
     */
    private static boolean canPlaceVine(ServerWorld world, BlockPos pos, Direction facing) {
        // Vine needs air and a solid block to attach to
        if (!world.isAir(pos)) {
            return false;
        }

        // Check if the block we're facing is solid
        BlockPos attachTo = pos.offset(facing.getOpposite());
        BlockState attachState = world.getBlockState(attachTo);

        return attachState.isSolidBlock(world, attachTo);
    }

    /**
     * Check if a position is in a corner (2+ adjacent solid walls).
     */
    private static boolean isCornerPosition(ServerWorld world, BlockPos pos) {
        int wallCount = 0;

        for (Direction dir : Direction.Type.HORIZONTAL) {
            BlockPos adjacent = pos.offset(dir);
            BlockState state = world.getBlockState(adjacent);

            if (state.isSolidBlock(world, adjacent)) {
                wallCount++;
            }
        }

        return wallCount >= 2;
    }

    /**
     * Apply heavier decay to a specific area (used for doorways, windows, etc.)
     */
    public static void applyHeavyDecay(ServerWorld world, BlockPos center, int radius) {
        Random random = world.getRandom();

        for (int dx = -radius; dx <= radius; dx++) {
            for (int dy = -radius; dy <= radius; dy++) {
                for (int dz = -radius; dz <= radius; dz++) {
                    BlockPos pos = center.add(dx, dy, dz);

                    if (world.isAir(pos) && random.nextFloat() < 0.2f) {
                        // Random chance to place cobweb
                        world.setBlockState(pos, Blocks.COBWEB.getDefaultState(), 2);
                    }
                }
            }
        }
    }
}
