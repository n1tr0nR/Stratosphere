package dev.voltron.stratosphere.client.render.entity;

import dev.voltron.stratosphere.content.entity.abst.AbstractShipEntity;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.Identifier;

public class ShipEntityRenderer extends EntityRenderer<AbstractShipEntity> {
    public ShipEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx);
    }

    @Override
    public Identifier getTexture(AbstractShipEntity entity) {
        return null;
    }
}
