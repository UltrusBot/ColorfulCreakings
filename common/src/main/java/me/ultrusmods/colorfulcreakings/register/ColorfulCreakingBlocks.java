package me.ultrusmods.colorfulcreakings.register;

import me.ultrusmods.colorfulcreakings.block.ColoredResinBlocks.ColoredResinBlockSet;
import me.ultrusmods.colorfulcreakings.data.CreakingColor;

import static me.ultrusmods.colorfulcreakings.block.ColoredResinBlocks.BLOCK_SET_MAP;

public class ColorfulCreakingBlocks {
    public static final ColoredResinBlockSet WHITE = ColoredResinBlockSet.create(CreakingColor.WHITE);
    public static final ColoredResinBlockSet ORANGE = ColoredResinBlockSet.createOrangeBase();
    public static final ColoredResinBlockSet MAGENTA = ColoredResinBlockSet.create(CreakingColor.MAGENTA);
    public static final ColoredResinBlockSet LIGHT_BLUE = ColoredResinBlockSet.create(CreakingColor.LIGHT_BLUE);
    public static final ColoredResinBlockSet YELLOW = ColoredResinBlockSet.create(CreakingColor.YELLOW);
    public static final ColoredResinBlockSet LIME = ColoredResinBlockSet.create(CreakingColor.LIME);
    public static final ColoredResinBlockSet PINK = ColoredResinBlockSet.create(CreakingColor.PINK);
    public static final ColoredResinBlockSet GRAY = ColoredResinBlockSet.create(CreakingColor.GRAY);
    public static final ColoredResinBlockSet LIGHT_GRAY = ColoredResinBlockSet.create(CreakingColor.LIGHT_GRAY);
    public static final ColoredResinBlockSet CYAN = ColoredResinBlockSet.create(CreakingColor.CYAN);
    public static final ColoredResinBlockSet PURPLE = ColoredResinBlockSet.create(CreakingColor.PURPLE);
    public static final ColoredResinBlockSet BLUE = ColoredResinBlockSet.create(CreakingColor.BLUE);
    public static final ColoredResinBlockSet BROWN = ColoredResinBlockSet.create(CreakingColor.BROWN);
    public static final ColoredResinBlockSet GREEN = ColoredResinBlockSet.create(CreakingColor.GREEN);
    public static final ColoredResinBlockSet RED = ColoredResinBlockSet.create(CreakingColor.RED);
    public static final ColoredResinBlockSet BLACK = ColoredResinBlockSet.create(CreakingColor.BLACK);

    public static void register() {
        BLOCK_SET_MAP.forEach((color, blockSet) -> {
            if (color == CreakingColor.ORANGE) {
                return;
            }
            blockSet.register();
        });
    }
}
