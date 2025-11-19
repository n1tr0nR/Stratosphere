package dev.voltron.stratosphere.init;

import dev.voltron.stratosphere.Stratosphere;
import dev.voltron.stratosphere.content.cca.ShipComponent;
import dev.voltron.stratosphere.content.entity.abst.AbstractShipEntity;
import net.minecraft.util.Identifier;
import org.ladysnake.cca.api.v3.component.ComponentKey;
import org.ladysnake.cca.api.v3.entity.EntityComponentFactoryRegistry;
import org.ladysnake.cca.api.v3.entity.EntityComponentInitializer;
import org.ladysnake.cca.api.v3.world.WorldComponentFactoryRegistry;
import org.ladysnake.cca.api.v3.world.WorldComponentInitializer;

public class ModComponents implements WorldComponentInitializer, EntityComponentInitializer {
    public static final ComponentKey<ShipComponent> SHIP = org.ladysnake.cca.api.v3.component.ComponentRegistry.getOrCreate(Identifier.of(Stratosphere.MOD_ID, "ship"), ShipComponent.class);


    @Override
    public void registerWorldComponentFactories(WorldComponentFactoryRegistry worldComponentFactoryRegistry) {

    }

    @Override
    public void registerEntityComponentFactories(EntityComponentFactoryRegistry entityComponentFactoryRegistry) {
        entityComponentFactoryRegistry.registerFor(AbstractShipEntity.class, SHIP, ShipComponent::new);
    }
}
