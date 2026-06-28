package ultraman.athenamod.net.spell;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.FireballEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class FireballSpell implements Spell {
    @Override
    public String getId() { return "Fireball"; }

    @Override
    public int getManaCost() { return 100; }

    @Override
    public int getCooldown() { return 50; }

    @Override
    public void cast(World world, PlayerEntity player) {
        if (world.isClient()) return;
        ServerWorld serverWorld = (ServerWorld) world;

        // Direção que o jogador está olhando
        Vec3d look = player.getRotationVec(1.0f).normalize();

        double spawnX = player.getX() + look.x * 1.5;
        double spawnY = player.getEyeY();
        double spawnZ = player.getZ() + look.z * 1.5;

        FireballEntity fireball = new FireballEntity(
                serverWorld,
                player,
                look.multiply(1.5),
                2
        );
        fireball.setPosition(spawnX, spawnY, spawnZ);

        serverWorld.spawnEntity(fireball);

        serverWorld.playSound(null, player.getBlockPos(),
                SoundEvents.ENTITY_BLAZE_SHOOT,
                SoundCategory.PLAYERS, 1.0f, 1.0f);

        player.sendMessage(
                Text.literal("§6🔥 Bola de fogo lançada!"),
                true
        );
    }
}
