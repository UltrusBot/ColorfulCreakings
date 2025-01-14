package me.ultrusmods.colorfulcreakings.mixin.client;

import me.ultrusmods.colorfulcreakings.client.ColoredCreakingEyesRenderer;
import me.ultrusmods.colorfulcreakings.client.CreakingAdditionalRenderState;
import me.ultrusmods.colorfulcreakings.platform.Services;
import me.ultrusmods.colorfulcreakings.register.ColorfulCreakingsAttachments;
import net.minecraft.client.model.CreakingModel;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.CreakingRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.state.CreakingRenderState;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.monster.creaking.Creaking;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CreakingRenderer.class)
public abstract class CreakingRendererMixin<T extends Creaking> extends MobRenderer<T, CreakingRenderState, CreakingModel> {

    @Shadow @Final private static ResourceLocation EYES_TEXTURE_LOCATION;

    public CreakingRendererMixin(EntityRendererProvider.Context p_174304_, CreakingModel p_174305_, float p_174306_) {
        super(p_174304_, p_174305_, p_174306_);
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    public void changeCreakingRenderingFeatures(EntityRendererProvider.Context context, CallbackInfo ci) {
        this.layers.removeFirst();
        if (Services.PLATFORM.getPlatformName().equals("Fabric")) {
            this.addLayer(new ColoredCreakingEyesRenderer(this, EYES_TEXTURE_LOCATION, CreakingModel::getHeadModelParts, RenderType::eyes, context.getModelSet()));
        }

    }

    @Inject(method = "extractRenderState(Lnet/minecraft/world/entity/monster/creaking/Creaking;Lnet/minecraft/client/renderer/entity/state/CreakingRenderState;F)V"
            , at = @At("TAIL"))
    public void addColorCreakingRenderState(T p_379591_, CreakingRenderState p_380210_, float p_379411_, CallbackInfo ci) {
        ((CreakingAdditionalRenderState)p_380210_).setCreakingColor(ColorfulCreakingsAttachments.CREAKING_COLOR.getAttachment(p_379591_));
    }
}
