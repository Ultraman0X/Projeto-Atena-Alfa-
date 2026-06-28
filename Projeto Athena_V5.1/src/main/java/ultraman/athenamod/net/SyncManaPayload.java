package ultraman.athenamod.net;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

public record SyncManaPayload(int currentMana, int maxMana) implements CustomPayload {
    public static final Id<SyncManaPayload> ID =
            new Id<>(Identifier.of("athenamod", "sync_mana"));

    public static final PacketCodec<RegistryByteBuf, SyncManaPayload> CODEC =
            PacketCodec.tuple(
                    PacketCodecs.INTEGER, SyncManaPayload::currentMana,
                    PacketCodecs.INTEGER, SyncManaPayload::maxMana,
                    SyncManaPayload::new
            );

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}
