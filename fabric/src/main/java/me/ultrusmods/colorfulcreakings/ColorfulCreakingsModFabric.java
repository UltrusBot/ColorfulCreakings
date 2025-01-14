package me.ultrusmods.colorfulcreakings;

import me.ultrusmods.colorfulcreakings.network.CreakingColorS2CPacket;
import me.ultrusmods.colorfulcreakings.register.*;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.networking.v1.EntityTrackingEvents;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.world.entity.monster.creaking.Creaking;
import net.minecraft.world.item.CreativeModeTabs;

public class ColorfulCreakingsModFabric implements ModInitializer {
    
    @Override
    public void onInitialize() {
        ColorfulCreakingsMod.init();
        ColorfulCreakingBlocks.register();
        ColorfulCreakingsItems.register();
        ColorfulCreakingsBlockEntities.register();
        ColorfulCreakingsAttachments.register();

        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.BUILDING_BLOCKS).register(fabricItemGroupEntries -> {
            ColorfulCreakingsCreativeTabItems.addBeforeOrangeResinBuildingBlocks(fabricItemGroupEntries::addBefore);
            ColorfulCreakingsCreativeTabItems.addAfterOrangeResinBuildingBlocks(fabricItemGroupEntries::addAfter);
        });
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.INGREDIENTS).register(fabricItemGroupEntries -> {
            ColorfulCreakingsCreativeTabItems.addBeforeOrangeResinIngredients(fabricItemGroupEntries::addBefore);
            ColorfulCreakingsCreativeTabItems.addAfterOrangeResinIngredients(fabricItemGroupEntries::addAfter);
        });
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.SPAWN_EGGS).register(fabricItemGroupEntries -> {
            ColorfulCreakingsCreativeTabItems.addBeforeCreakingHeart(fabricItemGroupEntries::addBefore);
            ColorfulCreakingsCreativeTabItems.addAfterCreakingHeart(fabricItemGroupEntries::addAfter);
        });
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.NATURAL_BLOCKS).register(fabricItemGroupEntries -> {
            ColorfulCreakingsCreativeTabItems.addBeforeNaturalBlocks(fabricItemGroupEntries::addBefore);
            ColorfulCreakingsCreativeTabItems.addAfterNaturalBlocks(fabricItemGroupEntries::addAfter);
        });


        EntityTrackingEvents.START_TRACKING.register((entity, serverPlayer) -> {
            if (entity instanceof Creaking creaking && ColorfulCreakingsAttachments.CREAKING_COLOR.hasAttachment(creaking)) {
                ServerPlayNetworking.send(serverPlayer, new CreakingColorS2CPacket(creaking.getId(), ColorfulCreakingsAttachments.CREAKING_COLOR.getAttachment(creaking)));
            }
        });

        PayloadTypeRegistry.playS2C().register(CreakingColorS2CPacket.TYPE, CreakingColorS2CPacket.STREAM_CODEC);

    }
}
