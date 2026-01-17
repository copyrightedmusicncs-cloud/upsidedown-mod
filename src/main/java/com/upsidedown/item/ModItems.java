package com.upsidedown.item;

import com.upsidedown.UpsideDownMod;
import com.upsidedown.dimension.ModDimensions;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

/**
 * Registers all mod items.
 */
public class ModItems {

    // Portal Catalyst - used to teleport between dimensions
    public static final Item PORTAL_CATALYST = registerItem("portal_catalyst",
            new PortalCatalystItem(new Item.Settings()
                    .maxCount(1)
                    .registryKey(RegistryKey.of(RegistryKeys.ITEM,
                            Identifier.of(UpsideDownMod.MOD_ID, "portal_catalyst")))));

    private static Item registerItem(String name, Item item) {
        Identifier id = Identifier.of(UpsideDownMod.MOD_ID, name);
        return Registry.register(Registries.ITEM, id, item);
    }

    public static void register() {
        UpsideDownMod.LOGGER.info("Registering items for {}", UpsideDownMod.MOD_ID);

        // Add to creative tab
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(entries -> {
            entries.add(PORTAL_CATALYST);
        });
    }
}
