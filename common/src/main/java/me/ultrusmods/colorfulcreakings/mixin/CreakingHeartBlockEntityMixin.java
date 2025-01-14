package me.ultrusmods.colorfulcreakings.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalIntRef;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import me.ultrusmods.colorfulcreakings.Constants;
import me.ultrusmods.colorfulcreakings.block.ColoredCreakingHeartBlock;
import me.ultrusmods.colorfulcreakings.block.ColoredResinBlocks;
import me.ultrusmods.colorfulcreakings.network.CreakingColorS2CPacket;
import me.ultrusmods.colorfulcreakings.register.ColorfulCreakingsAttachments;
import me.ultrusmods.colorfulcreakings.tag.ColorfulCreakingsBlockTags;
import net.minecraft.core.BlockPos;
import net.minecraft.network.protocol.common.ClientboundCustomPayloadPacket;
import net.minecraft.server.level.ServerChunkCache;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.monster.creaking.Creaking;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.MultifaceBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.CreakingHeartBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkSource;
import org.apache.commons.lang3.mutable.Mutable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CreakingHeartBlockEntity.class)
public abstract class CreakingHeartBlockEntityMixin extends BlockEntity {

    public CreakingHeartBlockEntityMixin(BlockEntityType<?> type, BlockPos pos, BlockState blockState) {
        super(type, pos, blockState);
    }

    @Inject(method = "spawnProtector", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/level/ServerLevel;gameEvent(Lnet/minecraft/world/entity/Entity;Lnet/minecraft/core/Holder;Lnet/minecraft/world/phys/Vec3;)V"))
    private static void changeCreakingColor(ServerLevel level, CreakingHeartBlockEntity creakingHeart, CallbackInfoReturnable<Creaking> cir, @Local Creaking creaking) {
        if (creakingHeart.getBlockState().getBlock() instanceof ColoredCreakingHeartBlock coloredCreakingHeartBlock) {
            ColorfulCreakingsAttachments.CREAKING_COLOR.setAttachment(creaking, coloredCreakingHeartBlock.getColor());
            ChunkSource var4 = level.getChunkSource();
            if (var4 instanceof ServerChunkCache chunkCache) {
                chunkCache.broadcastAndSend(creaking, new ClientboundCustomPayloadPacket(new CreakingColorS2CPacket(creaking.getId(), coloredCreakingHeartBlock.getColor())));
            }
        }
    }

    @Inject(method = {"method_65169", "lambda$spreadResin$4"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/core/Direction;getOpposite()Lnet/minecraft/core/Direction;"))
    private void changeResinColor(Mutable mutable, BlockPos blockPos, CallbackInfoReturnable<BlockPos.TraversalNodeStatus> cir, @Local LocalRef<BlockState> state) {
        if (this.getBlockState().getBlock() instanceof ColoredCreakingHeartBlock coloredCreakingHeartBlock) {
            var clumpBlock = ColoredResinBlocks.BLOCK_SET_MAP.get(coloredCreakingHeartBlock.getColor()).clump();
            if (state.get().isAir()) {
                state.set(clumpBlock.defaultBlockState());
            } else if (state.get().is(Blocks.WATER) && state.get().getFluidState().isSource()) {
                state.set(clumpBlock.defaultBlockState().setValue(MultifaceBlock.WATERLOGGED, true));
            }
        }
    }

    @ModifyExpressionValue(method = {"method_65169", "lambda$spreadResin$4"},
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/state/BlockState;is(Lnet/minecraft/world/level/block/Block;)Z", ordinal = 1))
    private boolean allowAllResinClumps(boolean original, @Local BlockState state) {
        return state.is(ColorfulCreakingsBlockTags.RESIN_CLUMP);
    }


    @Inject(method = "emitParticles", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/monster/creaking/Creaking;getBoundingBox()Lnet/minecraft/world/phys/AABB;"))
    private void changeTrailColor(ServerLevel level, int count, boolean reverseDirection, CallbackInfo ci, @Local(ordinal = 1) LocalIntRef i) {
        if (this.getBlockState().getBlock() instanceof ColoredCreakingHeartBlock coloredCreakingHeartBlock) {
            if (i.get() == 0xFC7812) { // Checking if it's the orange trail
                i.set(coloredCreakingHeartBlock.getColor().getDyeColor().getTextureDiffuseColor());
            }
        }

    }
}
