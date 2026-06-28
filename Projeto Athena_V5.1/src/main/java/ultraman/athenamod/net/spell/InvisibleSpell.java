package ultraman.athenamod.net.spell;

import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.world.World;

import java.util.List;

public class InvisibleSpell implements Spell{

    private static final int DURATION = 200;
    private static final int RADIUS   = 20;

    @Override
    public String getId() {
        return "Invisible";
    }

    @Override
    public int getManaCost() {
        return 30;
    }

    @Override
    public int getCooldown() {
        return 80;
    }

    @Override
    public void cast(World world, PlayerEntity player) {
        if (world.isClient()) return;
        ServerWorld serverWorld = (ServerWorld) world;

        player.addStatusEffect(new StatusEffectInstance(
                StatusEffects.INVISIBILITY,
                DURATION,
                0,
                false,
                false
        ));

        List<MobEntity> mobs = serverWorld.getEntitiesByClass(
                MobEntity.class,
                player.getBoundingBox().expand(RADIUS),
                mob -> mob.isAlive() && player.equals(mob.getTarget())
        );
        for (MobEntity mob : mobs) {
            mob.setTarget(null);
        }

        serverWorld.playSound(null, player.getBlockPos(),
                SoundEvents.ENTITY_ENDERMAN_TELEPORT,
                SoundCategory.PLAYERS, 0.5f, 1.5f);

        serverWorld.spawnParticles(ParticleTypes.POOF,
                player.getX(), player.getY() + 1, player.getZ(),
                20, 0.3, 0.5, 0.3, 0.05);

        player.sendMessage(
                Text.literal("§7✦ Você desapareceu nas sombras por 10s."),
                true
        );
    }
}
