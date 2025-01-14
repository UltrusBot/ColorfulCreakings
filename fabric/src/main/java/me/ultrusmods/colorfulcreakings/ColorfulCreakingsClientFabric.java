package me.ultrusmods.colorfulcreakings;

import me.ultrusmods.colorfulcreakings.block.ColoredResinBlocks;
import me.ultrusmods.colorfulcreakings.client.ColorfulCreakingsModelLayers;
import me.ultrusmods.colorfulcreakings.data.CreakingColor;
import me.ultrusmods.colorfulcreakings.network.CreakingColorS2CPacket;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.minecraft.client.model.CreakingModel;
import net.minecraft.client.renderer.RenderType;

public class ColorfulCreakingsClientFabric implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ColoredResinBlocks.BLOCK_SET_MAP.forEach(((creakingColor, coloredResinBlockSet) -> {
            if (creakingColor == CreakingColor.ORANGE) {
                return;
            }

            BlockRenderLayerMap.INSTANCE.putBlock(coloredResinBlockSet.clump(), RenderType.cutout());
        }));

        ClientPlayNetworking.registerGlobalReceiver(CreakingColorS2CPacket.TYPE, (payload, context) -> payload.handle());
        EntityModelLayerRegistry.registerModelLayer(ColorfulCreakingsModelLayers.COLORED_CREAKING_EYES, CreakingModel::createBodyLayer);
    }
}
