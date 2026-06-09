package ultraman.athenamod.net.spell;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.world.World;

public class FireballSpell implements Spell {
    @Override
    public String getId() { return "fireball"; }

    @Override
    public int getManaCost() { return 20; }

    @Override
    public int getCooldown() { return 60; }

    @Override
    public void cast(World world, PlayerEntity player) {
        player.sendMessage(Text.literal("Fireball lançada!"), true);
        // TODO: implementar lógica de projétil
    }
}
