package me.ultrusmods.colorfulcreakings.register;

import me.ultrusmods.colorfulcreakings.Constants;
import me.ultrusmods.colorfulcreakings.block.ColoredResinBlocks;
import me.ultrusmods.colorfulcreakings.block.entity.ColoredCreakingHeartBlockEntity;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntityType;

import java.util.Set;
import java.util.stream.Collectors;

public class ColorfulCreakingsBlockEntities {
    private static final Set<Block> COLORED_CREAKING_HEART_BLOCKS = ColoredResinBlocks.BLOCK_SET_MAP.values().stream().map(ColoredResinBlocks.ColoredResinBlockSet::creakingHeart).filter(block -> block != Blocks.CREAKING_HEART).collect(Collectors.toSet());
    public static BlockEntityType<ColoredCreakingHeartBlockEntity> COLORED_CREAKING_HEART_BLOCK_ENTITY = new BlockEntityType<>(ColoredCreakingHeartBlockEntity::new, COLORED_CREAKING_HEART_BLOCKS);

    public static void register() {
        Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, Constants.id("colored_creaking_heart"), COLORED_CREAKING_HEART_BLOCK_ENTITY);
    }
}
