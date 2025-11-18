package dev.voltron.stratosphere.content.item;

import dev.voltron.stratosphere.Stratosphere;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModItems {
    public static final Item RATIONS = registerItem("rations", new Item(new Item.Settings().food(ModRationItems.RATIONS)));

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, Identifier.of(Stratosphere.MOD_ID, name), item);
    }

    public static void registerModItems() {}
}