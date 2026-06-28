package ultraman.athenamod.net.spell;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.WindChargeEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class WindBurstSpell implements Spell{
    @Override
    public String getId() {
        return "Wind Burst";
    }

    @Override
    public int getManaCost() {
        return 40;
    }

    @Override
    public int getCooldown() {
        return 30;
    }

    @Override
    public void cast(World world, PlayerEntity player) {
        if (world.isClient()) return;
        ServerWorld serverWorld = (ServerWorld) world;

        Vec3d look = player.getRotationVec(1.0f).normalize();

        // Spawn à frente do jogador
        double spawnX = player.getX() + look.x * 1.2;
        double spawnY = player.getEyeY();
        double spawnZ = player.getZ() + look.z * 1.2;

        // WindChargeEntity: projétil de vento nativo do 1.21+
        WindChargeEntity windCharge = new WindChargeEntity(
                player,
                serverWorld,
                        look.x * 2.0, look.y * 2.0, look.z * 2.0
                );
        windCharge.setPosition(spawnX, spawnY, spawnZ);
        serverWorld.spawnEntity(windCharge);

        serverWorld.playSound(null, player.getBlockPos(),
                SoundEvents.ENTITY_WIND_CHARGE_THROW,
                SoundCategory.PLAYERS, 1.0f, 1.0f);

        serverWorld.spawnParticles(ParticleTypes.GUST,
                spawnX, spawnY, spawnZ,
                5, 0.1, 0.1, 0.1, 0.05);

        player.sendMessage(Text.literal("§f💨 Rajada de Vento lançada!"), true);
    }
}
