package me.ultrusmods.colorfulcreakings;

import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Constants {
    public static final String MOD_ID = "colorfulcreakings";
    public static final String MOD_NAME = "Colorful Creakings";
    public static final Logger LOG = LoggerFactory.getLogger(MOD_NAME);


    public static ResourceLocation id(String path) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
    }
}
