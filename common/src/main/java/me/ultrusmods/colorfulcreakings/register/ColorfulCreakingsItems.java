package me.ultrusmods.colorfulcreakings.register;

import me.ultrusmods.colorfulcreakings.data.CreakingColor;

import static me.ultrusmods.colorfulcreakings.item.ColoredResinItems.*;

public class ColorfulCreakingsItems {
    public static final ColoredResinItemSet WHITE = ColoredResinItemSet.create(CreakingColor.WHITE);
    public static final ColoredResinItemSet LIGHT_GRAY = ColoredResinItemSet.create(CreakingColor.LIGHT_GRAY);
    public static final ColoredResinItemSet GRAY = ColoredResinItemSet.create(CreakingColor.GRAY);
    public static final ColoredResinItemSet BLACK = ColoredResinItemSet.create(CreakingColor.BLACK);
    public static final ColoredResinItemSet BROWN = ColoredResinItemSet.create(CreakingColor.BROWN);
    public static final ColoredResinItemSet RED = ColoredResinItemSet.create(CreakingColor.RED);
    public static final ColoredResinItemSet ORANGE = ColoredResinItemSet.createOrangeBase();
    public static final ColoredResinItemSet YELLOW = ColoredResinItemSet.create(CreakingColor.YELLOW);
    public static final ColoredResinItemSet LIGHT_BLUE = ColoredResinItemSet.create(CreakingColor.LIGHT_BLUE);
    public static final ColoredResinItemSet LIME = ColoredResinItemSet.create(CreakingColor.LIME);
    public static final ColoredResinItemSet GREEN = ColoredResinItemSet.create(CreakingColor.GREEN);
    public static final ColoredResinItemSet CYAN = ColoredResinItemSet.create(CreakingColor.CYAN);
    public static final ColoredResinItemSet BLUE = ColoredResinItemSet.create(CreakingColor.BLUE);
    public static final ColoredResinItemSet PURPLE = ColoredResinItemSet.create(CreakingColor.PURPLE);
    public static final ColoredResinItemSet MAGENTA = ColoredResinItemSet.create(CreakingColor.MAGENTA);
    public static final ColoredResinItemSet PINK = ColoredResinItemSet.create(CreakingColor.PINK);

    public static void register() {
        ITEM_SET_MAP.forEach((color, itemSet) -> {
            if (color == CreakingColor.ORANGE) {
                return;
            }
            itemSet.register();
        });
    }
}
