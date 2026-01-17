package com.upsidedown.dimension;

import com.upsidedown.UpsideDownMod;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;

/**
 * Registers The Upside Down dimension.
 * 
 * The dimension uses the same seed as the Overworld to ensure
 * identical terrain and structure generation.
 */
public class ModDimensions {

    // Dimension key for The Upside Down
    public static final RegistryKey<World> UPSIDE_DOWN_KEY = RegistryKey.of(
            RegistryKeys.WORLD,
            Identifier.of(UpsideDownMod.MOD_ID, "the_upside_down"));

    // Dimension type key
    public static final RegistryKey<DimensionType> UPSIDE_DOWN_TYPE_KEY = RegistryKey.of(
            RegistryKeys.DIMENSION_TYPE,
            Identifier.of(UpsideDownMod.MOD_ID, "the_upside_down"));

    /**
     * Check if a world is The Upside Down.
     */
    public static boolean isUpsideDown(World world) {
        return world.getRegistryKey().equals(UPSIDE_DOWN_KEY);
    }

    /**
     * Called during mod initialization.
     * The dimension type is defined in JSON
     * (data/upsidedown/dimension_type/the_upside_down.json)
     * The dimension itself is defined in JSON
     * (data/upsidedown/dimension/the_upside_down.json)
     */
    public static void register() {
        UpsideDownMod.LOGGER.info("Registering The Upside Down dimension");

        // Dimension is registered via datapack JSON files
        // See: src/main/resources/data/upsidedown/dimension/
        // See: src/main/resources/data/upsidedown/dimension_type/
    }
}
