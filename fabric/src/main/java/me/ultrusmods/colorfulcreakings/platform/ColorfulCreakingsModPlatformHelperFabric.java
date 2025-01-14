package me.ultrusmods.colorfulcreakings.platform;

import me.ultrusmods.colorfulcreakings.attachment.AttachmentDataHelper;
import me.ultrusmods.colorfulcreakings.attachment.CommonAttachment;
import me.ultrusmods.colorfulcreakings.platform.services.IPlatformHelper;
import net.fabricmc.fabric.api.attachment.v1.AttachmentRegistry;
import net.fabricmc.fabric.api.attachment.v1.AttachmentType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.world.entity.LivingEntity;

public class ColorfulCreakingsModPlatformHelperFabric implements IPlatformHelper {

    @Override
    public String getPlatformName() {
        return "Fabric";
    }

    @Override
    public boolean isModLoaded(String modId) {

        return FabricLoader.getInstance().isModLoaded(modId);
    }

    @Override
    public boolean isDevelopmentEnvironment() {

        return FabricLoader.getInstance().isDevelopmentEnvironment();
    }

    @SuppressWarnings("UnstableApiUsage")
    @Override
    public <T> void registerAttachmentType(CommonAttachment<T> attachment) {
        AttachmentRegistry.Builder<T> attachmentType = AttachmentRegistry.builder();
        if (attachment.copyOnDeath()) {
            attachmentType.copyOnDeath();
        }
        if (attachment.defaultValue() != null) {
            attachmentType.initializer(attachment.defaultValue());
        }
        if (attachment.codec() != null) {
            attachmentType.persistent(attachment.codec());
        }
        var builtAttachment = attachmentType.buildAndRegister(attachment.id());
        attachment.setHelper(new AttachmentDataHelper<T>() {
            private final AttachmentType<T> internalAttachment = builtAttachment;

            @Override
            public T getAttachment(LivingEntity entity) {
                return entity.getAttachedOrCreate(internalAttachment);
            }

            @Override
            public void setAttachment(LivingEntity entity, T value) {
                entity.setAttached(internalAttachment, value);
            }

            @Override
            public boolean hasAttachment(LivingEntity entity) {
                return entity.hasAttached(internalAttachment);
            }

            @Override
            public void removeAttachment(LivingEntity entity) {
                entity.removeAttached(internalAttachment);
            }
        });
    }
}
