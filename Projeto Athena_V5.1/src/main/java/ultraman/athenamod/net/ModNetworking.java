package ultraman.athenamod.net;

import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.server.network.ServerPlayerEntity;
import ultraman.athenamod.net.spell.ClassSpellBook;
import ultraman.athenamod.net.spell.Spell;
import ultraman.athenamod.net.spell.SpellManager;

import java.util.List;

public class ModNetworking {

    public static void register() {
        // Registra o tipo de pacote no lado servidor (play channel)
        PayloadTypeRegistry.playC2S().register(CastSpellPayload.ID, CastSpellPayload.CODEC);

        PayloadTypeRegistry.playS2C().register(SyncManaPayload.ID, SyncManaPayload.CODEC);
        // Lida com o pacote quando o servidor o recebe
        ServerPlayNetworking.registerGlobalReceiver(CastSpellPayload.ID, (payload, context) -> {
            context.server().execute(() -> {
                var player = context.player();
                int slot = payload.slot();

                ClassType type = PlayerClassData.get(player);
                List<Spell> spells = ClassSpellBook.getSpells(type);

                if (slot < 0 || slot >= spells.size()) return;

                Spell spell = spells.get(slot);
                SpellManager.castSpell(spell, player.getWorld(), player);

                // Sincroniza mana após usar feitiço
                syncMana(player);
            });
        });
    }

    public static void syncMana(ServerPlayerEntity player) {
        int current = PlayerClassData.getMana(player);
        int max     = PlayerClassData.get(player).getMaxMana();
        ServerPlayNetworking.send(player, new SyncManaPayload(current, max));
    }
}
