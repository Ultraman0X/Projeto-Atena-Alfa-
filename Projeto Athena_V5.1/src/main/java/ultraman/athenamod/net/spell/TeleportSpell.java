package ultraman.athenamod.net.spell;

import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;

import java.util.Set;

public class TeleportSpell implements Spell{
    private static final double MAX_RANGE = 20.0;

    @Override
    public String getId() {
        return "Teleport";
    }

    @Override
    public int getManaCost() {
        return 50;
    }

    @Override
    public int getCooldown() {
        return 120;
    }

    @Override
    public void cast(World world, PlayerEntity player) {
        if (world.isClient()) return;
        ServerWorld serverWorld = (ServerWorld) world;

        Vec3d start = player.getEyePos();
        Vec3d end   = start.add(player.getRotationVec(1.0f).multiply(MAX_RANGE));

        // Raycast para encontrar o ponto de destino (pára em blocos sólidos)
        BlockHitResult hit = world.raycast(new RaycastContext(
                start, end,
                RaycastContext.ShapeType.COLLIDER,
                RaycastContext.FluidHandling.NONE,
                player
        ));

        Vec3d destination;
        if (hit.getType() == HitResult.Type.BLOCK) {
            BlockPos hitPos = hit.getBlockPos();
            destination = new Vec3d(
                    hitPos.getX() + 0.5,
                    hitPos.getY() + 1.0,   // em cima do bloco
                    hitPos.getZ() + 0.5
            );
        } else {
            destination = end;
        }

        serverWorld.spawnParticles(ParticleTypes.PORTAL,
                player.getX(), player.getY() + 1, player.getZ(),
                20, 0.3, 0.5, 0.3, 0.3);

        player.teleport(
                serverWorld,
                destination.x,
                destination.y,
                destination.z,
                Set.of(),
                player.getYaw(),
                player.getPitch()
        );

        // Slow falling por 1 segundo para evitar dano de queda pós-teleporte
        player.addStatusEffect(new StatusEffectInstance(
                StatusEffects.SLOW_FALLING, 30, 0, false, false
        ));

        // Partículas no ponto de chegada
        serverWorld.spawnParticles(ParticleTypes.PORTAL,
                destination.x, destination.y + 1, destination.z,
                20, 0.3, 0.5, 0.3, 0.3);

        serverWorld.playSound(null, player.getBlockPos(),
                SoundEvents.ENTITY_ENDERMAN_TELEPORT,
                SoundCategory.PLAYERS, 1.0f, 1.0f);

        player.sendMessage(Text.literal("§5✦ Teleporte!"), true);
    }
}
