package me.ultrusmods.colorfulcreakings.tag;

import me.ultrusmods.colorfulcreakings.Constants;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class ColorfulCreakingsItemTags {
    public static final TagKey<Item> RESIN_CLUMPS = TagKey.create(Registries.ITEM, Constants.id("resin_clumps"));
    public static final TagKey<Item> RESIN_BRICKS = TagKey.create(Registries.ITEM, Constants.id("resin_bricks"));
}
