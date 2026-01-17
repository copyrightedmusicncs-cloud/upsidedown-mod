package com.upsidedown.block;

import com.upsidedown.UpsideDownMod;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.block.*;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;

import java.util.function.Function;

/**
 * Registers all custom blocks for The Upside Down dimension.
 * These are corrupted/decayed versions of Overworld blocks.
 */
public class ModBlocks {

        // ===== TERRAIN BLOCKS =====

        public static final Block CORRUPTED_GRASS = registerBlock("corrupted_grass",
                        Block::new,
                        AbstractBlock.Settings.copy(Blocks.GRASS_BLOCK)
                                        .sounds(BlockSoundGroup.GRASS));

        public static final Block CORRUPTED_DIRT = registerBlock("corrupted_dirt",
                        Block::new,
                        AbstractBlock.Settings.copy(Blocks.DIRT)
                                        .sounds(BlockSoundGroup.GRAVEL));

        public static final Block CORRUPTED_STONE = registerBlock("corrupted_stone",
                        Block::new,
                        AbstractBlock.Settings.copy(Blocks.STONE)
                                        .sounds(BlockSoundGroup.STONE));

        // ===== VEGETATION BLOCKS =====

        public static final Block DEAD_GRASS = registerBlock("dead_grass",
                        ShortPlantBlock::new,
                        AbstractBlock.Settings.copy(Blocks.SHORT_GRASS)
                                        .noCollision()
                                        .breakInstantly());

        public static final Block DEAD_FLOWER = registerBlock("dead_flower",
                        settings -> new FlowerBlock(net.minecraft.entity.effect.StatusEffects.WITHER, 5.0f, settings),
                        AbstractBlock.Settings.copy(Blocks.DANDELION)
                                        .noCollision()
                                        .breakInstantly());

        public static final Block DEAD_LEAVES = registerBlock("dead_leaves",
                        LeavesBlock::new,
                        AbstractBlock.Settings.copy(Blocks.OAK_LEAVES)
                                        .sounds(BlockSoundGroup.GRASS)
                                        .nonOpaque());

        // ===== WOOD BLOCKS =====

        public static final Block DECAYED_LOG = registerBlock("decayed_log",
                        PillarBlock::new,
                        AbstractBlock.Settings.copy(Blocks.OAK_LOG)
                                        .sounds(BlockSoundGroup.WOOD));

        public static final Block DECAYED_PLANKS = registerBlock("decayed_planks",
                        Block::new,
                        AbstractBlock.Settings.copy(Blocks.OAK_PLANKS)
                                        .sounds(BlockSoundGroup.WOOD));

        // ===== OTHER BLOCKS =====

        public static final Block CRACKED_GLASS = registerBlock("cracked_glass",
                        TransparentBlock::new,
                        AbstractBlock.Settings.copy(Blocks.GLASS)
                                        .sounds(BlockSoundGroup.GLASS)
                                        .nonOpaque());

        /**
         * Registers a block with its block item.
         */
        private static <T extends Block> T registerBlock(String name, Function<AbstractBlock.Settings, T> factory,
                        AbstractBlock.Settings settings) {
                Identifier id = Identifier.of(UpsideDownMod.MOD_ID, name);
                RegistryKey<Block> blockKey = RegistryKey.of(RegistryKeys.BLOCK, id);

                // Create block - settings already have the key in 1.21.1
                T block = factory.apply(settings);

                // Register block
                Registry.register(Registries.BLOCK, blockKey, block);

                // Register block item
                RegistryKey<Item> itemKey = RegistryKey.of(RegistryKeys.ITEM, id);
                BlockItem blockItem = new BlockItem(block, new Item.Settings());
                Registry.register(Registries.ITEM, itemKey, blockItem);

                return block;
        }

        /**
         * Called during mod initialization to register all blocks.
         */
        public static void register() {
                UpsideDownMod.LOGGER.info("Registering blocks for {}", UpsideDownMod.MOD_ID);

                // Add blocks to creative tab
                ItemGroupEvents.modifyEntriesEvent(ItemGroups.BUILDING_BLOCKS).register(entries -> {
                        entries.add(CORRUPTED_GRASS);
                        entries.add(CORRUPTED_DIRT);
                        entries.add(CORRUPTED_STONE);
                        entries.add(DECAYED_LOG);
                        entries.add(DECAYED_PLANKS);
                        entries.add(CRACKED_GLASS);
                });

                ItemGroupEvents.modifyEntriesEvent(ItemGroups.NATURAL).register(entries -> {
                        entries.add(DEAD_GRASS);
                        entries.add(DEAD_FLOWER);
                        entries.add(DEAD_LEAVES);
                });
        }
}
