package dev.voltron.stratosphere.init;

import dev.voltron.stratosphere.Stratosphere;
import dev.voltron.stratosphere.content.entity.abst.AbstractShipEntity;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModEntities {
    public static EntityType<AbstractShipEntity> SHIP = Registry.register(
            Registries.ENTITY_TYPE,
            Identifier.of(Stratosphere.MOD_ID, "ship"),
            FabricEntityTypeBuilder.create(SpawnGroup.MISC, AbstractShipEntity::new).dimensions(EntityDimensions.fixed(1.0F, 1.0F)).trackRangeChunks(128).build()
    );

    public static void init(){
    }
}
