package com.upsidedown.sync;

import com.upsidedown.UpsideDownMod;
import com.upsidedown.config.ModConfig;
import com.upsidedown.dimension.BlockTransformationMap;
import com.upsidedown.dimension.ModDimensions;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.WorldChunk;

import java.util.ArrayList;
import java.util.List;

/**
 * Handles syncing player-built structures from Overworld to The Upside Down.
 * 
 * In Mirror Mode: Detects player builds and syncs them with decay effects when
 * entering.
 * In Snapshot Mode: Does a one-time full copy on first entry.
 */
public class StructureSyncHandler {

    private static final int SYNC_RADIUS_CHUNKS = 4; // How many chunks around player to sync

    /**
     * Called when a player enters The Upside Down.
     */
    public static void onPlayerEnterUpsideDown(ServerPlayerEntity player) {
        ModConfig config = ModConfig.get();

        if (config.syncMode == ModConfig.SyncMode.SNAPSHOT && !config.dimensionInitialized) {
            // First time entering - do full snapshot
            performSnapshot(player);
            config.dimensionInitialized = true;
            ModConfig.save();
        } else if (config.syncMode == ModConfig.SyncMode.MIRROR) {
            // Mirror mode - sync nearby structures
            syncNearbyStructures(player);
        }
    }

    /**
     * Snapshot mode: Copy and transform all loaded chunks once.
     */
    private static void performSnapshot(ServerPlayerEntity player) {
        UpsideDownMod.LOGGER.info("Performing initial snapshot of Overworld to Upside Down");

        ServerWorld overworld = player.getServer().getWorld(World.OVERWORLD);
        ServerWorld upsideDown = player.getServer().getWorld(ModDimensions.UPSIDE_DOWN_KEY);

        if (overworld == null || upsideDown == null) {
            UpsideDownMod.LOGGER.error("Could not access dimensions for snapshot");
            return;
        }

        // Sync a larger area for snapshot
        ChunkPos playerChunkPos = player.getChunkPos();
        int snapshotRadius = 8; // Larger radius for initial snapshot

        for (int dx = -snapshotRadius; dx <= snapshotRadius; dx++) {
            for (int dz = -snapshotRadius; dz <= snapshotRadius; dz++) {
                ChunkPos chunkPos = new ChunkPos(playerChunkPos.x + dx, playerChunkPos.z + dz);
                syncChunk(overworld, upsideDown, chunkPos, true);
            }
        }

        UpsideDownMod.LOGGER.info("Snapshot complete");
    }

    /**
     * Mirror mode: Sync nearby player-built structures.
     */
    private static void syncNearbyStructures(ServerPlayerEntity player) {
        ServerWorld overworld = player.getServer().getWorld(World.OVERWORLD);
        ServerWorld upsideDown = player.getServer().getWorld(ModDimensions.UPSIDE_DOWN_KEY);

        if (overworld == null || upsideDown == null) {
            return;
        }

        ChunkPos playerChunkPos = player.getChunkPos();

        for (int dx = -SYNC_RADIUS_CHUNKS; dx <= SYNC_RADIUS_CHUNKS; dx++) {
            for (int dz = -SYNC_RADIUS_CHUNKS; dz <= SYNC_RADIUS_CHUNKS; dz++) {
                ChunkPos chunkPos = new ChunkPos(playerChunkPos.x + dx, playerChunkPos.z + dz);
                syncPlayerBuilds(overworld, upsideDown, chunkPos);
            }
        }
    }

    /**
     * Sync an entire chunk (for snapshot mode).
     */
    private static void syncChunk(ServerWorld source, ServerWorld target, ChunkPos chunkPos, boolean applyDecay) {
        if (!source.isChunkLoaded(chunkPos.x, chunkPos.z)) {
            return;
        }

        WorldChunk sourceChunk = source.getChunk(chunkPos.x, chunkPos.z);

        int startX = chunkPos.getStartX();
        int startZ = chunkPos.getStartZ();

        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                for (int y = source.getBottomY(); y < source.getTopY(); y++) {
                    BlockPos pos = new BlockPos(startX + x, y, startZ + z);
                    BlockState sourceState = sourceChunk.getBlockState(pos);

                    // Transform the block
                    BlockState transformedState = BlockTransformationMap.transform(sourceState);

                    // Set in target dimension
                    target.setBlockState(pos, transformedState, 2);

                    // Apply decay to structures if enabled
                    if (applyDecay && BlockTransformationMap.isStructureBlock(sourceState.getBlock())) {
                        DecayApplicator.applyDecay(target, pos);
                    }
                }
            }
        }
    }

    /**
     * Sync only player-built blocks in a chunk (for mirror mode).
     */
    private static void syncPlayerBuilds(ServerWorld overworld, ServerWorld upsideDown, ChunkPos chunkPos) {
        if (!overworld.isChunkLoaded(chunkPos.x, chunkPos.z)) {
            return;
        }

        List<BlockPos> playerBlocks = PlayerBuildDetector.detectPlayerBuilds(overworld, chunkPos);

        for (BlockPos pos : playerBlocks) {
            BlockState overworldState = overworld.getBlockState(pos);
            BlockState upsideDownState = upsideDown.getBlockState(pos);

            // Only sync if the Upside Down block is still natural (not already synced)
            if (upsideDownState.isAir() || !BlockTransformationMap.isPlayerPlacedBlock(upsideDownState.getBlock())) {
                // Transform and place
                BlockState transformedState = BlockTransformationMap.transform(overworldState);
                upsideDown.setBlockState(pos, transformedState, 2);

                // Apply decay
                DecayApplicator.applyDecay(upsideDown, pos);
            }
        }
    }
}
