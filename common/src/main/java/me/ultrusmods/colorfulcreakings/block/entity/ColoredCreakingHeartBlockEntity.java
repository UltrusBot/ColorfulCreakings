package me.ultrusmods.colorfulcreakings.block.entity;

import me.ultrusmods.colorfulcreakings.register.ColorfulCreakingsBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.CreakingHeartBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class ColoredCreakingHeartBlockEntity extends CreakingHeartBlockEntity {
    public ColoredCreakingHeartBlockEntity(BlockPos pos, BlockState state) {
        super(pos, state);
    }

    @Override
    public BlockEntityType<?> getType() {
        return ColorfulCreakingsBlockEntities.COLORED_CREAKING_HEART_BLOCK_ENTITY;
    }


    @Override
    public boolean isValidBlockState(BlockState state) {
        return ColorfulCreakingsBlockEntities.COLORED_CREAKING_HEART_BLOCK_ENTITY.isValid(state);
    }
}
