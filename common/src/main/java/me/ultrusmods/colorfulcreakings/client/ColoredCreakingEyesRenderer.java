package me.ultrusmods.colorfulcreakings.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import me.ultrusmods.colorfulcreakings.data.CreakingColor;
import net.minecraft.client.model.CreakingModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.LivingEntityEmissiveLayer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.state.CreakingRenderState;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.ARGB;
import net.minecraft.util.Mth;
import net.minecraft.world.item.DyeColor;

import java.util.List;
import java.util.function.Function;

public class ColoredCreakingEyesRenderer extends RenderLayer<CreakingRenderState, CreakingModel> {

    private final LivingEntityEmissiveLayer.DrawSelector<CreakingRenderState, CreakingModel> drawSelector;
    private final Function<ResourceLocation, RenderType> bufferProvider;
    private final CreakingModel model;

    public ColoredCreakingEyesRenderer(RenderLayerParent<CreakingRenderState, CreakingModel> renderer, ResourceLocation texture, LivingEntityEmissiveLayer.DrawSelector<CreakingRenderState, CreakingModel> drawSelector, Function<ResourceLocation, RenderType> bufferProvider, EntityModelSet modelSet) {
        super(renderer);
        this.drawSelector = drawSelector;
        this.bufferProvider = bufferProvider;
        this.model = new CreakingModel(modelSet.bakeLayer(ColorfulCreakingsModelLayers.COLORED_CREAKING_EYES));

    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource multiBufferSource, int packedLight, CreakingRenderState creakingRenderState, float v, float v1) {
        if (this.onlyDrawSelectedParts(creakingRenderState)) {
            VertexConsumer vertexconsumer = multiBufferSource.getBuffer(this.bufferProvider.apply(getTexture(creakingRenderState)));
            this.getParentModel().renderToBuffer(poseStack, vertexconsumer, packedLight, LivingEntityRenderer.getOverlayCoords(creakingRenderState, 0.0F), getTextureColor(creakingRenderState));
            this.resetDrawForAllParts();

        } else {
            coloredCutoutModelCopyLayerRender(model, getTexture(creakingRenderState), poseStack, multiBufferSource, packedLight, creakingRenderState, getTextureColor(creakingRenderState));
        }
    }
    public static int getTextureColor(CreakingRenderState creakingRenderState) {
        if (creakingRenderState.customName == null || !creakingRenderState.customName.getString().equals("jeb_")) {
            return 0xFFFFFFFF;
        }
        int k = Mth.floor(creakingRenderState.ageInTicks);
        int i1 = DyeColor.values().length;
        float f = ((float)(k % 25) + Mth.frac(creakingRenderState.ageInTicks)) / 25.0F;
        int l = k / 25;
        int j1 = l % i1;
        int l1 = DyeColor.byId(j1).getTextureDiffuseColor();
        int k1 = (l + 1) % i1;
        int i2 = DyeColor.byId(k1).getTextureDiffuseColor();
        return ARGB.lerp(f, l1, i2);
    }

    public ResourceLocation getTexture(CreakingRenderState creakingRenderState) {
        if (creakingRenderState.customName != null && creakingRenderState.customName.getString().equals("jeb_")) {
            return CreakingColor.WHITE.getEyesTexture();
        }
        return ((CreakingAdditionalRenderState)creakingRenderState).getCreakingColor().getEyesTexture();
    }

    private boolean onlyDrawSelectedParts(CreakingRenderState renderState) {
        List<ModelPart> list = this.drawSelector.getPartsToDraw(this.getParentModel(), renderState);
        if (list.isEmpty()) {
            return false;
        } else {
            this.getParentModel().allParts().forEach((p_379465_) -> {
                p_379465_.skipDraw = true;
            });
            list.forEach((p_379767_) -> {
                p_379767_.skipDraw = false;
            });
            return true;
        }
    }

    private void resetDrawForAllParts() {
        this.getParentModel().allParts().forEach((p_379339_) -> {
            p_379339_.skipDraw = false;
        });
    }
}
