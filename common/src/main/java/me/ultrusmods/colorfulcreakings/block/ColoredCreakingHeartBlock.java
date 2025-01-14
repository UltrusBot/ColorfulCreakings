package me.ultrusmods.colorfulcreakings.block;

import me.ultrusmods.colorfulcreakings.block.entity.ColoredCreakingHeartBlockEntity;
import me.ultrusmods.colorfulcreakings.data.CreakingColor;
import me.ultrusmods.colorfulcreakings.register.ColorfulCreakingsBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.CreakingHeartBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.CreakingHeartBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class ColoredCreakingHeartBlock extends CreakingHeartBlock {


    private final CreakingColor color;
    protected ColoredCreakingHeartBlock(Properties p_380228_, CreakingColor color) {
        super(p_380228_);
        this.color = color;
    }

    public CreakingColor getColor() {
        return color;
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level p_379447_, BlockState p_379641_, BlockEntityType<T> p_380325_) {
        if (p_379447_.isClientSide) {
            return null;
        } else {
            return p_379641_.getValue(ACTIVE) ? createTickerHelper(p_380325_, ColorfulCreakingsBlockEntities.COLORED_CREAKING_HEART_BLOCK_ENTITY, CreakingHeartBlockEntity::serverTick) : null;
        }
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new ColoredCreakingHeartBlockEntity(blockPos, blockState);
    }
}
