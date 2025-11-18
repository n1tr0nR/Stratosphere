package dev.voltron.stratosphere.init;

import dev.voltron.stratosphere.Stratosphere;
import dev.voltron.stratosphere.content.block.ShipConstructorBlock;
import dev.voltron.stratosphere.content.entity.ShipEntity;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;

public class ModBlocks {
    public static void init(){
    }

    public static Block SHIP_CONSTRUCTOR = generate("ship_constructor", new ShipConstructorBlock(
            AbstractBlock.Settings.create().strength(3.6F).sounds(BlockSoundGroup.STONE)
    ));

    private static Block generate(String name, Block block){
        Registry.register(Registries.ITEM,
                Identifier.of(Stratosphere.MOD_ID, name), new BlockItem(block, new Item.Settings()));
        return Registry.register(Registries.BLOCK,
                Identifier.of(Stratosphere.MOD_ID, name), block);
    }
}
