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

public class PowerAttackSpell implements Spell{
    @Override
    public String getId() {
        return "Power Attack";
    }

    @Override
    public int getManaCost() {
        return 20;
    }

    @Override
    public int getCooldown() {
        return 50;
    }

    @Override
    public void cast(World world, PlayerEntity player) {
        if (world.isClient()) return;
        ServerWorld serverWorld = (ServerWorld) world;

        // Strength II por 8 segundos (160 ticks)
        player.addStatusEffect(new StatusEffectInstance(
                StatusEffects.STRENGTH,
                160,
                1,      // amplifier 1 = Strength II
                false,
                true
        ));

        serverWorld.playSound(null, player.getBlockPos(),
                SoundEvents.ENTITY_PLAYER_ATTACK_STRONG,
                SoundCategory.PLAYERS, 1.0f, 0.7f);

        serverWorld.spawnParticles(ParticleTypes.CRIT,
                player.getX(), player.getY() + 1, player.getZ(),
                16, 0.3, 0.5, 0.3, 0.2);

        player.sendMessage(Text.literal("§c⚔ Ataque Poderoso! Dano aumentado por 8s."), true);
    }
}
