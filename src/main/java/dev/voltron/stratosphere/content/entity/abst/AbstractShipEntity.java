package dev.voltron.stratosphere.content.entity.abst;

import dev.voltron.stratosphere.mixin.PlayerEntityMixin;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.*;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.joml.Quaternionf;

public class AbstractShipEntity extends Entity {
    public AbstractShipEntity(EntityType<?> type, World world) {
        super(type, world);
    }

    @Override
    protected void initDataTracker(DataTracker.Builder builder) {

    }

    @Override
    protected void readCustomDataFromNbt(NbtCompound nbt) {

    }

    @Override
    protected void writeCustomDataToNbt(NbtCompound nbt) {

    }

    public void travel(Vec3d movementInput, boolean jumping){
        double side = movementInput.x;
        double forward = movementInput.z;

        float turnSpeed = 30.0f;
        float pitchSpeed = 30.0f;

        float targetYaw = this.getYaw() + (float)(-side * turnSpeed);
        this.setYaw(MathHelper.lerp(0.2f, this.getYaw(), targetYaw));

        float targetPitch = this.getPitch() + (float)(forward * pitchSpeed);
        this.setPitch(MathHelper.clamp(MathHelper.lerp(0.2f, this.getPitch(), targetPitch), -90f, 90f));

        double yawRad = Math.toRadians(this.getYaw());
        double pitchRad = Math.toRadians(this.getPitch());

        double sinYaw = Math.sin(yawRad);
        double cosYaw = Math.cos(yawRad);
        double sinPitch = Math.sin(pitchRad);
        double cosPitch = Math.cos(pitchRad);

        double targetX = 0;
        double targetY = 0;
        double targetZ = 0;

        if(jumping){
            targetX = -sinYaw * cosPitch * 1.6;
            targetY = -sinPitch * 1.6;
            targetZ = cosYaw * cosPitch * 1.6;
        }

        Vec3d velocity = this.getVelocity();
        double smooth = 0.1;

        Vec3d smoothedVelocity = velocity.lerp(new Vec3d(targetX, targetY != 0 ? targetY : velocity.y, targetZ), smooth);

        this.setVelocity(smoothedVelocity);
        this.move(MovementType.SELF, smoothedVelocity);

        this.prevYaw = this.getYaw();
        this.prevPitch = this.getPitch();
    }

}
