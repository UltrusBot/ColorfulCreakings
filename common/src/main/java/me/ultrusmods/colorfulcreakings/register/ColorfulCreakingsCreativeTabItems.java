package me.ultrusmods.colorfulcreakings.register;

import com.google.common.collect.Lists;
import me.ultrusmods.colorfulcreakings.data.CreakingColor;
import me.ultrusmods.colorfulcreakings.item.ColoredResinItems;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.function.BiConsumer;

public class ColorfulCreakingsCreativeTabItems {


    public static void addBeforeOrangeResinBuildingBlocks(BiConsumer<ItemStack, ItemStack> addBefore) {
        var items = ColoredResinItems.ITEM_SET_MAP.values();
        for (var itemSet : items) {
            if (itemSet.color().ordinal() >= CreakingColor.ORANGE.ordinal()) {
                continue;
            }
            addBefore.accept(Items.RESIN_BRICKS.getDefaultInstance(), itemSet.bricks().getDefaultInstance());
            addBefore.accept(Items.RESIN_BRICKS.getDefaultInstance(), itemSet.brickStairs().getDefaultInstance());
            addBefore.accept(Items.RESIN_BRICKS.getDefaultInstance(), itemSet.brickSlab().getDefaultInstance());
            addBefore.accept(Items.RESIN_BRICKS.getDefaultInstance(), itemSet.brickWall().getDefaultInstance());
            addBefore.accept(Items.RESIN_BRICKS.getDefaultInstance(), itemSet.chiseledBricks().getDefaultInstance());
        }
    }
    public static void addAfterOrangeResinBuildingBlocks(BiConsumer<ItemStack, ItemStack> addAfter) {
        var items = Lists.reverse(Lists.newArrayList(ColoredResinItems.ITEM_SET_MAP.values()));
        for (var itemSet : items) {
            if (itemSet.color().ordinal() <= CreakingColor.ORANGE.ordinal()) {
                continue;
            }
            addAfter.accept(Items.CHISELED_RESIN_BRICKS.getDefaultInstance(), itemSet.chiseledBricks().getDefaultInstance());
            addAfter.accept(Items.CHISELED_RESIN_BRICKS.getDefaultInstance(), itemSet.brickWall().getDefaultInstance());
            addAfter.accept(Items.CHISELED_RESIN_BRICKS.getDefaultInstance(), itemSet.brickSlab().getDefaultInstance());
            addAfter.accept(Items.CHISELED_RESIN_BRICKS.getDefaultInstance(), itemSet.brickStairs().getDefaultInstance());
            addAfter.accept(Items.CHISELED_RESIN_BRICKS.getDefaultInstance(), itemSet.bricks().getDefaultInstance());

        }
    }

    public static void addBeforeNaturalBlocks(BiConsumer<ItemStack, ItemStack> addBefore) {
        var items = ColoredResinItems.ITEM_SET_MAP.values();
        for (var itemSet : items) {
            if (itemSet.color().ordinal() >= CreakingColor.ORANGE.ordinal()) {
                continue;
            }
            addBefore.accept(Items.RESIN_BLOCK.getDefaultInstance(), itemSet.baseBlock().getDefaultInstance());
        }
    }

    public static void addAfterNaturalBlocks(BiConsumer<ItemStack, ItemStack> addAfter) {
        var items = Lists.reverse(Lists.newArrayList(ColoredResinItems.ITEM_SET_MAP.values()));
        for (var itemSet : items) {
            if (itemSet.color().ordinal() <= CreakingColor.ORANGE.ordinal()) {
                continue;
            }
            addAfter.accept(Items.RESIN_BLOCK.getDefaultInstance(), itemSet.baseBlock().getDefaultInstance());
        }
    }

    public static void addBeforeOrangeResinIngredients(BiConsumer<ItemStack, ItemStack> addBefore) {
        var items = ColoredResinItems.ITEM_SET_MAP.values();
        for (var itemSet : items) {
            if (itemSet.color().ordinal() >= CreakingColor.ORANGE.ordinal()) {
                continue;
            }
            addBefore.accept(Items.RESIN_BRICK.getDefaultInstance(), itemSet.brick().getDefaultInstance());
            addBefore.accept(Items.RESIN_CLUMP.getDefaultInstance(), itemSet.clump().getDefaultInstance());
        }
    }

    public static void addAfterOrangeResinIngredients(BiConsumer<ItemStack, ItemStack> addAfter) {
        var items = Lists.reverse(Lists.newArrayList(ColoredResinItems.ITEM_SET_MAP.values()));
        for (var itemSet : items) {
            if (itemSet.color().ordinal() <= CreakingColor.ORANGE.ordinal()) {
                continue;
            }
            addAfter.accept(Items.RESIN_BRICK.getDefaultInstance(), itemSet.brick().getDefaultInstance());
            addAfter.accept(Items.RESIN_CLUMP.getDefaultInstance(), itemSet.clump().getDefaultInstance());
        }
    }

    public static void addBeforeCreakingHeart(BiConsumer<ItemStack, ItemStack> addBefore) {
        var items = ColoredResinItems.ITEM_SET_MAP.values();
        for (var itemSet : items) {
            if (itemSet.color().ordinal() >= CreakingColor.ORANGE.ordinal()) {
                continue;
            }
            addBefore.accept(Items.CREAKING_HEART.getDefaultInstance(), itemSet.creakingHeart().getDefaultInstance());
        }
    }

    public static void addAfterCreakingHeart(BiConsumer<ItemStack, ItemStack> addAfter) {
        var items = Lists.reverse(Lists.newArrayList(ColoredResinItems.ITEM_SET_MAP.values()));
        for (var itemSet : items) {
            if (itemSet.color().ordinal() <= CreakingColor.ORANGE.ordinal()) {
                continue;
            }
            addAfter.accept(Items.CREAKING_HEART.getDefaultInstance(), itemSet.creakingHeart().getDefaultInstance());
        }
    }
}
