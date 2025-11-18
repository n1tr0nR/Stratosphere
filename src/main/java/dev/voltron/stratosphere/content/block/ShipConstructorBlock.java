package dev.voltron.stratosphere.content.block;

import dev.voltron.stratosphere.content.entity.ShipEntity;
import dev.voltron.stratosphere.init.ModEntities;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;

import java.util.HashMap;
import java.util.Map;

public class ShipConstructorBlock extends Block {
    public ShipConstructorBlock(Settings settings) {
        super(settings);
    }

    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        return flip(pos, world, player, super.onUse(state, world, pos, player, hit));
    }

    public ActionResult flip(BlockPos pos, World world, PlayerEntity player, ActionResult def){

        return def;
    }
}
