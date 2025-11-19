package dev.voltron.stratosphere.client;

import dev.voltron.stratosphere.Stratosphere;
import foundry.veil.api.client.render.VeilRenderSystem;
import foundry.veil.api.client.render.post.PostPipeline;
import foundry.veil.api.client.render.post.PostProcessingManager;
import foundry.veil.api.event.VeilRenderLevelStageEvent;
import foundry.veil.platform.VeilEventPlatform;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.Camera;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import org.joml.Vector3f;

public class ShaderManager {
    public static void reg(){
        VeilEventPlatform.INSTANCE.onVeilRenderLevelStage((stage, worldRenderer, immediate, matrixStack, matrix4fc, matrix4fc1, i, renderTickCounter, camera, frustum) -> {
            MinecraftClient client = MinecraftClient.getInstance();
            ClientWorld world = client.world;
            if (world != null){
                float time = MathHelper.lerp(renderTickCounter.getTickDelta(false), (world.getTimeOfDay() - 1) % 24000, world.getTimeOfDay() % 24000); //Lerp for smoothed time
                PostProcessingManager manager = VeilRenderSystem.renderer().getPostProcessingManager();

                if (stage == VeilRenderLevelStageEvent.Stage.AFTER_SKY) {
                    registerSpaceSkybox(manager, time, camera);
                }
            }
        });
    }

    public static void registerSpaceSkybox(PostProcessingManager manager, float time, Camera camera){
        PostPipeline space = manager.getPipeline(Identifier.of(Stratosphere.MOD_ID, "space"));
        if (space != null){
            space.getOrCreateUniform("Position").setVector(camera.getPos().toVector3f());
            space.getOrCreateUniform("Radius").setFloat(100);
            space.getOrCreateUniform("Time").setFloat(time);

            //SpaceGasSettings
            //TODO: Make a system swapping system
            space.getOrCreateUniform("Density").setFloat(100);
            space.getOrCreateUniform("Power").setFloat(0.25F);
            space.getOrCreateUniform("Color").setVector(new Vector3f((float) 153 / 255, (float) 178 / 255, (float) 255 / 255));

            manager.runPipeline(space);
        } else {
            Stratosphere.LOGGER.error("PostPipeline: 'space' is null.");
        }
    }
}
