package ultraman.athenamod.net.spell;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.world.World;

import java.util.List;

public class BaneSpell implements Spell{

    private static final int RADIUS   = 16;
    private static final int DURATION = 200;

    @Override
    public String getId() {
        return "Bane";
    }

    @Override
    public int getManaCost() {
        return 60;
    }

    @Override
    public int getCooldown() {
        return 180;
    }

    @Override
    public void cast(World world, PlayerEntity player) {
        if (world.isClient()) return;
        ServerWorld serverWorld = (ServerWorld) world;

        List<LivingEntity> targets = serverWorld.getEntitiesByClass(
                LivingEntity.class,
                player.getBoundingBox().expand(RADIUS),
                e -> e.isAlive() && e instanceof HostileEntity
        );

        int count = 0;
        for (LivingEntity target : targets) {
            // Weakness II: reduz dano de ataque
            target.addStatusEffect(new StatusEffectInstance(
                    StatusEffects.WEAKNESS, DURATION, 1, false, true));
            // Blindness: cegueira (remove linha de visão de longo alcance)
            target.addStatusEffect(new StatusEffectInstance(
                    StatusEffects.BLINDNESS, DURATION, 0, false, true));
            // Slowness I: desacelera também
            target.addStatusEffect(new StatusEffectInstance(
                    StatusEffects.SLOWNESS, DURATION, 0, false, true));
            count++;
        }

        serverWorld.playSound(null, player.getBlockPos(),
                SoundEvents.ENTITY_WITHER_AMBIENT,
                SoundCategory.PLAYERS, 0.6f, 1.5f);

        serverWorld.spawnParticles(ParticleTypes.SQUID_INK,
                player.getX(), player.getY() + 1, player.getZ(),
                30, 1.5, 0.5, 1.5, 0.1);

        player.sendMessage(
                Text.literal("§8☠ Anátema! " + count + " inimigo(s) amaldiçoados por 10s."),
                true
        );
    }
}
