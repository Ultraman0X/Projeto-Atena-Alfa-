package ultraman.athenamod.net.spell;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.ActiveTargetGoal;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.world.World;

import java.util.List;

public class TauntSpell implements Spell{

    private static final int RADIUS        = 16;   // blocos ao redor do guerreiro
    private static final int TAUNT_TICKS   = 200;  // 10 segundos de provocação

    @Override
    public String getId() {
        return "Taunt";
    }

    @Override
    public int getManaCost() {
        return 10;
    }

    @Override
    public int getCooldown() {
        return 40;
    }

    @Override
    public void cast(World world, PlayerEntity player) {

        if (world.isClient()) return;
        ServerWorld serverWorld = (ServerWorld) world;

        List<MobEntity> mobs = serverWorld.getEntitiesByClass(
                MobEntity.class,
                player.getBoundingBox().expand(RADIUS),
                LivingEntity::isAlive
        );

        int count = 0;
        for (MobEntity mob : mobs) {
            mob.setTarget(player);
            count++;
        }

        net.minecraft.entity.effect.StatusEffectInstance absorption =
                new net.minecraft.entity.effect.StatusEffectInstance(
                        net.minecraft.entity.effect.StatusEffects.ABSORPTION,
                        TAUNT_TICKS,
                        1,   // amplifier 1 = 8 pontos de absorção (4 corações)
                        false,
                        true
                );
        net.minecraft.entity.effect.StatusEffectInstance resistance =
                new net.minecraft.entity.effect.StatusEffectInstance(
                        StatusEffects.RESISTANCE
                );
        player.addStatusEffect(absorption);
        player.addStatusEffect(resistance);

        serverWorld.playSound(null, player.getBlockPos(),
                SoundEvents.ENTITY_RAVAGER_ROAR,
                SoundCategory.PLAYERS, 1.0f, 0.8f);

        serverWorld.spawnParticles(ParticleTypes.ANGRY_VILLAGER,
                player.getX(), player.getY() + 1, player.getZ(),
                12, 0.5, 0.5, 0.5, 0.1);

        player.sendMessage(
                Text.literal("§c⚔ Provocação! " + count + " mob(s) focados em você por 10s."),
                true
        );
    }
}
