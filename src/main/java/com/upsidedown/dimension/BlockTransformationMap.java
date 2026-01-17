package com.upsidedown.dimension;

import com.upsidedown.block.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Maps Overworld blocks to their Upside Down corrupted variants.
 * This is used by the chunk generator to transform copied terrain.
 */
public class BlockTransformationMap {

    private static final Map<Block, Block> BLOCK_MAP = new HashMap<>();

    // Blocks that indicate player-placed structures (for decay application)
    public static final Set<Block> PLAYER_BUILD_BLOCKS = Set.of(
            Blocks.CRAFTING_TABLE, Blocks.FURNACE, Blocks.CHEST, Blocks.BARREL,
            Blocks.OAK_PLANKS, Blocks.SPRUCE_PLANKS, Blocks.BIRCH_PLANKS,
            Blocks.JUNGLE_PLANKS, Blocks.ACACIA_PLANKS, Blocks.DARK_OAK_PLANKS,
            Blocks.MANGROVE_PLANKS, Blocks.CHERRY_PLANKS, Blocks.BAMBOO_PLANKS,
            Blocks.COBBLESTONE, Blocks.STONE_BRICKS, Blocks.BRICKS,
            Blocks.GLASS, Blocks.GLASS_PANE,
            Blocks.TORCH, Blocks.WALL_TORCH,
            Blocks.OAK_DOOR, Blocks.SPRUCE_DOOR, Blocks.BIRCH_DOOR,
            Blocks.LADDER, Blocks.OAK_FENCE, Blocks.SPRUCE_FENCE,
            Blocks.OAK_STAIRS, Blocks.COBBLESTONE_STAIRS, Blocks.STONE_STAIRS,
            Blocks.OAK_SLAB, Blocks.COBBLESTONE_SLAB, Blocks.STONE_SLAB,
            Blocks.WHITE_BED, Blocks.RED_BED);

    // Blocks that indicate MC-generated structures
    public static final Set<Block> STRUCTURE_BLOCKS = Set.of(
            Blocks.COBBLESTONE, Blocks.MOSSY_COBBLESTONE,
            Blocks.STONE_BRICKS, Blocks.MOSSY_STONE_BRICKS, Blocks.CRACKED_STONE_BRICKS,
            Blocks.BRICKS, Blocks.NETHER_BRICKS,
            Blocks.SANDSTONE, Blocks.CUT_SANDSTONE, Blocks.CHISELED_SANDSTONE,
            Blocks.OAK_PLANKS, Blocks.SPRUCE_PLANKS, Blocks.BIRCH_PLANKS,
            Blocks.OAK_LOG, Blocks.SPRUCE_LOG, Blocks.BIRCH_LOG,
            Blocks.BOOKSHELF, Blocks.CHEST, Blocks.SPAWNER,
            Blocks.IRON_BARS, Blocks.COBWEB);

    static {
        // Terrain blocks
        BLOCK_MAP.put(Blocks.GRASS_BLOCK, ModBlocks.CORRUPTED_GRASS);
        BLOCK_MAP.put(Blocks.DIRT, ModBlocks.CORRUPTED_DIRT);
        BLOCK_MAP.put(Blocks.COARSE_DIRT, ModBlocks.CORRUPTED_DIRT);
        BLOCK_MAP.put(Blocks.ROOTED_DIRT, ModBlocks.CORRUPTED_DIRT);
        BLOCK_MAP.put(Blocks.STONE, ModBlocks.CORRUPTED_STONE);

        // Vegetation - all leaves become dead leaves
        BLOCK_MAP.put(Blocks.OAK_LEAVES, ModBlocks.DEAD_LEAVES);
        BLOCK_MAP.put(Blocks.SPRUCE_LEAVES, ModBlocks.DEAD_LEAVES);
        BLOCK_MAP.put(Blocks.BIRCH_LEAVES, ModBlocks.DEAD_LEAVES);
        BLOCK_MAP.put(Blocks.JUNGLE_LEAVES, ModBlocks.DEAD_LEAVES);
        BLOCK_MAP.put(Blocks.ACACIA_LEAVES, ModBlocks.DEAD_LEAVES);
        BLOCK_MAP.put(Blocks.DARK_OAK_LEAVES, ModBlocks.DEAD_LEAVES);
        BLOCK_MAP.put(Blocks.MANGROVE_LEAVES, ModBlocks.DEAD_LEAVES);
        BLOCK_MAP.put(Blocks.CHERRY_LEAVES, ModBlocks.DEAD_LEAVES);
        BLOCK_MAP.put(Blocks.AZALEA_LEAVES, ModBlocks.DEAD_LEAVES);
        BLOCK_MAP.put(Blocks.FLOWERING_AZALEA_LEAVES, ModBlocks.DEAD_LEAVES);

        // Grass variants
        BLOCK_MAP.put(Blocks.SHORT_GRASS, ModBlocks.DEAD_GRASS);
        BLOCK_MAP.put(Blocks.TALL_GRASS, ModBlocks.DEAD_GRASS);
        BLOCK_MAP.put(Blocks.FERN, ModBlocks.DEAD_GRASS);
        BLOCK_MAP.put(Blocks.LARGE_FERN, ModBlocks.DEAD_GRASS);

        // Flowers become dead
        BLOCK_MAP.put(Blocks.DANDELION, ModBlocks.DEAD_FLOWER);
        BLOCK_MAP.put(Blocks.POPPY, ModBlocks.DEAD_FLOWER);
        BLOCK_MAP.put(Blocks.BLUE_ORCHID, ModBlocks.DEAD_FLOWER);
        BLOCK_MAP.put(Blocks.ALLIUM, ModBlocks.DEAD_FLOWER);
        BLOCK_MAP.put(Blocks.AZURE_BLUET, ModBlocks.DEAD_FLOWER);
        BLOCK_MAP.put(Blocks.RED_TULIP, ModBlocks.DEAD_FLOWER);
        BLOCK_MAP.put(Blocks.ORANGE_TULIP, ModBlocks.DEAD_FLOWER);
        BLOCK_MAP.put(Blocks.WHITE_TULIP, ModBlocks.DEAD_FLOWER);
        BLOCK_MAP.put(Blocks.PINK_TULIP, ModBlocks.DEAD_FLOWER);
        BLOCK_MAP.put(Blocks.OXEYE_DAISY, ModBlocks.DEAD_FLOWER);
        BLOCK_MAP.put(Blocks.CORNFLOWER, ModBlocks.DEAD_FLOWER);
        BLOCK_MAP.put(Blocks.LILY_OF_THE_VALLEY, ModBlocks.DEAD_FLOWER);
        BLOCK_MAP.put(Blocks.SUNFLOWER, ModBlocks.DEAD_FLOWER);
        BLOCK_MAP.put(Blocks.LILAC, ModBlocks.DEAD_FLOWER);
        BLOCK_MAP.put(Blocks.ROSE_BUSH, ModBlocks.DEAD_FLOWER);
        BLOCK_MAP.put(Blocks.PEONY, ModBlocks.DEAD_FLOWER);
        BLOCK_MAP.put(Blocks.TORCHFLOWER, ModBlocks.DEAD_FLOWER);
        BLOCK_MAP.put(Blocks.PINK_PETALS, ModBlocks.DEAD_FLOWER);

        // All logs become decayed
        BLOCK_MAP.put(Blocks.OAK_LOG, ModBlocks.DECAYED_LOG);
        BLOCK_MAP.put(Blocks.SPRUCE_LOG, ModBlocks.DECAYED_LOG);
        BLOCK_MAP.put(Blocks.BIRCH_LOG, ModBlocks.DECAYED_LOG);
        BLOCK_MAP.put(Blocks.JUNGLE_LOG, ModBlocks.DECAYED_LOG);
        BLOCK_MAP.put(Blocks.ACACIA_LOG, ModBlocks.DECAYED_LOG);
        BLOCK_MAP.put(Blocks.DARK_OAK_LOG, ModBlocks.DECAYED_LOG);
        BLOCK_MAP.put(Blocks.MANGROVE_LOG, ModBlocks.DECAYED_LOG);
        BLOCK_MAP.put(Blocks.CHERRY_LOG, ModBlocks.DECAYED_LOG);

        // Planks become decayed
        BLOCK_MAP.put(Blocks.OAK_PLANKS, ModBlocks.DECAYED_PLANKS);
        BLOCK_MAP.put(Blocks.SPRUCE_PLANKS, ModBlocks.DECAYED_PLANKS);
        BLOCK_MAP.put(Blocks.BIRCH_PLANKS, ModBlocks.DECAYED_PLANKS);
        BLOCK_MAP.put(Blocks.JUNGLE_PLANKS, ModBlocks.DECAYED_PLANKS);
        BLOCK_MAP.put(Blocks.ACACIA_PLANKS, ModBlocks.DECAYED_PLANKS);
        BLOCK_MAP.put(Blocks.DARK_OAK_PLANKS, ModBlocks.DECAYED_PLANKS);

        // Glass becomes cracked
        BLOCK_MAP.put(Blocks.GLASS, ModBlocks.CRACKED_GLASS);
    }

    /**
     * Transform a block state to its Upside Down equivalent.
     * Preserves block properties (orientation, etc.) when possible.
     */
    public static BlockState transform(BlockState original) {
        Block originalBlock = original.getBlock();
        Block transformedBlock = BLOCK_MAP.get(originalBlock);

        if (transformedBlock == null) {
            return original; // No transformation needed
        }

        // Return the default state of the transformed block
        // TODO: Copy over block properties (facing, axis, etc.) when applicable
        return transformedBlock.getDefaultState();
    }

    /**
     * Check if a block is a structure block that should have decay applied.
     */
    public static boolean isStructureBlock(Block block) {
        return STRUCTURE_BLOCKS.contains(block) || PLAYER_BUILD_BLOCKS.contains(block);
    }

    /**
     * Check if a block is definitely player-placed.
     */
    public static boolean isPlayerPlacedBlock(Block block) {
        return PLAYER_BUILD_BLOCKS.contains(block);
    }
}
