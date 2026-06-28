package ultraman.athenamod.net.spell;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;
import net.minecraft.util.math.Vec3d;

public class HealSpell implements Spell {

    private static final float HEAL_AMOUNT = 6.0f;  // 3 corações
    private static final double MAX_RANGE = 10.0;  // alcance em blocos

    @Override
    public String getId() {
        return "Heal";
    }

    @Override
    public int getManaCost() {
        return 50;
    }

    @Override
    public int getCooldown() {
        return 80;
    }

    @Override
    public void cast(World world, PlayerEntity player) {
        if (world.isClient()) return;
        ServerWorld serverWorld = (ServerWorld) world;

        // Raycast a partir dos olhos do clérigo na direção que está olhando
        Vec3d start = player.getEyePos();
        Vec3d end = start.add(player.getRotationVec(1.0f).multiply(MAX_RANGE));

        LivingEntity target = getTargetEntity(serverWorld, player, start, end);

        if (target == null) {
            // Cura a si mesmo
            target = player;
        }

        if (target instanceof HostileEntity) {
            player.sendMessage(
                    Text.literal("§c✚ Não é possível curar entidades hostis!"),
                    true
            );
            return;
        }

        float before = target.getHealth();
        target.heal(HEAL_AMOUNT);
        float healed = target.getHealth() - before;

        // Partículas de cura no alvo
        serverWorld.spawnParticles(ParticleTypes.HEART,
                target.getX(), target.getY() + target.getHeight(),
                target.getZ(), 8, 0.3, 0.3, 0.3, 0.05);

        serverWorld.playSound(null, target.getBlockPos(),
                SoundEvents.ENTITY_PLAYER_LEVELUP,
                SoundCategory.PLAYERS, 0.5f, 2.0f);

        String targetName = target == player ? "você mesmo" : target.getName().getString();
        player.sendMessage(
                Text.literal("§a✚ Curou " + targetName + " em " +
                        String.format("%.1f", healed) + " de vida!"),
                true
        );


    }
    private LivingEntity getTargetEntity(ServerWorld world, PlayerEntity caster,
                                         Vec3d start, Vec3d end) {
        // Buscar todas as entidades vivas no caminho do olhar
        return world.getEntitiesByClass(
                        LivingEntity.class,
                        caster.getBoundingBox().expand(MAX_RANGE + 1),
                        e -> e.isAlive() && e != caster
                ).stream()
                .filter(e -> e.getBoundingBox().expand(0.3).raycast(start, end).isPresent())
                .min((a, b) -> Double.compare(
                        a.squaredDistanceTo(caster),
                        b.squaredDistanceTo(caster)))
                .orElse(null);
    }
}

