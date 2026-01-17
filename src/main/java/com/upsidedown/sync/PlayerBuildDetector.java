package com.upsidedown.sync;

import com.upsidedown.dimension.BlockTransformationMap;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.chunk.WorldChunk;

import java.util.ArrayList;
import java.util.List;

/**
 * Detects blocks that were placed by players vs naturally generated terrain.
 */
public class PlayerBuildDetector {

    /**
     * Detect player-placed blocks in a chunk.
     */
    public static List<BlockPos> detectPlayerBuilds(ServerWorld world, ChunkPos chunkPos) {
        List<BlockPos> playerBlocks = new ArrayList<>();

        if (!world.isChunkLoaded(chunkPos.x, chunkPos.z)) {
            return playerBlocks;
        }

        WorldChunk chunk = world.getChunk(chunkPos.x, chunkPos.z);
        int startX = chunkPos.getStartX();
        int startZ = chunkPos.getStartZ();

        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                for (int y = world.getBottomY(); y < world.getTopY(); y++) {
                    BlockPos pos = new BlockPos(startX + x, y, startZ + z);
                    BlockState state = chunk.getBlockState(pos);

                    if (BlockTransformationMap.isPlayerPlacedBlock(state.getBlock())) {
                        playerBlocks.add(pos);
                    }
                }
            }
        }

        return playerBlocks;
    }

    /**
     * Check if a specific block is likely player-placed.
     */
    public static boolean isPlayerPlaced(ServerWorld world, BlockPos pos) {
        BlockState state = world.getBlockState(pos);
        return BlockTransformationMap.isPlayerPlacedBlock(state.getBlock());
    }
}
