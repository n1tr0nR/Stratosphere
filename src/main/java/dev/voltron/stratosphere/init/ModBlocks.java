package dev.voltron.stratosphere.init;

import dev.voltron.stratosphere.Stratosphere;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;

public class ModBlocks {
    public static void init(){
    }

    private static Block generate(String name, Block block){
        Registry.register(Registries.ITEM,
                Identifier.of(Stratosphere.MOD_ID, name), new BlockItem(block, new Item.Settings()));
        return Registry.register(Registries.BLOCK,
                Identifier.of(Stratosphere.MOD_ID, name), block);
    }
}
