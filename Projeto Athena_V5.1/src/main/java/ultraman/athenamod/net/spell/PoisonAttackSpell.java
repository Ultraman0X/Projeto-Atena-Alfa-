package ultraman.athenamod.net.spell;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.world.World;

public class PoisonAttackSpell implements Spell{

    // Quanto tempo o ladino fica "imbuído" (6 segundos)
    private static final int IMBUE_TICKS   = 120;
    // Duração do veneno aplicado no alvo (4 segundos)
    private static final int POISON_TICKS  = 80;

    @Override
    public String getId() {
        return "Poison Attack";
    }

    @Override
    public int getManaCost() {
        return 30;
    }

    @Override
    public int getCooldown() {
        return 100;
    }

    @Override
    public void cast(World world, PlayerEntity player) {
        if (world.isClient()) return;
        ServerWorld serverWorld = (ServerWorld) world;

        player.addStatusEffect(new StatusEffectInstance(
                StatusEffects.GLOWING,
                IMBUE_TICKS,
                0, false, true
        ));

        // Registrar o imbue no attachment de estado
        player.setAttached(ultraman.athenamod.net.AthenaMod.POISON_IMBUE_ATTACHMENT, IMBUE_TICKS);

        serverWorld.playSound(null, player.getBlockPos(),
                SoundEvents.ENTITY_WITCH_DRINK,
                SoundCategory.PLAYERS, 0.8f, 1.2f);

        serverWorld.spawnParticles(ParticleTypes.EFFECT,
                player.getX(), player.getY() + 1, player.getZ(),
                20, 0.3, 0.5, 0.3, 0.05);

        player.sendMessage(
                Text.literal("§2☠ Lâmina Envenenada! Próximos golpes causam veneno por 6s."),
                true
        );
    }
}
