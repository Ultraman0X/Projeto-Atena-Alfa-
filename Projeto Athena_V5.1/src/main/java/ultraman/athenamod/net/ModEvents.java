package ultraman.athenamod.net;

import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;

public class ModEvents {
    public static void register() {
        ServerTickEvents.END_SERVER_TICK.register(server -> {
            for (ServerPlayerEntity player : server.getPlayerManager().getPlayerList()) {
                PlayerClassData.tick(player);
                ModNetworking.syncMana(player);
            }
        });
        // Evento de dano: aplica veneno se o atacante for Ladino imbuído
        ServerLivingEntityEvents.ALLOW_DAMAGE.register(
                (target, source, amount) -> {
                    applyPoisonOnHit(source.getAttacker(), target);
                    return true; // não cancela o dano, só adiciona efeito
                }
        );

    }
    private static void tickPoisonImbue(ServerPlayerEntity player) {
        int ticks = player.getAttachedOrSet(AthenaMod.POISON_IMBUE_ATTACHMENT, 0);
        if (ticks > 0) {
            player.setAttached(AthenaMod.POISON_IMBUE_ATTACHMENT, ticks - 1);
        }
    }
    private static void applyPoisonOnHit(Object attacker, LivingEntity target) {
        if (!(attacker instanceof PlayerEntity player)) return;

        int imbue = player.getAttachedOrSet(AthenaMod.POISON_IMBUE_ATTACHMENT, 0);
        if (imbue <= 0) return;

        ClassType type = PlayerClassData.get(player);
        if (type != ClassType.ROGUE) return;

        target.addStatusEffect(new StatusEffectInstance(
                StatusEffects.POISON,
                80,   // 4 segundos de veneno
                0,
                false,
                true
        ));
    }
}
