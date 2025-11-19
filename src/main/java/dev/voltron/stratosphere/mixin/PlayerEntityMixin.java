package dev.voltron.stratosphere.mixin;

import dev.voltron.stratosphere.Stratosphere;
import dev.voltron.stratosphere.content.entity.abst.AbstractShipEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity {
    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "travel", at = @At("HEAD"), cancellable = true)
    private void stratosphere$shipMovement(Vec3d movementInput, CallbackInfo ci){
        if (this.hasVehicle()){
            if (this.getVehicle() instanceof AbstractShipEntity ship){
                ship.travel(movementInput, this.jumping);
                Stratosphere.LOGGER.info("SHIP SHIP SHIP");
                ci.cancel();
            }
        }
    }
}
