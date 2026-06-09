package ultraman.athenamod.net.spell;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

public interface Spell {
    String getId();
    int getManaCost();
    int getCooldown();
    void cast(World world, PlayerEntity player);
}
