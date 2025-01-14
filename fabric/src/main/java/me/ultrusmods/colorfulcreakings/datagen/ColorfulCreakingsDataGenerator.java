package me.ultrusmods.colorfulcreakings.datagen;

import me.ultrusmods.colorfulcreakings.Constants;
import me.ultrusmods.colorfulcreakings.block.ColoredResinBlocks;
import me.ultrusmods.colorfulcreakings.data.CreakingColor;
import me.ultrusmods.colorfulcreakings.item.ColoredResinItems;
import me.ultrusmods.colorfulcreakings.tag.ColorfulCreakingsBlockTags;
import me.ultrusmods.colorfulcreakings.tag.ColorfulCreakingsItemTags;
import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalBlockTags;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.blockstates.*;
import net.minecraft.client.data.models.model.*;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.BlockFamilies;
import net.minecraft.data.BlockFamily;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.SimpleCookingRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CreakingHeartBlock;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

import java.util.EnumMap;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

public class ColorfulCreakingsDataGenerator implements DataGeneratorEntrypoint {
    public static EnumMap<CreakingColor, BlockFamily> BLOCK_FAMILIES = new EnumMap<>(CreakingColor.class);

    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        var pack = fabricDataGenerator.createPack();
        pack.addProvider(ColorfulCreakingsModelProvider::new);
        pack.addProvider(ColorfulCreakingsBlockTagProvider::new);
        pack.addProvider(ColorfulCreakingsItemTagProvider::new);
        pack.addProvider(ColorfulCreakingsRecipeProviderRunner::new);

    }

    public static final class ColorfulCreakingsModelProvider extends FabricModelProvider {
        public ColorfulCreakingsModelProvider(FabricDataOutput output) {
            super(output);
        }

        @Override
        public void generateBlockStateModels(BlockModelGenerators blockModelGenerators) {
            ColoredResinBlocks.BLOCK_SET_MAP.forEach((creakingColor, blocks) -> {
                if (creakingColor == CreakingColor.ORANGE) {
                    return;
                }
                var items = ColoredResinItems.ITEM_SET_MAP.get(creakingColor);

                blockModelGenerators.createMultifaceBlockStates(blocks.clump());
                blockModelGenerators.createTrivialCube(blocks.baseBlock());
                var blockFamily = BlockFamilies.familyBuilder(blocks.bricks())
                        .stairs(blocks.brickStairs())
                        .slab(blocks.brickSlab())
                        .wall(blocks.brickWall())
                        .chiseled(blocks.chiseledBricks()).getFamily();
                blockModelGenerators.family(blocks.bricks()).generateFor(blockFamily);
                BLOCK_FAMILIES.put(creakingColor, blockFamily);

                generateCreakingHeartBlockModel(blocks.creakingHeart(), blockModelGenerators);
            });
        }

        public void generateCreakingHeartBlockModel(Block block, BlockModelGenerators blockModelGenerators) {
            if (block == Blocks.CREAKING_HEART) {
                Constants.LOG.error("Creaking heart block is wrong");
            }
            Function<TexturedModel.Provider, ResourceLocation> function = (provider) -> provider.updateTexture((textureMapping) -> {
                textureMapping.put(TextureSlot.SIDE, TextureMapping.getBlockTexture(block, "_active"));
            }).updateTexture((textureMapping) -> {
                textureMapping.put(TextureSlot.END, TextureMapping.getBlockTexture(block, "_top_active"));
            }).createWithSuffix(block, "_active", blockModelGenerators.modelOutput);

            Function<TexturedModel.Provider, ResourceLocation> regular = (provider) -> provider.updateTexture((textureMapping) -> {
                textureMapping.put(TextureSlot.SIDE, TextureMapping.getBlockTexture(block, "_active"));
            }).updateTexture((textureMapping) -> {
                textureMapping.put(TextureSlot.END, TextureMapping.getBlockTexture(block, "_top_active"));
            }).create(block, blockModelGenerators.modelOutput);
            regular.apply(TexturedModel.COLUMN_ALT);
            regular.apply(TexturedModel.COLUMN_HORIZONTAL_ALT);

            ResourceLocation topActive = function.apply(TexturedModel.COLUMN_ALT);
            ResourceLocation sideActive = function.apply(TexturedModel.COLUMN_HORIZONTAL_ALT);
            ResourceLocation topNormal = ResourceLocation.withDefaultNamespace("block/creaking_heart");
            ResourceLocation sideNormal = ResourceLocation.withDefaultNamespace("block/creaking_heart_horizontal");
            blockModelGenerators.blockStateOutput.accept(
                    MultiVariantGenerator.multiVariant(block)
                            .with(PropertyDispatch.properties(BlockStateProperties.AXIS, CreakingHeartBlock.ACTIVE)
                                    .select(Direction.Axis.Y, false, Variant.variant().with(VariantProperties.MODEL, topNormal))
                                    .select(Direction.Axis.Z, false, Variant.variant().with(VariantProperties.MODEL, sideNormal).with(VariantProperties.X_ROT, VariantProperties.Rotation.R90))
                                    .select(Direction.Axis.X, false, Variant.variant().with(VariantProperties.MODEL, sideNormal).with(VariantProperties.X_ROT, VariantProperties.Rotation.R90).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90))
                                    .select(Direction.Axis.Y, true, Variant.variant().with(VariantProperties.MODEL, topActive))
                                    .select(Direction.Axis.Z, true, Variant.variant().with(VariantProperties.MODEL, sideActive).with(VariantProperties.X_ROT, VariantProperties.Rotation.R90))
                                    .select(Direction.Axis.X, true, Variant.variant().with(VariantProperties.MODEL, sideActive).with(VariantProperties.X_ROT, VariantProperties.Rotation.R90).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90))));

        }

        @Override
        public void generateItemModels(ItemModelGenerators itemModelGenerators) {
            ColoredResinItems.ITEM_SET_MAP.forEach((creakingColor, itemSet) -> {
                if (creakingColor == CreakingColor.ORANGE) {
                    return;
                }
                itemModelGenerators.generateFlatItem(itemSet.brick(), ModelTemplates.FLAT_ITEM);
                itemModelGenerators.generateFlatItem(itemSet.clump(), ModelTemplates.FLAT_ITEM);
                ;
            });
        }


    }

    public static final class ColorfulCreakingsBlockTagProvider extends FabricTagProvider.BlockTagProvider {
        public ColorfulCreakingsBlockTagProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
            super(output, registriesFuture);
        }

        @Override
        protected void addTags(HolderLookup.Provider provider) {
            var stairsTag = this.getOrCreateTagBuilder(BlockTags.STAIRS);
            var slabTag = this.getOrCreateTagBuilder(BlockTags.SLABS);
            var wallTag = this.getOrCreateTagBuilder(BlockTags.WALLS);
            var COMBINATION_STEP_SOUND_BLOCKS = this.getOrCreateTagBuilder(BlockTags.COMBINATION_STEP_SOUND_BLOCKS);
            var mineablePickaxe = this.getOrCreateTagBuilder(BlockTags.MINEABLE_WITH_PICKAXE);
            var mineableWithAxe = this.getOrCreateTagBuilder(BlockTags.MINEABLE_WITH_AXE);
            var blockTag = this.getOrCreateTagBuilder(ColorfulCreakingsBlockTags.RESIN_CLUMP);
            var storageBlocks = this.getOrCreateTagBuilder(ConventionalBlockTags.STORAGE_BLOCKS_RESIN);
            var replaceable = this.getOrCreateTagBuilder(BlockTags.REPLACEABLE);

            ColoredResinBlocks.BLOCK_SET_MAP.forEach((creakingColor, blocks) -> {
                if (creakingColor == CreakingColor.ORANGE) {
                    return;
                }
                stairsTag.add(blocks.brickStairs());
                slabTag.add(blocks.brickSlab());
                wallTag.add(blocks.brickWall());
                COMBINATION_STEP_SOUND_BLOCKS.add(blocks.clump());
                mineablePickaxe.add(blocks.bricks(), blocks.brickStairs(), blocks.brickSlab(), blocks.brickWall(), blocks.chiseledBricks());
                mineableWithAxe.add(blocks.creakingHeart());
                storageBlocks.add(blocks.baseBlock());
                replaceable.add(blocks.clump());
            });
            ColoredResinBlocks.BLOCK_SET_MAP.forEach((creakingColor, blocks) -> {
                blockTag.add(blocks.clump());
            });

        }
    }

    public static final class ColorfulCreakingsItemTagProvider extends FabricTagProvider.ItemTagProvider {
        public ColorfulCreakingsItemTagProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
            super(output, registriesFuture);
        }

        @Override
        protected void addTags(HolderLookup.Provider provider) {
            var clumps = this.getOrCreateTagBuilder(ColorfulCreakingsItemTags.RESIN_CLUMPS);
            var bricks = this.getOrCreateTagBuilder(ColorfulCreakingsItemTags.RESIN_BRICKS);
            var storageBlocks = this.getOrCreateTagBuilder(ConventionalItemTags.STORAGE_BLOCKS_RESIN);
            var resinClumps = this.getOrCreateTagBuilder(ConventionalItemTags.RESIN_CLUMPS);
            var resinBricks = this.getOrCreateTagBuilder(ConventionalItemTags.RESIN_BRICKS);

            resinClumps.addTag(ColorfulCreakingsItemTags.RESIN_CLUMPS);
            resinBricks.addTag(ColorfulCreakingsItemTags.RESIN_BRICKS);
            ColoredResinItems.ITEM_SET_MAP.forEach((creakingColor, itemSet) -> {
                clumps.add(itemSet.clump());
                bricks.add(itemSet.brick());
                storageBlocks.add(itemSet.baseBlock());
            });
        }
    }

    public static final class ColorfulCreakingsRecipeProviderRunner extends FabricRecipeProvider {
        public ColorfulCreakingsRecipeProviderRunner(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
            super(output, registriesFuture);
        }

        @Override
        protected RecipeProvider createRecipeProvider(HolderLookup.Provider provider, RecipeOutput recipeOutput) {
            return new ColorfulCreakingsRecipeProvider(provider, recipeOutput);
        }


        @Override
        public String getName() {
            return "colorfulcreakings:recipes";
        }

        private static class ColorfulCreakingsRecipeProvider extends RecipeProvider {
            public ColorfulCreakingsRecipeProvider(HolderLookup.Provider provider, RecipeOutput recipeOutput) {
                super(provider, recipeOutput);
            }

            @Override
            public void buildRecipes() {
                ColoredResinItems.ITEM_SET_MAP.forEach((creakingColor, itemSet) -> {
                    if (creakingColor == CreakingColor.ORANGE) {
                        return;
                    }
                    SimpleCookingRecipeBuilder.smelting(Ingredient.of(itemSet.clump()), RecipeCategory.MISC, itemSet.brick(), 0.1F, 200).unlockedBy("has_resin_clump", this.has(itemSet.clump())).save(this.output);

                    this.stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, itemSet.brickSlab(), itemSet.bricks(), 2);
                    this.stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, itemSet.brickStairs(), itemSet.bricks());
                    this.stonecutterResultFromBase(RecipeCategory.DECORATIONS, itemSet.brickWall(), itemSet.bricks());
                    this.stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, itemSet.chiseledBricks(), itemSet.bricks());

                    this.twoByTwoPacker(RecipeCategory.BUILDING_BLOCKS, itemSet.bricks(), itemSet.brick());
                    this.nineBlockStorageRecipes(RecipeCategory.MISC, itemSet.clump(), RecipeCategory.BUILDING_BLOCKS, itemSet.baseBlock());
                    this.shaped(RecipeCategory.MISC, itemSet.creakingHeart()).define('R', itemSet.baseBlock()).define('L', Blocks.PALE_OAK_LOG).pattern(" L ").pattern(" R ").pattern(" L ").unlockedBy("has_resin_block", this.has(itemSet.baseBlock())).save(this.output);
                    var dyeTag = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("c", "dyes/" + creakingColor.getSerializedName()));
                    this.shaped(RecipeCategory.MISC, itemSet.brick(), 8).define('#', ColorfulCreakingsItemTags.RESIN_BRICKS).define('X', dyeTag).pattern("###").pattern("#X#").pattern("###").group("dyed_resin_brick").unlockedBy("has_resin_brick", this.has(ColorfulCreakingsItemTags.RESIN_BRICKS)).save(this.output, BuiltInRegistries.ITEM.getKey(itemSet.brick()).getPath() + "_dyeing");
                    this.shaped(RecipeCategory.MISC, itemSet.clump(), 8).define('#', ColorfulCreakingsItemTags.RESIN_CLUMPS).define('X', dyeTag).pattern("###").pattern("#X#").pattern("###").group("dyed_resin").unlockedBy("has_resin", this.has(ColorfulCreakingsItemTags.RESIN_CLUMPS)).save(this.output, BuiltInRegistries.ITEM.getKey(itemSet.clump()).getPath() + "_dyeing");
                    var blockFamily = BLOCK_FAMILIES.get(creakingColor);
                    this.generateRecipes(blockFamily, FeatureFlagSet.of(FeatureFlags.VANILLA));
                });
            }
        }
    }



}
