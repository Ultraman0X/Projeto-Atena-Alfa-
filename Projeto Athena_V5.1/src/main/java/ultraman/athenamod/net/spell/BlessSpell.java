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

public class BlessSpell implements Spell{

    private static final int RADIUS   = 16;
    private static final int DURATION = 300;

    @Override
    public String getId() {
        return "Bless";
    }

    @Override
    public int getManaCost() {
        return 60;
    }

    @Override
    public int getCooldown() {
        return 200;
    }

    @Override
    public void cast(World world, PlayerEntity player) {
        if (world.isClient()) return;
        ServerWorld serverWorld = (ServerWorld) world;

        List<LivingEntity> targets = serverWorld.getEntitiesByClass(
                LivingEntity.class,
                player.getBoundingBox().expand(RADIUS),
                e -> e.isAlive() && !(e instanceof HostileEntity)
        );

        int count = 0;
        for (LivingEntity target : targets) {
            // Resistance I: reduz dano recebido em 20%
            target.addStatusEffect(new StatusEffectInstance(
                    StatusEffects.RESISTANCE, DURATION, 0, false, true));
            // Fire Resistance: imune a dano de fogo
            target.addStatusEffect(new StatusEffectInstance(
                    StatusEffects.FIRE_RESISTANCE, DURATION, 0, false, true));
            // Night Vision: visão no escuro
            target.addStatusEffect(new StatusEffectInstance(
                    StatusEffects.NIGHT_VISION, DURATION, 0, false, true));
            count++;
        }

        serverWorld.playSound(null, player.getBlockPos(),
                SoundEvents.BLOCK_BEACON_ACTIVATE,
                SoundCategory.PLAYERS, 0.8f, 1.2f);

        serverWorld.spawnParticles(ParticleTypes.END_ROD,
                player.getX(), player.getY() + 1, player.getZ(),
                40, 1.5, 0.5, 1.5, 0.05);

        player.sendMessage(
                Text.literal("§e✦ Bênção! " + count + " aliado(s) protegidos por 15s."),
                true
        );
    }
}
