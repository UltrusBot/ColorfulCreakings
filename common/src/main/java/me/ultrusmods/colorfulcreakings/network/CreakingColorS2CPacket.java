package me.ultrusmods.colorfulcreakings.network;

import me.ultrusmods.colorfulcreakings.Constants;
import me.ultrusmods.colorfulcreakings.data.CreakingColor;
import me.ultrusmods.colorfulcreakings.register.ColorfulCreakingsAttachments;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.entity.monster.creaking.Creaking;

public record CreakingColorS2CPacket(int entityId, CreakingColor color) implements CustomPacketPayload {

    public static final Type<CreakingColorS2CPacket> TYPE = new Type<>(Constants.id("creaking_color"));
    public static final StreamCodec<RegistryFriendlyByteBuf, CreakingColorS2CPacket> STREAM_CODEC = StreamCodec.of(CreakingColorS2CPacket::write, CreakingColorS2CPacket::new);


    public CreakingColorS2CPacket(FriendlyByteBuf buf) {
        this(buf.readInt(), buf.readEnum(CreakingColor.class));
    }

    public static void write(FriendlyByteBuf buf, CreakingColorS2CPacket packet) {
        buf.writeInt(packet.entityId());
        buf.writeEnum(packet.color());
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public void handle() {
        var packetS2C = this;
        Minecraft.getInstance().execute(new Runnable() {
            @Override
            public void run() {
                var entity = Minecraft.getInstance().level.getEntity(packetS2C.entityId());
                if (entity instanceof Creaking creaking) {
                    ColorfulCreakingsAttachments.CREAKING_COLOR.setAttachment(creaking, packetS2C.color());
                }
            }
        });
    }
}
