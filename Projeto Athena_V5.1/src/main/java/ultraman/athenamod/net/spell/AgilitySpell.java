package ultraman.athenamod.net.spell;

import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.world.World;

public class AgilitySpell implements Spell{

    private static final int DURATION = 200; // 10 segundos

    @Override
    public String getId() {
        return "Agility";
    }

    @Override
    public int getManaCost() {
        return 40;
    }

    @Override
    public int getCooldown() {
        return 150;
    }

    @Override
    public void cast(World world, PlayerEntity player) {
        if (world.isClient()) return;
        ServerWorld serverWorld = (ServerWorld) world;

        // Slow Falling: cancela dano de queda e faz cair lentamente
        player.addStatusEffect(new StatusEffectInstance(
                StatusEffects.SLOW_FALLING,
                DURATION, 0, false, true
        ));

        // Jump Boost II: pulos mais altos
        player.addStatusEffect(new StatusEffectInstance(
                StatusEffects.JUMP_BOOST,
                DURATION, 1, false, true
        ));

        // Speed I: leve aumento de velocidade também
        player.addStatusEffect(new StatusEffectInstance(
                StatusEffects.SPEED,
                DURATION, 0, false, true
        ));

        serverWorld.playSound(null, player.getBlockPos(),
                SoundEvents.ENTITY_PHANTOM_FLAP,
                SoundCategory.PLAYERS, 0.7f, 1.5f);

        serverWorld.spawnParticles(ParticleTypes.CLOUD,
                player.getX(), player.getY(), player.getZ(),
                15, 0.3, 0.2, 0.3, 0.05);

        player.sendMessage(
                Text.literal("§b🌬 Agilidade! Pulo e queda aprimorados por 10s."),
                true
        );
    }
}
