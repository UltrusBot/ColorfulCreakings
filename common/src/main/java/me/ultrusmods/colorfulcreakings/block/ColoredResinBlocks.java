package me.ultrusmods.colorfulcreakings.block;

import me.ultrusmods.colorfulcreakings.Constants;
import me.ultrusmods.colorfulcreakings.data.CreakingColor;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;

import java.util.HashMap;
import java.util.Map;

public class ColoredResinBlocks {
    public static final Map<CreakingColor, ColoredResinBlockSet> BLOCK_SET_MAP = new HashMap<>();

    public record ColoredResinBlockSet(CreakingColor color, Block baseBlock, Block bricks, Block brickStairs,
                                       Block brickSlab, Block brickWall, Block chiseledBricks, Block clump, Block creakingHeart) {
        public static ColoredResinBlockSet create(CreakingColor color) {
            var baseBlockProperties = BlockBehaviour.Properties.of().mapColor(color.getDyeColor().getMapColor()).instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.RESIN);
            baseBlockProperties.setId(resinBlockId(color, "resin_block"));
            var brickProperties = BlockBehaviour.Properties.of().mapColor(color.getDyeColor().getMapColor()).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().sound(SoundType.RESIN_BRICKS).strength(1.5F, 6.0F);
            brickProperties.setId(resinBlockId(color, "resin_bricks"));
            var resinBricks = new Block(brickProperties);
            var brickStairsProperties = BlockBehaviour.Properties.of().mapColor(color.getDyeColor().getMapColor()).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().sound(SoundType.RESIN_BRICKS).strength(1.5F, 6.0F);
            brickStairsProperties.setId(resinBlockId(color, "resin_brick_stairs"));
            var brickSlabProperties = BlockBehaviour.Properties.of().mapColor(color.getDyeColor().getMapColor()).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().sound(SoundType.RESIN_BRICKS).strength(1.5F, 6.0F);
            brickSlabProperties.setId(resinBlockId(color, "resin_brick_slab"));
            var brickWallProperties = BlockBehaviour.Properties.of().mapColor(color.getDyeColor().getMapColor()).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().sound(SoundType.RESIN_BRICKS).strength(1.5F, 6.0F);
            brickWallProperties.setId(resinBlockId(color, "resin_brick_wall"));
            var chiseledBricksProperties = BlockBehaviour.Properties.of().mapColor(color.getDyeColor().getMapColor()).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().sound(SoundType.RESIN_BRICKS).strength(1.5F, 6.0F);
            chiseledBricksProperties.setId(resinBlockId(color, "chiseled_resin_bricks"));

            var clumpProperties = BlockBehaviour.Properties.of().mapColor(color.getDyeColor().getMapColor()).replaceable().noCollission().sound(SoundType.RESIN).ignitedByLava().pushReaction(PushReaction.DESTROY);
            clumpProperties.setId(resinBlockId(color, "resin_clump"));

            var creakingHeartProperties = BlockBehaviour.Properties.of().mapColor(color.getDyeColor().getMapColor()).instrument(NoteBlockInstrument.BASEDRUM).strength(10.0F).sound(SoundType.CREAKING_HEART);
            creakingHeartProperties.setId(resinBlockId(color, "creaking_heart"));



            ColoredResinBlockSet coloredResinBlockSet = new ColoredResinBlockSet(color,
                    new Block(baseBlockProperties),
                    resinBricks,
                    new StairBlock(resinBricks.defaultBlockState(), brickStairsProperties),
                    new SlabBlock(brickSlabProperties),
                    new WallBlock(brickWallProperties),
                    new Block(chiseledBricksProperties),
                    new MultifaceBlock(clumpProperties),
                    new ColoredCreakingHeartBlock(creakingHeartProperties, color));
            BLOCK_SET_MAP.put(color, coloredResinBlockSet);
            return coloredResinBlockSet;
        }
        public static ColoredResinBlockSet createOrangeBase() {
            ColoredResinBlockSet coloredResinBlockSet = new ColoredResinBlockSet(
                    CreakingColor.ORANGE,
                    Blocks.RESIN_BLOCK,
                    Blocks.RESIN_BRICKS,
                    Blocks.RESIN_BRICK_STAIRS,
                    Blocks.RESIN_BRICK_SLAB,
                    Blocks.RESIN_BRICK_WALL,
                    Blocks.CHISELED_RESIN_BRICKS,
                    Blocks.RESIN_CLUMP,
                    Blocks.CREAKING_HEART
            );
            BLOCK_SET_MAP.put(CreakingColor.ORANGE, coloredResinBlockSet);
            return coloredResinBlockSet;
        }

        public void register() {
            Registry.register(BuiltInRegistries.BLOCK, resinBlockId(color, "resin_block"), baseBlock);
            Registry.register(BuiltInRegistries.BLOCK, resinBlockId(color, "resin_bricks"), bricks);
            Registry.register(BuiltInRegistries.BLOCK, resinBlockId(color, "resin_brick_stairs"), brickStairs);
            Registry.register(BuiltInRegistries.BLOCK, resinBlockId(color, "resin_brick_slab"), brickSlab);
            Registry.register(BuiltInRegistries.BLOCK, resinBlockId(color, "resin_brick_wall"), brickWall);
            Registry.register(BuiltInRegistries.BLOCK, resinBlockId(color, "chiseled_resin_bricks"), chiseledBricks);
            Registry.register(BuiltInRegistries.BLOCK, resinBlockId(color, "resin_clump"), clump);
            Registry.register(BuiltInRegistries.BLOCK, resinBlockId(color, "creaking_heart"), creakingHeart);
        }
    }

    private static ResourceKey<Block> resinBlockId(CreakingColor color, String name) {
        return ResourceKey.create(Registries.BLOCK, Constants.id(color.getSerializedName() + "_" + name));
    }
}
