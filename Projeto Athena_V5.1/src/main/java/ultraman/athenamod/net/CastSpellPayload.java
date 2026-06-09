package ultraman.athenamod.net;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

public record CastSpellPayload(int slot) implements CustomPayload {

    public static final Id<CastSpellPayload> ID =
            new Id<>(Identifier.of("athenamod", "cast_spell"));

    public static final PacketCodec<RegistryByteBuf, CastSpellPayload> CODEC =
            PacketCodec.tuple(
                    PacketCodecs.INTEGER, CastSpellPayload::slot,
                    CastSpellPayload::new
            );

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}
