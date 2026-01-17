package com.upsidedown;

import com.upsidedown.block.ModBlocks;
import com.upsidedown.config.ModConfig;
import com.upsidedown.dimension.ModDimensions;
import com.upsidedown.item.ModItems;
import com.upsidedown.particle.ModParticles;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Main entry point for The Upside Down mod.
 * 
 * This mod creates a parallel dimension that mirrors the Overworld
 * with a dark, corrupted Stranger Things aesthetic.
 */
public class UpsideDownMod implements ModInitializer {
    public static final String MOD_ID = "upsidedown";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        LOGGER.info("Initializing The Upside Down...");

        // Load configuration
        ModConfig.load();

        // Register all mod content
        ModBlocks.register();
        ModItems.register();
        ModParticles.register();
        ModDimensions.register();

        LOGGER.info("The Upside Down has opened.");
    }
}
