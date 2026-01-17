package com.upsidedown.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.upsidedown.UpsideDownMod;
import net.fabricmc.loader.api.FabricLoader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Configuration for The Upside Down mod.
 * Settings are stored per-world and loaded at startup.
 */
public class ModConfig {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static ModConfig INSTANCE = new ModConfig();

    /**
     * Sync mode determines how the Upside Down dimension relates to the Overworld.
     */
    public enum SyncMode {
        /**
         * MIRROR: Player-built structures sync into the Upside Down when you enter,
         * appearing decayed with vines and cobwebs. MC structures always have decay.
         */
        MIRROR,

        /**
         * SNAPSHOT: The entire Overworld is copied once when you first enter the
         * Upside Down. After that, dimensions are completely independent.
         */
        SNAPSHOT
    }

    // Sync mode setting - default to MIRROR (recommended)
    public SyncMode syncMode = SyncMode.MIRROR;

    // Whether to apply decay effects (vines, cobwebs) to structures
    public boolean applyDecayEffects = true;

    // Particle density multiplier (0.0 to 2.0, 1.0 = normal)
    public float sporeParticleDensity = 1.0f;

    // Fog density in the Upside Down (0.0 to 1.0)
    public float fogDensity = 0.8f;

    // Whether the dimension has been initialized (for snapshot mode)
    public boolean dimensionInitialized = false;

    /**
     * Gets the current configuration instance.
     */
    public static ModConfig get() {
        return INSTANCE;
    }

    /**
     * Loads the configuration from file, or creates default if not exists.
     */
    public static void load() {
        Path configPath = getConfigPath();

        if (Files.exists(configPath)) {
            try {
                String json = Files.readString(configPath);
                INSTANCE = GSON.fromJson(json, ModConfig.class);
                UpsideDownMod.LOGGER.info("Loaded configuration from {}", configPath);
            } catch (IOException e) {
                UpsideDownMod.LOGGER.error("Failed to load config, using defaults", e);
                INSTANCE = new ModConfig();
            }
        } else {
            INSTANCE = new ModConfig();
            save();
            UpsideDownMod.LOGGER.info("Created default configuration at {}", configPath);
        }
    }

    /**
     * Saves the current configuration to file.
     */
    public static void save() {
        Path configPath = getConfigPath();

        try {
            Files.createDirectories(configPath.getParent());
            Files.writeString(configPath, GSON.toJson(INSTANCE));
        } catch (IOException e) {
            UpsideDownMod.LOGGER.error("Failed to save config", e);
        }
    }

    private static Path getConfigPath() {
        return FabricLoader.getInstance().getConfigDir().resolve("upsidedown.json");
    }
}
