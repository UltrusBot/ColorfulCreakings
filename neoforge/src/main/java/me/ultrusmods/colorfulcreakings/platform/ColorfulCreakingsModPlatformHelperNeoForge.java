package me.ultrusmods.colorfulcreakings.platform;

import me.ultrusmods.colorfulcreakings.attachment.AttachmentDataHelper;
import me.ultrusmods.colorfulcreakings.attachment.CommonAttachment;
import me.ultrusmods.colorfulcreakings.platform.services.IPlatformHelper;
import net.minecraft.core.Registry;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.fml.ModList;
import net.neoforged.fml.loading.FMLLoader;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class ColorfulCreakingsModPlatformHelperNeoForge implements IPlatformHelper {

    @Override
    public String getPlatformName() {
            return "NeoForge";
    }

    @Override
    public boolean isModLoaded(String modId) {

        return ModList.get().isLoaded(modId);
    }

    @Override
    public boolean isDevelopmentEnvironment() {

        return !FMLLoader.isProduction();
    }

    @Override
    public <T> void registerAttachmentType(CommonAttachment<T> attachment) {
        var attachmentTypeBuilder = AttachmentType.builder(attachment.defaultValue());
        if (attachment.codec() != null) {
            attachmentTypeBuilder.serialize(attachment.codec());
        }
        if (attachment.copyOnDeath()) {
            attachmentTypeBuilder.copyOnDeath();
        }
        var attachmentType = attachmentTypeBuilder.build();
        attachment.setHelper(new AttachmentDataHelper<T>() {
            private final AttachmentType<T> internalAttachment = attachmentType;
            @Override
            public T getAttachment(LivingEntity entity) {
                return entity.getData(internalAttachment);
            }

            @Override
            public void setAttachment(LivingEntity entity, T value) {
                entity.setData(internalAttachment, value);
            }

            @Override
            public boolean hasAttachment(LivingEntity entity) {
                return entity.hasData(internalAttachment);
            }

            @Override
            public void removeAttachment(LivingEntity entity) {
                entity.removeData(internalAttachment);
            }
        });
        Registry.register(NeoForgeRegistries.ATTACHMENT_TYPES, attachment.id(), attachmentType);

    }
}