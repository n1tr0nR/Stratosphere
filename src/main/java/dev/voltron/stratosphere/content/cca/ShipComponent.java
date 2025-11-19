package dev.voltron.stratosphere.content.cca;

import dev.voltron.stratosphere.content.entity.abst.AbstractShipEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.math.Vec3d;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;
import org.ladysnake.cca.api.v3.component.tick.CommonTickingComponent;

public class ShipComponent implements AutoSyncedComponent, CommonTickingComponent {
    private final AbstractShipEntity ship;

    public Vec3d acceleration = new Vec3d(0, 0, 0);
    public Vec3d velocity = new Vec3d(0, 0, 0);
    public Vec3d angularVelocity = new Vec3d(0, 0, 0);

    public boolean inertia = false;

    public ShipComponent(AbstractShipEntity ship) {
        this.ship = ship;
    }

    @Override
    public void tick() {

    }

    @Override
    public void readFromNbt(NbtCompound nbtCompound, RegistryWrapper.WrapperLookup wrapperLookup) {

    }

    @Override
    public void writeToNbt(NbtCompound nbtCompound, RegistryWrapper.WrapperLookup wrapperLookup) {

    }
}
