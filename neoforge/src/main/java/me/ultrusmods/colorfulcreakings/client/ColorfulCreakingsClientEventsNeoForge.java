package me.ultrusmods.colorfulcreakings.client;

import me.ultrusmods.colorfulcreakings.Constants;
import me.ultrusmods.colorfulcreakings.block.ColoredResinBlocks;
import me.ultrusmods.colorfulcreakings.data.CreakingColor;
import net.minecraft.client.model.CreakingModel;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.CreakingRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.creaking.Creaking;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

@EventBusSubscriber(modid = Constants.MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ColorfulCreakingsClientEventsNeoForge {

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        ColoredResinBlocks.BLOCK_SET_MAP.forEach(((creakingColor, coloredResinBlockSet) -> {
            if (creakingColor == CreakingColor.ORANGE) {
                return;
            }

            ItemBlockRenderTypes.setRenderLayer(coloredResinBlockSet.clump(), RenderType.cutout());
        }));
    }

    @SubscribeEvent
    public static void registerEntityLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(ColorfulCreakingsModelLayers.COLORED_CREAKING_EYES, CreakingModel::createBodyLayer);
    }

    @SubscribeEvent
    public static void onAddRendererLayer(EntityRenderersEvent.AddLayers event) {
        ResourceLocation EYES_TEXTURE_LOCATION = ResourceLocation.withDefaultNamespace("textures/entity/creaking/creaking_eyes.png");
        if (event.getEntityTypes().contains(EntityType.CREAKING)) {
            var renderer = event.getRenderer(EntityType.CREAKING);
            if (renderer instanceof CreakingRenderer<? extends Creaking> creakingRenderer) {
                creakingRenderer.addLayer(new ColoredCreakingEyesRenderer(creakingRenderer, EYES_TEXTURE_LOCATION, CreakingModel::getHeadModelParts, RenderType::eyes, event.getEntityModels()));
            }
        }
    }
}
