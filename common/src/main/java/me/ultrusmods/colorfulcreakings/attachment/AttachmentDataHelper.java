package me.ultrusmods.colorfulcreakings.attachment;

import net.minecraft.world.entity.LivingEntity;

public interface AttachmentDataHelper<T> {
    T getAttachment(LivingEntity entity);

    void setAttachment(LivingEntity entity, T value);

    boolean hasAttachment(LivingEntity entity);

    void removeAttachment(LivingEntity entity);



}
