package ultraman.athenamod.net;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class AthenaModClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {

        ClientPlayNetworking.registerGlobalReceiver(SyncManaPayload.ID, (payload, context) -> {
            ManaHudOverlay.setMana(payload.currentMana(), payload.maxMana());
        });
        HudRenderCallback.EVENT.register((drawContext, renderTickCounter) -> {
            float tickDelta = renderTickCounter.getTickDelta(true);
            ManaHudOverlay.render(drawContext, tickDelta);
        });

        // Registra os keybindings
        ModKeyBindings.register();
    }

    /** Envia pacote de feitiço para o servidor. */
    public static void sendSpellPacket(int slot) {
        ClientPlayNetworking.send(new CastSpellPayload(slot));
    }
}
