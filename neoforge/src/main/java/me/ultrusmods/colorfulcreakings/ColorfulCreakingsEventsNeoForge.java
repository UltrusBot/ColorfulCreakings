package me.ultrusmods.colorfulcreakings;

import me.ultrusmods.colorfulcreakings.network.CreakingColorS2CPacket;
import me.ultrusmods.colorfulcreakings.register.ColorfulCreakingsAttachments;
import me.ultrusmods.colorfulcreakings.register.ColorfulCreakingsCreativeTabItems;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.monster.creaking.Creaking;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.network.PacketDistributor;

@EventBusSubscriber(modid = Constants.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class ColorfulCreakingsEventsNeoForge {


    @SubscribeEvent
    private static void onBuildCreativeModeTabContents(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.BUILDING_BLOCKS) {
            ColorfulCreakingsCreativeTabItems.addBeforeOrangeResinBuildingBlocks((itemStack, itemStack2) -> {
                event.insertBefore(itemStack, itemStack2, CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            });

            ColorfulCreakingsCreativeTabItems.addAfterOrangeResinBuildingBlocks((itemStack, itemStack2) -> {
                event.insertAfter(itemStack, itemStack2, CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            });
        }
        else if (event.getTabKey() == CreativeModeTabs.INGREDIENTS) {
            ColorfulCreakingsCreativeTabItems.addBeforeOrangeResinIngredients((itemStack, itemStack2) -> {
                event.insertBefore(itemStack, itemStack2, CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            });
            ColorfulCreakingsCreativeTabItems.addAfterOrangeResinIngredients((itemStack, itemStack2) -> {
                event.insertAfter(itemStack, itemStack2, CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            });
        }
        else if (event.getTabKey() == CreativeModeTabs.SPAWN_EGGS) {
            ColorfulCreakingsCreativeTabItems.addBeforeCreakingHeart((itemStack, itemStack2) -> {
                event.insertBefore(itemStack, itemStack2, CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            });
            ColorfulCreakingsCreativeTabItems.addAfterCreakingHeart((itemStack, itemStack2) -> {
                event.insertAfter(itemStack, itemStack2, CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            });
        } else if (event.getTabKey() == CreativeModeTabs.NATURAL_BLOCKS) {
            ColorfulCreakingsCreativeTabItems.addBeforeNaturalBlocks((itemStack, itemStack2) -> {
                event.insertBefore(itemStack, itemStack2, CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            });
            ColorfulCreakingsCreativeTabItems.addAfterNaturalBlocks((itemStack, itemStack2) -> {
                event.insertAfter(itemStack, itemStack2, CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            });
        }
    }

    @EventBusSubscriber(modid = Constants.MOD_ID, bus = EventBusSubscriber.Bus.GAME)
    public static final class ForgeEvents {
        @SubscribeEvent
        private static void onStartTracking(PlayerEvent.StartTracking event) {
            var entity = event.getTarget();
            var player = event.getEntity();
            if (player instanceof ServerPlayer serverPlayer && entity instanceof Creaking creaking && ColorfulCreakingsAttachments.CREAKING_COLOR.hasAttachment(creaking)) {
                PacketDistributor.sendToPlayer(serverPlayer, new CreakingColorS2CPacket(creaking.getId(), ColorfulCreakingsAttachments.CREAKING_COLOR.getAttachment(creaking)));
            }
        }
    }

}
