package dev.voltron.stratosphere.content.entity;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.nbt.NbtList;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;

import java.util.HashMap;
import java.util.Map;

public class ShipEntity extends Entity {
    private final Map<Vec3i, BlockState> map = new HashMap<>();

    public ShipEntity(EntityType<?> type, World world) {
        super(type, world);

        map.put(new Vec3i(0, 0, 0), Blocks.OAK_PLANKS.getDefaultState());
    }

    @Override
    protected void initDataTracker(DataTracker.Builder builder) {

    }

    @Override
    protected void readCustomDataFromNbt(NbtCompound nbt) {
        map.clear();

        NbtList entries = nbt.getList("Blocks", NbtElement.COMPOUND_TYPE);
        for (int i = 0; i < entries.size(); i++) {
            NbtCompound entry = entries.getCompound(i);

            Vec3i pos = new Vec3i(
                    entry.getInt("x"),
                    entry.getInt("y"),
                    entry.getInt("z")
            );

            RegistryEntryLookup<Block> blockLookup = this.getWorld().getRegistryManager()
                    .getWrapperOrThrow(RegistryKeys.BLOCK);

            BlockState state = NbtHelper.toBlockState(blockLookup, entry.getCompound("State"));
            map.put(pos, state);
        }
    }

    @Override
    protected void writeCustomDataToNbt(NbtCompound nbt) {
        NbtList entries = new NbtList();

        for (Map.Entry<Vec3i, BlockState> e : map.entrySet()) {
            NbtCompound entry = new NbtCompound();

            Vec3i pos = e.getKey();
            BlockState state = e.getValue();

            entry.putInt("x", pos.getX());
            entry.putInt("y", pos.getY());
            entry.putInt("z", pos.getZ());

            entry.put("State", NbtHelper.fromBlockState(state));

            entries.add(entry);
        }

        nbt.put("Blocks", entries);
    }
}
