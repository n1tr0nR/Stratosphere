package dev.voltron.stratosphere.helper;

import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;

import java.util.List;

public class VanillaCollision {
    public static Vec3d solve(Vec3d movement, Box entityBoundingBox, List<VoxelShape> collisions) {
        if (collisions.isEmpty()) {
            return movement;
        } else {
            double d = movement.x;
            double e = movement.y;
            double f = movement.z;
            if (e != (double)0.0F) {
                e = VoxelShapes.calculateMaxOffset(Direction.Axis.Y, entityBoundingBox, collisions, e);
                if (e != (double)0.0F) {
                    entityBoundingBox = entityBoundingBox.offset((double)0.0F, e, (double)0.0F);
                }
            }

            boolean bl = Math.abs(d) < Math.abs(f);
            if (bl && f != (double)0.0F) {
                f = VoxelShapes.calculateMaxOffset(Direction.Axis.Z, entityBoundingBox, collisions, f);
                if (f != (double)0.0F) {
                    entityBoundingBox = entityBoundingBox.offset((double)0.0F, (double)0.0F, f);
                }
            }

            if (d != (double)0.0F) {
                d = VoxelShapes.calculateMaxOffset(Direction.Axis.X, entityBoundingBox, collisions, d);
                if (!bl && d != (double)0.0F) {
                    entityBoundingBox = entityBoundingBox.offset(d, (double)0.0F, (double)0.0F);
                }
            }

            if (!bl && f != (double)0.0F) {
                f = VoxelShapes.calculateMaxOffset(Direction.Axis.Z, entityBoundingBox, collisions, f);
            }

            return new Vec3d(d, e, f);
        }
    }
}
