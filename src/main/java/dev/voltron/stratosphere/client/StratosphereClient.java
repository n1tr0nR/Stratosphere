package dev.voltron.stratosphere.client;

import dev.voltron.stratosphere.client.render.entity.ShipEntityRenderer;
import dev.voltron.stratosphere.init.ModEntities;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;

public class StratosphereClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.register(ModEntities.SHIP, ShipEntityRenderer::new);
    }
}
