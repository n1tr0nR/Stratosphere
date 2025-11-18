package dev.voltron.stratosphere.client.render.entity;

import dev.voltron.stratosphere.content.entity.ShipEntity;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.*;
import net.minecraft.client.render.block.BlockRenderManager;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;

import java.util.Map;

public class ShipEntityRenderer extends EntityRenderer<ShipEntity> {
    final BlockRenderManager blockRenderManager;

    public ShipEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx);
        blockRenderManager = ctx.getBlockRenderManager();
    }

    @Override
    public void render(ShipEntity entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {

    }

    private void drawBlockOutline(MatrixStack matrices, Vec3i pos, VertexConsumerProvider vertexConsumers) {
        Box box = new Box(0, 0, 0, 1, 1, 1);

        VertexConsumer vc = vertexConsumers.getBuffer(RenderLayer.getLines());

        float r = 1.0f, g = 1.0f, b = 0.0f, a = 1.0f;

        WorldRenderer.drawBox(
                matrices,
                vc,
                box.minX, box.minY, box.minZ,
                box.maxX, box.maxY, box.maxZ,
                r, g, b, a
        );
    }


    @Override
    public Identifier getTexture(ShipEntity entity) {
        return null;
    }
}
