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

public class RallySpell implements Spell{

    private static final int RADIUS   = 20;
    private static final int DURATION = 160; // 8 segundos

    @Override
    public String getId() {
        return "Rally";
    }

    @Override
    public int getManaCost() {
        return 40;
    }

    @Override
    public int getCooldown() {
        return 120;
    }

    @Override
    public void cast(World world, PlayerEntity player) {
        if (world.isClient()) return;
        ServerWorld serverWorld = (ServerWorld) world;

        // Buscar aliados: jogadores e entidades não-hostis no raio
        List<LivingEntity> allies = serverWorld.getEntitiesByClass(
                LivingEntity.class,
                player.getBoundingBox().expand(RADIUS),
                e -> e.isAlive() && !(e instanceof HostileEntity)
        );

        int count = 0;
        for (LivingEntity ally : allies) {
            // Speed II
            ally.addStatusEffect(new StatusEffectInstance(
                    StatusEffects.SPEED, DURATION, 1, false, true));
            // Haste I (velocidade de ataque)
            ally.addStatusEffect(new StatusEffectInstance(
                    StatusEffects.HASTE, DURATION, 0, false, true));
            count++;
        }

        // Som de chifre de guerra
        serverWorld.playSound(null, player.getBlockPos(),
                SoundEvents.ITEM_GOAT_HORN_PLAY,
                SoundCategory.PLAYERS, 1.5f, 1.0f);

        serverWorld.spawnParticles(ParticleTypes.TOTEM_OF_UNDYING,
                player.getX(), player.getY() + 1, player.getZ(),
                30, 1.0, 0.5, 1.0, 0.2);

        player.sendMessage(
                Text.literal("§e🎺 Grito de Batalha! " + count + " aliado(s) fortalecidos por 8s."),
                true
        );
    }
}
